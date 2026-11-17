import java.util.ArrayList;
import java.util.*;
// Abstract parent class: abstraction, inheritance and runtime polymorphism
abstract class HostelMember {
    protected String name;
    protected int id;
    HostelMember(String name, int id) {
        this.name = name;
        this.id = id;
    }
    // Abstract method: every concrete hostel member must define its role.
    abstract void showRole();
}
class Student extends HostelMember {
    String getCourse() { return course; }
    String getAttendance() { return attendance; }
    List<Payment> getPayments() { return List.copyOf(payments); }

    private String course;
    private Room room;
    private Fee fee;
    private ArrayList<Payment> payments = new ArrayList<>();
    private Leave leave;
    private Visitor visitor;
    private String attendance = "Not marked";

    Student(String name, int id, String course) {
        super(name, id);
        this.course = course;
        fee = new Fee(this);
    }

    // Copy constructor: creates a separate Student object with copied basic details.
    // A fresh Fee object is created for the copied student; active records are not shared.
    Student(Student other) {
        super(other.name, other.id);
        this.course = other.course;
        this.attendance = other.attendance;
        this.fee = new Fee(this);
    }

    @Override
    void showRole() {
        System.out.println("Role: Student");
    }

    void display() {
        System.out.println("Name: " + name + " | ID: " + id +
                " | Course: " + course +
                " | Room: " + (room == null ? "Not allotted" : room.getRoomNo()));
    }

    int getId() {
        return id;
    }

    String getName() {
        return name;
    }

    Fee getFee() {
        return fee;
    }

    Room getRoom() {
        return room;
    }

    void setRoom(Room room) {
        this.room = room;
    }

    void addPayment(Payment payment) {
        payments.add(payment);
    }

    void showPayments() {
        if (payments.isEmpty()) {
            System.out.println("No payments recorded.");
            return;
        }
        for (Payment payment : payments) {
            payment.display();
        }
    }

    void setLeave(Leave leave) {
        this.leave = leave;
    }

    Leave getLeave() {
        return leave;
    }

    void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    Visitor getVisitor() {
        return visitor;
    }

    void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    void showAttendance() {
        System.out.println(name + "'s attendance: " + attendance);
    }

    void updateDetails(String name, String course) {
        this.name = name;
        this.course = course;
    }
}

class Warden extends HostelMember {
    Warden(String name, int id) {
        super(name, id);
    }

    @Override
    void showRole() {
        System.out.println("Role: Warden");
    }
}

class Room {
    private int roomNo;
    private String type;
    private int capacity;
    private ArrayList<Student> occupants = new ArrayList<>();

    Room(int roomNo, String type, int capacity) {
        this.roomNo = roomNo;
        this.type = type;
        this.capacity = capacity;
    }

    // Basic-to-class conversion demonstration
    Room(int roomNo) {
        this(roomNo, "Shared", 2);
    }

    int getRoomNo() {
        return roomNo;
    }

    int getAvailableBeds() {
        return capacity - occupants.size();
    }

    boolean hasSpace() {
        return occupants.size() < capacity;
    }

    void addStudent(Student student) {
        if (!hasSpace()) {
            throw new IllegalStateException("This room is full.");
        }
        occupants.add(student);
        student.setRoom(this);
    }

    void removeStudent(Student student) {
        occupants.remove(student);
        student.setRoom(null);
    }

    void display() {
        System.out.println("Room " + roomNo + " | " + type +
                " | Capacity: " + capacity +
                " | Occupied: " + occupants.size() +
                " | Beds available: " + getAvailableBeds());
    }
}

class Fee {
    static final double SEMESTER_FEE = 50000.0;
    static final String PERIOD = "6 months (one semester)";

    private Student student;
    private final double totalFee = SEMESTER_FEE;
    private double paidAmount = 0;

    Fee(Student student) {
        this.student = student;
    }

    double getTotalFee() {
        return totalFee;
    }

    double getOutstanding() {
        return totalFee - paidAmount;
    }

    void addPayment(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment must be greater than zero.");
        }
        if (amount > getOutstanding()) {
            throw new IllegalArgumentException("Payment exceeds the outstanding fee.");
        }
        paidAmount += amount;
    }

    void display() {
        System.out.println("\n--- Fee Details ---");
        System.out.println("Student: " + student.getName() + " (ID: " + student.getId() + ")");
        System.out.println("Fee period: " + PERIOD);
        System.out.println("Total fee: Rs. " + totalFee);
        System.out.println("Paid: Rs. " + paidAmount);
        System.out.println("Outstanding: Rs. " + getOutstanding());
        System.out.println("Status: " + (getOutstanding() == 0 ? "Paid" : "Pending"));
    }
}

class Payment {
    double getAmount() { return amount; }
    String getMode() { return mode; }
    String getTransactionId() { return transactionId; }

    private Student student;
    private double amount;
    private String mode;
    private String transactionId;

    Payment(Student student, double amount, String mode, String transactionId) {
        this.student = student;
        this.amount = amount;
        this.mode = mode;
        this.transactionId = transactionId;
    }

    // Class-to-class conversion example
    Payment(Booking booking, double amount, String mode, String transactionId) {
        this(booking.getStudent(), amount, mode, transactionId);
    }

    void display() {
        System.out.println("Student: " + student.getName() +
                " | Amount: Rs. " + amount +
                " | Mode: " + mode +
                " | Transaction: " + transactionId);
    }
    // Method overloading
    void showReceipt() {
        display();
    }
    void showReceipt(String heading) {
        System.out.println("\n" + heading);
        display();
    }
}
class Booking {
    private Student student;
    private Room room;
    Booking(Student student, Room room) {
        this.student = student;
        this.room = room;
    }
    Student getStudent() {
        return student;
    }
    void display() {
        System.out.println(student.getName() + " is allotted Room " + room.getRoomNo());
    }
}
class Leave {
    String getFromDate() { return fromDate; }
    String getToDate() { return toDate; }
    String getReason() { return reason; }
    String getStatus() { return status; }

    private Student student;
    private String fromDate;
    private String toDate;
    private String reason;
    private String status = "Pending";
    Leave(Student student, String fromDate, String toDate, String reason) {
        this.student = student;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
    }
    void decide(String decision) {
        status = decision;
    }
    void display() {
        System.out.println(student.getName() + " | " + fromDate + " to " + toDate +
                " | " + reason + " | Status: " + status);
    }
}
class Visitor {
    String getVisitorName() { return visitorName; }
    String getStatus() { return status; }
    void reject() { status = "Rejected"; }

    private Student student;
    private String visitorName;
    private String status = "Pending";
    Visitor(Student student, String visitorName) {
        this.student = student;
        this.visitorName = visitorName;
    }
    void approve() {
        status = "Approved";
    }
    void display() {
        System.out.println(visitorName + " visiting " + student.getName() +
                " | Status: " + status);
    }
}
class HostelNoticeBoard {
    // Synchronization: only one thread can post a notice at a time.
    synchronized void postNotice(String notice) {
        System.out.println("[Hostel Notice] " + notice);
    }
}
class NotificationThread extends Thread {
    private String message;
    private HostelNoticeBoard board;

    NotificationThread(String message, HostelNoticeBoard board) {
        this.message = message;
        this.board = board;
    }

    @Override
    public void run() {
        board.postNotice(message);
    }
}

