import com.sun.net.httpserver.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.io.IOException;

// Small local web server. All records stay in memory.
public class HostelServer {
    static ArrayList<Student> students = new ArrayList<>();
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<String> notices = new ArrayList<>();
    static Warden[] wardens = {new Warden("Warden 1", 1001), new Warden("Warden 2", 1002)};
    static Map<String, HostelMember> sessions = new HashMap<>();
    static HostelNoticeBoard board = new HostelNoticeBoard();
    static int nextId = 1;

    public static void main(String[] args) throws IOException {
        for (int i = 1; i <= 5; i++) rooms.add(new Room(100+i, 2, i%2==0));
        for (int i = 1; i <= 8; i++) rooms.add(new Room(200+i, 3, i%2==0));
        for (int i = 1; i <= 4; i++) rooms.add(new Room(300+i, 4, i%2==0));
        for (Warden w : wardens) w.resetPassword("WARDEN@123");
        notices.add("Welcome! Please pay your hostel fee on time.");
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 8080), 0);
        server.createContext("/", HostelServer::handle);
        server.start();
        System.out.println("Open http://localhost:8080 | Stop with Ctrl+C");
    }

    // One request at a time keeps room bookings and payments consistent.
    static synchronized void handle(HttpExchange x) throws IOException {
        try {
            String path = x.getRequestURI().getPath();
            if (path.equals("/style.css") && x.getRequestMethod().equals("GET")) {
                send(x, 200, "text/css", Files.readString(Path.of("style.css"))); return;
            }
            if (!path.equals("/")) { send(x,404,"text/plain","Page not found"); return; }
            HostelMember user = sessions.get(cookie(x));
            if (x.getRequestMethod().equals("POST")) {
                // Reject cross-site form submissions. Browser forms carry Origin.
                String origin = x.getRequestHeaders().getFirst("Origin");
                if (origin != null && !origin.equals("http://localhost:8080") && !origin.equals("http://127.0.0.1:8080"))
                    throw new IllegalArgumentException("Invalid request origin.");
                byte[] body = x.getRequestBody().readNBytes(16385);
                if (body.length > 16384) throw new IllegalArgumentException("Form is too large.");
                Map<String,String> f = parse(new String(body, StandardCharsets.UTF_8));
                String action = f.getOrDefault("action", "");
                if (action.equals("login")) {
                    int id = number(f,"id");
                    HostelMember found = null;
                    if (f.get("role").equals("student")) found = find(id);
                    else for (Warden w : wardens) if (w.getId()==id) found=w;
                    if (found==null || !found.checkPassword(f.get("password")))
                        throw new IllegalArgumentException("Incorrect ID or password.");
                    sessions.remove(cookie(x));
                    String token = UUID.randomUUID().toString();
                    sessions.put(token,found);
                    x.getResponseHeaders().add("Set-Cookie","session="+token+"; HttpOnly; SameSite=Strict; Path=/");
                    redirect(x,"Logged in."); return;
                }
                if (action.equals("register")) {
                    if (students.size()>=50) throw new IllegalStateException("Hostel registration is full.");
                    Student s = new Student(f.get("name"),nextId,f.get("course"));
                    s.resetPassword(f.get("password"));
                    students.add(s); nextId++;
                    redirect(x,"Registered. Student ID: "+s.getId()+". Use this ID to log in."); return;
                }
                if (user==null) { send(x,401,"text/html",page(null,"Please log in first.")); return; }
                if (action.equals("logout")) {
                    sessions.remove(cookie(x));
                    x.getResponseHeaders().add("Set-Cookie","session=; Max-Age=0; HttpOnly; SameSite=Strict; Path=/");
                } else if (action.equals("password")) {
                    user.changePassword(f.get("current"), f.get("password"));
                } else if (user instanceof Student) {
                    studentAction((Student)user,action,f);
                } else {
                    wardenAction((Warden)user,action,f);
                }
                redirect(x,"Saved successfully."); return;
            }
            if (!x.getRequestMethod().equals("GET")) { send(x,405,"text/plain","Method not allowed"); return; }
            String message = parse(x.getRequestURI().getRawQuery()).getOrDefault("message","");
            send(x,200,"text/html",page(user,message));
        } catch (IllegalArgumentException | IllegalStateException e) {
            send(x,400,"text/html",page(sessions.get(cookie(x)),e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace(); send(x,500,"text/plain","Server error. Check the terminal.");
        } finally { x.close(); }
    }

    static void studentAction(Student s, String action, Map<String,String> f) {
        switch (action) {
            case "book":
                Room room = null;
                for (Room r : rooms) if (r.getRoomNo()==number(f,"room")) room=r;
                if (room==null) throw new IllegalArgumentException("Choose a valid room.");
                room.addStudent(s); break;
            case "pay":
                double amount = Double.parseDouble(f.get("amount"));
                Payment payment = new Payment(s,amount,f.get("mode"),f.get("reference"));
                s.getFee().addPayment(amount); s.addPayment(payment); break;
            case "leave":
                LocalDate from = LocalDate.parse(f.get("from")), to = LocalDate.parse(f.get("to"));
                if (to.isBefore(from)) throw new IllegalArgumentException("End date must not be before start date.");
                s.setLeave(new Leave(s,from.toString(),to.toString(),f.get("reason"))); break;
            case "visitor": s.setVisitor(new Visitor(s,f.get("name"))); break;
            default: throw new IllegalArgumentException("Action not allowed.");
        }
    }

    static void wardenAction(Warden w, String action, Map<String,String> f) throws InterruptedException {
        if (action.equals("notice")) {
            String message = HostelMember.requireText(f.get("notice"),"Notice");
            NotificationThread thread = new NotificationThread(message,board);
            thread.start(); thread.join(); notices.add(message); return;
        }
        Student s = find(number(f,"student"));
        if (s==null) throw new IllegalArgumentException("Student not found.");
        switch (action) {
            case "update": s.updateDetails(f.get("name"),f.get("course")); break;
            case "attendance":
                String status = f.get("status");
                if (!Arrays.asList("Present","Absent").contains(status)) throw new IllegalArgumentException("Invalid attendance.");
                s.setAttendance(status); break;
            case "decide":
                if (s.getLeave()==null) throw new IllegalStateException("No leave request.");
                String decision = f.get("decision");
                if (!Arrays.asList("Approved","Rejected").contains(decision)) throw new IllegalArgumentException("Invalid decision.");
                s.getLeave().decide(decision); break;
            case "approve":
                if (s.getVisitor()==null) throw new IllegalStateException("No visitor request.");
                s.getVisitor().approve(); break;
            case "penalty": s.getFee().applyLatePenalty(); break;
            case "reset":
                if (!w.checkPassword(f.get("current"))) throw new IllegalArgumentException("Incorrect warden password.");
                s.resetPassword(f.get("password"));
                sessions.entrySet().removeIf(e -> e.getValue()==s); break;
            default: throw new IllegalArgumentException("Action not allowed.");
        }
    }

    // The frontend is ordinary HTML forms and CSS; no JavaScript is needed.
    static String page(HostelMember user, String message) {
        StringBuilder b = new StringBuilder("<!doctype html><html lang='en'><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width,initial-scale=1'><title>Hostel Management</title><link rel='stylesheet' href='/style.css'></head><body><header><small>STUDENT LIVING</small><h1>Hostel Management</h1><p>17 rooms &middot; 50 beds &middot; One simple place to manage your stay</p></header><main>");
        if (!message.isEmpty()) b.append("<div class='message' role='status'>").append(h(message)).append("</div>");
        if (user==null) {
            b.append("<div class='grid'><section><h2>Welcome back</h2><p>Choose your role and sign in.</p>");
            b.append(form("login",select("role","Role","student","warden")+input("id","Member ID","number")+input("password","Password","password"),"Log in"));
            b.append("<p class='muted'>Demo warden: 1001 or 1002<br>Password: WARDEN@123</p></section><section><h2>New student</h2><p>Your student ID is assigned automatically.</p>");
            b.append(registration()).append("</section></div>");
        } else {
            b.append("<div class='top'><div><h2>").append(h(user.getName())).append("</h2><p>").append(user instanceof Warden ? "Warden" : "Student").append(" &middot; ID ").append(user.getId()).append("</p></div>").append(form("logout","","Log out")).append("</div>");
            b.append("<section><h2>Notice board</h2>");
            for (String n : notices) b.append("<p>").append(h(n)).append("</p>");
            b.append("</section>");
            if (user instanceof Student) studentPage(b,(Student)user); else wardenPage(b);
            b.append("<section><details><summary>Change my password</summary>").append(form("password",input("current","Current password","password")+input("password","New password (6+ characters)","password"),"Change password")).append("</details></section>");
        }
        b.append("<section><h2>Rooms &amp; semester fees</h2><p>Fees are per student for six months.</p><div class='scroll'><table><tr><th>Room</th><th>Sharing</th><th>Type</th><th>Available beds</th><th>Fee</th></tr>");
        for (Room r : rooms) b.append("<tr><td>").append(r.getRoomNo()).append("</td><td>").append(r.getCapacity()).append("</td><td>").append(r.isAc()?"AC":"Non-AC").append("</td><td>").append(r.getAvailableBeds()).append("</td><td>Rs. ").append(r.getSemesterFee()).append("</td></tr>");
        return b.append("</table></div></section></main><footer>Local classroom project &middot; Data resets when the server stops &middot; Payments are records only</footer></body></html>").toString();
    }

    static void studentPage(StringBuilder b, Student s) {
        b.append("<section><h2>My details</h2>").append(details(s)).append("</section><div class='grid'>");
        if (s.getRoom()==null) {
            String options = "<label>Available room<select name='room'>";
            for (Room r : rooms) if(r.hasSpace()) options += "<option value='"+r.getRoomNo()+"'>"+r.getRoomNo()+" / "+r.getCapacity()+"-sharing / "+(r.isAc()?"AC":"Non-AC")+" / Rs. "+r.getSemesterFee()+"</option>";
            b.append(card("Book a room",form("book",options+"</select></label>","Book room")));
        }
        b.append(card("Record fee payment","<p>Demo record only; no money is transferred.</p>"+form("pay",input("amount","Amount (Rs.)","number")+select("mode","Mode","Cash","UPI","Bank")+input("reference","Transaction / reference ID","text"),"Record payment")));
        b.append(card("Apply for leave",form("leave",input("from","From","date")+input("to","To","date")+input("reason","Reason","text"),"Submit leave")));
        b.append(card("Register visitor",form("visitor",input("name","Visitor name","text"),"Submit visitor"))).append("</div>");
    }

    static void wardenPage(StringBuilder b) {
        b.append("<div class='grid'>").append(card("Register student",registration())).append(card("Post notice",form("notice",input("notice","Notice","text"),"Post notice"))).append("</div><section><h2>Students ("+students.size()+" / 50)</h2>");
        if (students.isEmpty()) b.append("<p>No students registered yet.</p>");
        for (Student s : students) {
            String id = "<input type='hidden' name='student' value='"+s.getId()+"'>";
            b.append("<details><summary>").append(h(s.getName())).append(" &middot; ID ").append(s.getId()).append("</summary>").append(details(s)).append("<div class='grid'>");
            b.append(card("Update details",form("update",id+inputValue("name","Name",s.getName())+inputValue("course","Course",s.getCourse()),"Update")));
            b.append(card("Attendance",form("attendance",id+select("status","Status","Present","Absent"),"Save attendance")));
            b.append(card("Leave decision",form("decide",id+select("decision","Decision","Approved","Rejected"),"Save decision")));
            b.append(card("Visitor",form("approve",id,"Approve visitor")));
            b.append(card("Late fee", "<p>Add Rs. 500 once, only if fees are outstanding.</p>"+form("penalty",id,"Apply late penalty")));
            b.append(card("Reset student password",form("reset",id+input("current","Your warden password","password")+input("password","New student password","password"),"Reset password")));
            b.append("</div></details>");
        }
        b.append("</section>");
    }

    static String details(Student s) {
        Fee fee=s.getFee();
        String out="<p>Course: "+h(s.getCourse())+" | Room: "+(s.getRoom()==null?"Not booked":s.getRoom().getRoomNo())+" | Attendance: "+h(s.getAttendance())+"</p>";
        out+="<p><strong>Total: Rs. "+fee.getTotalFee()+" &nbsp; Paid: Rs. "+(fee.getTotalFee()-fee.getOutstanding())+" &nbsp; Due: Rs. "+fee.getOutstanding()+"</strong></p>";
        out+="<p>Leave: "+h(s.getLeave()==null?"No request":s.getLeave().summary())+"</p><p>Visitor: "+h(s.getVisitor()==null?"No request":s.getVisitor().summary())+"</p><h3>Payment history</h3>";
        if (s.getPayments().isEmpty()) out+="<p>No payments recorded.</p>";
        for (Payment p : s.getPayments()) out+="<p>"+h(p.summary())+"</p>";
        return out;
    }
    static String registration() { return form("register",input("name","Full name","text")+input("course","Course","text")+input("password","Password (6+ characters)","password"),"Register student"); }
    static String card(String title,String content) { return "<section><h2>"+title+"</h2>"+content+"</section>"; }
    static String form(String action,String fields,String button) { return "<form method='post' action='/'><input type='hidden' name='action' value='"+action+"'>"+fields+"<button>"+button+"</button></form>"; }
    static String input(String name,String label,String type) { return "<label>"+label+"<input required name='"+name+"' type='"+type+"' "+(type.equals("number")?"min='0.01' step='any'":"maxlength='250'")+ (type.equals("password")?" autocomplete='off'":"")+"></label>"; }
    static String inputValue(String name,String label,String value) { return "<label>"+label+"<input required maxlength='250' name='"+name+"' value='"+h(value)+"'></label>"; }
    static String select(String name,String label,String... values) {
        String out="<label>"+label+"<select name='"+name+"'>";
        for(String v:values) out+="<option>"+v+"</option>";
        return out+"</select></label>";
    }
    static Student find(int id) { for(Student s:students) if(s.getId()==id) return s; return null; }
    static int number(Map<String,String> f,String key) { return Integer.parseInt(f.get(key)); }
    static String h(String value) { return value.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;").replace("'","&#39;"); }
    static Map<String,String> parse(String raw) {
        Map<String,String> result=new HashMap<>();
        if(raw!=null && !raw.isEmpty()) for(String pair:raw.split("&")) {
            String[] parts=pair.split("=",2);
            result.put(URLDecoder.decode(parts[0],StandardCharsets.UTF_8),parts.length==2?URLDecoder.decode(parts[1],StandardCharsets.UTF_8):"");
        }
        return result;
    }
    static String cookie(HttpExchange x) {
        String raw=x.getRequestHeaders().getFirst("Cookie");
        if(raw!=null) for(String part:raw.split(";")) if(part.trim().startsWith("session=")) return part.trim().substring(8);
        return "";
    }
    static void redirect(HttpExchange x,String message) throws IOException {
        x.getResponseHeaders().set("Location","/?message="+URLEncoder.encode(message,StandardCharsets.UTF_8)); x.sendResponseHeaders(303,-1);
    }
    static void send(HttpExchange x,int status,String type,String body) throws IOException {
        byte[] bytes=body.getBytes(StandardCharsets.UTF_8);
        x.getResponseHeaders().set("Content-Type",type+"; charset=utf-8");
        x.getResponseHeaders().set("Cache-Control","no-store");
        x.getResponseHeaders().set("X-Content-Type-Options","nosniff");
        x.getResponseHeaders().set("Content-Security-Policy","default-src 'self'; style-src 'self'; form-action 'self'; frame-ancestors 'none'");
        x.sendResponseHeaders(status,bytes.length); x.getResponseBody().write(bytes);
    }
}
