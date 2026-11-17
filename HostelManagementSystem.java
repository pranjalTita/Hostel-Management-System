import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HostelManagementSystem {
    public static void main(String[] args) throws Exception {
        System.out.println("========== HOSTEL MANAGEMENT SYSTEM ==========");
        System.out.println("Fixed hostel fee: Rs. " + Fee.SEMESTER_FEE +
                " for " + Fee.PERIOD);
        System.out.println("Five rooms are available, with two beds each (10 students total).");

        // Demonstrate upcasting and downcasting.
        HostelMember member = new Student("Demo Student", 10000, "CSE");
        member.showRole();
        if (member instanceof Student) {
            Student convertedStudent = (Student) member;
            System.out.println("Downcasting example: " + convertedStudent.getName());
        }

        // Demonstrate the Student copy constructor.
        Student originalStudent = new Student("Copy Demo", 99998, "CSE");
        Student copiedStudent = new Student(originalStudent);
        System.out.println("\nCopy constructor demonstration:");
        System.out.print("Original: ");
        originalStudent.display();
        System.out.print("Copied:   ");
        copiedStudent.display();

        // Demonstrate basic-to-class and class-to-basic conversion.
        Room sampleRoom = new Room(999);
        System.out.println("Basic-to-class example: Room " + sampleRoom.getRoomNo());
        Fee sampleFee = new Fee(new Student("Sample", 10001, "CSE"));
        double feeAsBasicValue = sampleFee.getTotalFee();
        System.out.println("Class-to-basic example: Rs. " + feeAsBasicValue);

        new Payment(new Booking(originalStudent, sampleRoom), 100, "Demo", "DEMO")
                .showReceipt("Class-to-class conversion example");
        HostelService service = new HostelService();
        byte[] page = Files.readAllBytes(Path.of("index.html"));
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
        server.createContext("/", exchange -> handle(exchange, page, service));
        server.start();
        System.out.println("Open http://localhost:8080");
    }
    static void checkWardenAttempts(HttpExchange x, HostelService service, Map<String,String> input) {
        String cookies = x.getRequestHeaders().getFirst("Cookie");
        String flow = null;
        if (cookies != null) for (String cookie : cookies.split(";")) {
            String part = cookie.trim();
            if (part.startsWith("wardenFlow=")) flow = part.substring(11);
        }
        if (flow == null || !service.attempts.containsKey(flow)) {
            flow = service.newWardenFlow();
            x.getResponseHeaders().add("Set-Cookie", "wardenFlow=" + flow + "; Path=/api/warden; HttpOnly; SameSite=Strict");
        }
        int left = service.attempts.get(flow);
        if (left == 0) throw new ApiError(401, "Access denied. Reopen the Warden Portal to try again.");
        String id = input.getOrDefault("id", "").trim();
        if (!(id.equals("1001") || id.equals("1002")) || !"WARDEN@123".equals(input.get("password"))) {
            service.attempts.put(flow, --left);
            throw new ApiError(401, left == 0 ? "Access denied. Reopen the Warden Portal to try again." : "Incorrect credentials. Attempts left: " + left);
        }
        service.attempts.remove(flow);
    }
    static void handle(HttpExchange x, byte[] page, HostelService service) throws IOException {
        int status = 200;
        String type = "application/json";
        byte[] body;
        try {
            String method = x.getRequestMethod();
            String path = x.getRequestURI().getPath();
            if (path.equals("/") && method.equals("GET")) {
                body = page;
                type = "text/html";
            } else {
                Map<String,String> input = Map.of();
                if (method.equals("POST")) {
                    String mime = x.getRequestHeaders().getFirst("Content-Type");
                    if (mime == null || !mime.split(";")[0].trim().equalsIgnoreCase("application/json"))
                        throw new ApiError(415, "Send application/json.");
                    byte[] bytes = x.getRequestBody().readNBytes(16385);
                    if (bytes.length > 16384) throw new ApiError(413, "Request too large.");
                    input = Json.parse(new String(bytes, StandardCharsets.UTF_8));
                }
                // The console returned to role selection after three failed warden logins.
                if (path.equals("/api/warden/login") && method.equals("POST"))
                    checkWardenAttempts(x, service, input);
                if (path.equals("/api/warden/start") && method.equals("POST")) {
                    x.getResponseHeaders().add("Set-Cookie", "wardenFlow=" + service.newWardenFlow() + "; Path=/api/warden; HttpOnly; SameSite=Strict");
                }
                Object result = service.route(method, path, input, x.getRequestHeaders().getFirst("Authorization"));
                body = Json.bytes(result);
            }
        } catch (ApiError e) {
            status = e.status;
            body = Json.bytes(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException | IllegalStateException e) {
            status = 400;
            body = Json.bytes(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            status = 500;
            body = Json.bytes(Map.of("error", "Server error."));
        }
        x.getResponseHeaders().set("Content-Type", type + "; charset=utf-8");
        x.getResponseHeaders().set("Cache-Control", "no-store");
        x.sendResponseHeaders(status, body.length);
        try (OutputStream out = x.getResponseBody()) { out.write(body); }
    }
}
class ApiError extends RuntimeException {
    final int status;
    ApiError(int status, String message) { super(message); this.status = status; }
}
