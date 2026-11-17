import java.util.*;

class HostelService {
    private final ArrayList<Student> students = new ArrayList<>();
    private final Room[] rooms = {new Room(101), new Room(102), new Room(201), new Room(202), new Room(301)};
    private final Map<String, HostelMember> sessions = new HashMap<>();
    private final ArrayList<String> notices = new ArrayList<>();
    private final HostelNoticeBoard board = new HostelNoticeBoard() {
        @Override synchronized void postNotice(String message) {
            super.postNotice(message);
            notices.add(message);
        }
    };
    final Map<String,Integer> attempts = new HashMap<>();
    String newWardenFlow() {
        String flow = UUID.randomUUID().toString();
        attempts.put(flow, 3);
        return flow;
    }
    static String field(Map<String,String> data, String key) {
        String value = data.getOrDefault(key,"").trim();
        if (value.isEmpty()) throw new ApiError(400,"Enter a value for " + key + ".");
        return value;
    }
    static int number(Map<String,String> data, String key) {
        try { return Integer.parseInt(field(data,key)); }
        catch (NumberFormatException e) { throw new ApiError(400,"Enter a whole number for "+key+"."); }
    }
    Student student(int id) {
        return students.stream().filter(s -> s.getId()==id).findFirst()
            .orElseThrow(() -> new ApiError(404,"Student ID not found."));
    }
    Student register(Map<String,String> data) {
        int id=number(data,"id");
        if(id<10000 || id>10100) throw new ApiError(400,"Student ID must be 10000–10100.");
        if(students.stream().anyMatch(s -> s.getId()==id)) throw new ApiError(409,"Student ID already registered.");
        if(students.size()>=10) throw new ApiError(409,"Hostel registration is full.");
        Student s=new Student(field(data,"name"),id,field(data,"course"));
        students.add(s);
        return s;
    }
    Object login(HostelMember member) {
        String token=UUID.randomUUID().toString();
        sessions.put("Bearer "+token,member);
        return Map.of("token",token,"role",member instanceof Student ? "student":"warden");
    }
    // Serialize each operation, including capacity checks, payment recording and snapshots.
    synchronized Object route(String method,String path,Map<String,String> data,String auth) throws InterruptedException {
        String route=method+" "+path;
        switch(route) {
            case "POST /api/warden/start": return Map.of();
            case "GET /api/notices": return List.copyOf(notices);
            case "POST /api/register": return login(register(data));
            case "POST /api/student/login": return login(student(number(data,"id")));
            case "POST /api/warden/login":
                int id=number(data,"id");
                if(id<1001 || id>1002 || !"WARDEN@123".equals(data.get("password")))
                    throw new ApiError(401,"Incorrect warden credentials.");
                return login(new Warden("Warden",id));
        }
        HostelMember member=sessions.get(auth);
        if(member==null) throw new ApiError(401,"Please sign in.");
        if(route.equals("POST /api/logout")) { sessions.remove(auth); return Map.of(); }
        if(route.equals("GET /api/rooms")) return Arrays.stream(rooms)
            .map(r->Map.of("number",r.getRoomNo(),"free",r.getAvailableBeds(),"capacity",2)).toList();
        if(path.startsWith("/api/warden/")) {
            if(!(member instanceof Warden)) throw new ApiError(403,"Warden access required.");
            return warden(route,data);
        }
        if(!(member instanceof Student s)) throw new ApiError(403,"Student access required.");
        switch(route) {
            case "GET /api/profile": return profile(s);
            case "GET /api/fee": return fee(s);
            case "POST /api/book":
                if(s.getRoom()!=null) throw new ApiError(409,"You already have a room.");
                int roomNo=number(data,"room");
                Room room=Arrays.stream(rooms).filter(r->r.getRoomNo()==roomNo).findFirst()
                    .orElseThrow(()->new ApiError(404,"Room not found."));
                room.addStudent(s);
                new Booking(s,room).display(); break;
            case "POST /api/pay":
                double amount=Double.parseDouble(field(data,"amount"));
                if(!Double.isFinite(amount)) throw new ApiError(400,"Enter a finite amount.");
                Payment payment=new Payment(s,amount,field(data,"mode"),field(data,"transaction"));
                s.getFee().addPayment(amount);
                s.addPayment(payment);
                return receipt(s,payment);
            case "POST /api/leave":
                s.setLeave(new Leave(s,field(data,"from"),field(data,"to"),field(data,"reason"))); break;
            case "POST /api/visitor": s.setVisitor(new Visitor(s,field(data,"name"))); break;
            default: throw new ApiError(404,"Unknown endpoint.");
        }
        return profile(s);
    }
    Object warden(String route,Map<String,String> data) throws InterruptedException {
        switch(route) {
            case "GET /api/warden/students": return students.stream().map(this::profile).toList();
            case "POST /api/warden/register": return profile(register(data));
            case "POST /api/warden/notice":
                NotificationThread thread=new NotificationThread(field(data,"message"),board);
                thread.start(); thread.join(); return List.copyOf(notices);
        }
        Student s=student(number(data,"id"));
        switch(route) {
            case "POST /api/warden/edit": s.updateDetails(field(data,"name"),field(data,"course")); break;
            case "POST /api/warden/attendance":
                String status=field(data,"status");
                if(!Set.of("present","absent").contains(status.toLowerCase(Locale.ROOT)))
                    throw new ApiError(400,"Choose Present or Absent.");
                s.setAttendance(status); break;
            case "POST /api/warden/leave":
                Leave l=s.getLeave();
                if(l==null || !l.getStatus().equals("Pending")) throw new ApiError(409,"No pending leave.");
                l.decide(decision(data)); break;
            case "POST /api/warden/visitor":
                Visitor v=s.getVisitor();
                if(v==null || !v.getStatus().equals("Pending")) throw new ApiError(409,"No pending visitor.");
                if(decision(data).equals("Approved")) v.approve(); else v.reject();
                break;
            default: throw new ApiError(404,"Unknown endpoint.");
        }
        return profile(s);
    }
    String decision(Map<String,String> data) {
        String value=field(data,"decision");
        if(!Set.of("Approved","Rejected").contains(value)) throw new ApiError(400,"Invalid decision.");
        return value;
    }
    Map<String,Object> receipt(Student s, Payment p) {
        return Map.of("student",s.getName(),"id",s.getId(),"amount",p.getAmount(),"mode",p.getMode(),"transaction",p.getTransactionId());
    }
    Object fee(Student s) {
        Fee f=s.getFee();
        return Map.of("total",f.getTotalFee(),"due",f.getOutstanding(),"period",Fee.PERIOD,
            "payments",s.getPayments().stream().map(p->receipt(s,p)).toList());
    }
    Object profile(Student s) {
        Map<String,Object> p=new LinkedHashMap<>();
        p.put("id",s.getId()); p.put("name",s.getName()); p.put("course",s.getCourse());
        p.put("room",s.getRoom()==null ? null : s.getRoom().getRoomNo());
        p.put("attendance",s.getAttendance()); p.put("fee",fee(s));
        Leave l=s.getLeave(); Visitor v=s.getVisitor();
        p.put("leave",l==null ? null : Map.of("from",l.getFromDate(),"to",l.getToDate(),"reason",l.getReason(),"status",l.getStatus()));
        p.put("visitor",v==null ? null : Map.of("name",v.getVisitorName(),"status",v.getStatus()));
        return p;
    }
}
