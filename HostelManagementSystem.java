import java.util.ArrayList;
import java.util.Scanner;
// Abstract parent class: abstraction, inheritance and runtime polymorphism
abstract class HostelMember {
    // CHANGE 1: private data, accessed through methods.
    private String name;
    private final int id;
    private String password;
    boolean checkPassword(String attempt) {
        return password != null && password.equals(attempt);
    }
    void resetPassword(String newPassword) {
        String checked = requireText(newPassword, "Password");
        if (checked.length() < 6) {
            throw new IllegalArgumentException("Password must have at least 6 characters.");
        }
        password = checked;
    }
    void changePassword(String current, String replacement) {
        if (!checkPassword(current)) {
            throw new IllegalArgumentException("Current password is incorrect.");
        }
        resetPassword(replacement);
    }
    HostelMember(String name, int id) {
        // CHANGE 2: validate before creating the member.
        this.name = requireText(name, "Name");
        if (id <= 0) throw new IllegalArgumentException("ID must be positive.");
        this.id = id;
    }
    static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " cannot be blank.");
        }
        return value.trim();
    }
    String getName() { return name; }
    int getId() { return id; }
    void setName(String name) { this.name = requireText(name, "Name"); }
    // Abstract method: every concrete hostel member must define its role.
    abstract void showRole();
}
class Student extends HostelMember {
    private String course;
    private Room room;
    private Fee fee;
    private ArrayList<Payment> payments = new ArrayList<>();
    private Leave leave;
    private Visitor visitor;
    private String attendance = "Not marked";
    Student(String name, int id, String course) {
        super(name, id);
        if (id < 1 || id > 100) throw new IllegalArgumentException("Student ID must be 1-100.");
        this.course = requireText(course, "Course");
        fee = new Fee(this);
    }

    // Copy constructor: creates a separate Student object with copied basic details.
    // A fresh Fee object is created for the copied student; active records are not shared.
    Student(Student other) {
        super(other.getName(), other.getId());
        this.course = other.course;
        this.attendance = other.attendance;
        this.fee = new Fee(this);
    }

    @Override
    void showRole() {
        System.out.println("Role: Student");
    }

    void display() {
        System.out.println("Name: " + getName() + " | ID: " + getId() +
                " | Course: " + course +
                " | Room: " + (room == null ? "Not allotted" : room.getRoomNo()));
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
        System.out.println(getName() + "'s attendance: " + attendance);
    }

    void updateDetails(String name, String course) {
        String checkedCourse = requireText(course, "Course");
        setName(name);
        this.course = checkedCourse;
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
    private boolean ac;
    private ArrayList<Student> occupants = new ArrayList<>();

    Room(int roomNo, String type, int capacity) {
        // CHANGE 2: rooms must have a valid number, type and capacity.
        if (roomNo <= 0 || capacity <= 0) {
            throw new IllegalArgumentException("Room number and capacity must be positive.");
        }
        this.roomNo = roomNo;
        this.type = HostelMember.requireText(type, "Room type");
        this.capacity = capacity;
    }

    // Overloaded constructor for AC and non-AC rooms.
    Room(int roomNo, int capacity, boolean ac) {
        this(roomNo, "Shared", capacity);
        this.ac = ac;
    }

    double getSemesterFee() {
        double base = capacity == 2 ? 50000 : capacity == 3 ? 40000 : 35000;
        return base + (ac ? 15000 : 0);
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
        if (student == null) throw new IllegalArgumentException("Student is required.");
        if (student.getRoom() != null) throw new IllegalStateException("Student already has a room.");
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
        System.out.println("Room " + roomNo + " | " + capacity + "-sharing | " + (ac ? "AC" : "Non-AC") +
                " | Semester fee: Rs. " + getSemesterFee() +
                " | Capacity: " + capacity +
                " | Occupied: " + occupants.size() +
                " | Beds available: " + getAvailableBeds());
    }
}

class Fee {
    static final String PERIOD = "6 months (one semester)";

    private Student student;
    private double paidAmount = 0;

    Fee(Student student) {
        if (student == null) throw new IllegalArgumentException("Student is required.");
        this.student = student;
    }

    double getTotalFee() {
        return student.getRoom() == null ? 0 : student.getRoom().getSemesterFee();
    }

    double getOutstanding() {
        return getTotalFee() - paidAmount;
    }

    void addPayment(double amount) {
        if (student.getRoom() == null) throw new IllegalStateException("Book a room before paying fees.");
        if (!Double.isFinite(amount) || amount <= 0) {
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
        if (student.getRoom() == null) {
            System.out.println("Fee will be fixed after room booking.");
            return;
        }
        System.out.println("Total fee: Rs. " + getTotalFee());
        System.out.println("Paid: Rs. " + paidAmount);
        System.out.println("Outstanding: Rs. " + getOutstanding());
        System.out.println("Status: " + (getOutstanding() == 0 ? "Paid" : "Pending"));
    }
}

class Payment {
    private Student student;
    private double amount;
    private String mode;
    private String transactionId;

    Payment(Student student, double amount, String mode, String transactionId) {
        if (student == null) throw new IllegalArgumentException("Student is required.");
        this.student = student;
        if (!Double.isFinite(amount) || amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be finite and positive.");
        }
        this.amount = amount;
        this.mode = HostelMember.requireText(mode, "Payment mode");
        this.transactionId = HostelMember.requireText(transactionId, "Transaction ID");
    }

    private static Booking requireBooking(Booking booking) {
        if (booking == null) throw new IllegalArgumentException("Booking is required.");
        return booking;
    }

    // Class-to-class conversion example
    Payment(Booking booking, double amount, String mode, String transactionId) {
        this(requireBooking(booking).getStudent(), amount, mode, transactionId);
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
        if (student == null) throw new IllegalArgumentException("Student is required.");
        this.student = student;
        if (room == null) throw new IllegalArgumentException("Room is required.");
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
    private Student student;
    private String fromDate;
    private String toDate;
    private String reason;
    private String status = "Pending";
    Leave(Student student, String fromDate, String toDate, String reason) {
        if (student == null) throw new IllegalArgumentException("Student is required.");
        this.student = student;
        this.fromDate = HostelMember.requireText(fromDate, "From date");
        this.toDate = HostelMember.requireText(toDate, "To date");
        this.reason = HostelMember.requireText(reason, "Reason");
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
    private Student student;
    private String visitorName;
    private String status = "Pending";
    Visitor(Student student, String visitorName) {
        if (student == null) throw new IllegalArgumentException("Student is required.");
        this.student = student;
        this.visitorName = HostelMember.requireText(visitorName, "Visitor name");
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
    // CHANGE 3: both threads safely update this shared counter.
    private int noticeCount = 0;

    synchronized void postNotice(String notice) {
        noticeCount++;
        System.out.println("[Hostel Notice " + noticeCount + "] " + notice);
    }

    synchronized int getNoticeCount() {
        return noticeCount;
    }
}
class NotificationThread extends Thread {
    private String message;
    private HostelNoticeBoard board;

    NotificationThread(String message, HostelNoticeBoard board) {
        this.message = HostelMember.requireText(message, "Notice");
        if (board == null) throw new IllegalArgumentException("Notice board is required.");
        this.board = board;
    }

    @Override
    public void run() {
        board.postNotice(message);
    }
}

public class HostelManagementSystem {
    private static Scanner sc = new Scanner(System.in);

    private static final int MIN_WARDEN_ID = 1001;
    private static final int MAX_WARDEN_ID = 1002;
    private static final int MAX_STUDENT_ID = 100;
    private static final int MAX_STUDENTS = 50;
    private static int nextStudentId = 1;
    private static Warden[] wardens = {
            new Warden("Warden 1", 1001), new Warden("Warden 2", 1002)
    };
    private static final int MAX_LOGIN_ATTEMPTS = 3;

    private static ArrayList<Student> students = new ArrayList<>();
    private static int authenticatedWardenId;

    // 5 x 2 + 8 x 3 + 4 x 4 = exactly 50 beds.
    private static Room[] rooms = {
            new Room(101, 2, false),
            new Room(102, 2, true),
            new Room(103, 2, false),
            new Room(104, 2, true),
            new Room(105, 2, false),
            new Room(201, 3, false),
            new Room(202, 3, true),
            new Room(203, 3, false),
            new Room(204, 3, true),
            new Room(205, 3, false),
            new Room(206, 3, true),
            new Room(207, 3, false),
            new Room(208, 3, true),
            new Room(301, 4, false),
            new Room(302, 4, true),
            new Room(303, 4, false),
            new Room(304, 4, true)
    };

    static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid whole number.");
            }
        }
    }

    static double readDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                double amount = Double.parseDouble(sc.nextLine().trim());
                if (!Double.isFinite(amount)) {
                    throw new NumberFormatException();
                }
                return amount;
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid amount.");
            }
        }
    }

    static String readText(String message) {
        while (true) {
            System.out.print(message);
            String text = sc.nextLine().trim();
            if (!text.isEmpty()) return text;
            System.out.println("Please enter a value.");
        }
    }

    static Student findStudent(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    static Warden authenticateWarden() {
        System.out.println("\n--- Warden Login ---");
        for (int attempt = 1; attempt <= MAX_LOGIN_ATTEMPTS; attempt++) {
            int id = readInt("Warden ID (1001-1002): ");
            String password = readText("Password: ");
            for (Warden warden : wardens) {
                if (warden.getId() == id && warden.checkPassword(password)) return warden;
            }
            System.out.println("Incorrect credentials. Attempts left: " + (MAX_LOGIN_ATTEMPTS - attempt));
        }
        return null;
    }

    static Student authenticateStudent() {
        for (int attempt = 1; attempt <= MAX_LOGIN_ATTEMPTS; attempt++) {
            int id = readInt("Student ID (1-100): ");
            String password = readText("Password: ");
            Student student = findStudent(id);
            if (student != null && student.checkPassword(password)) return student;
            System.out.println("Incorrect credentials. Attempts left: " + (MAX_LOGIN_ATTEMPTS - attempt));
        }
        return null;
    }

    static String readNewPassword() {
        while (true) {
            String password = readText("New password (at least 6 characters): ");
            String confirmation = readText("Confirm password: ");
            if (password.length() >= 6 && password.equals(confirmation)) return password;
            System.out.println("Passwords must match and have at least 6 characters.");
        }
    }

    static void changeMemberPassword(HostelMember member) {
        String current = readText("Current password: ");
        if (!member.checkPassword(current)) {
            System.out.println("Current password is incorrect.");
            return;
        }
        member.changePassword(current, readNewPassword());
        System.out.println("Password changed successfully.");
    }

    static Student registerStudent() {
        if (students.size() >= MAX_STUDENTS) {
            System.out.println("Hostel capacity is full. No more registrations allowed.");
            return null;
        }

        System.out.println("\n--- Student Registration ---");
        String name = readText("Name: ");
        if (nextStudentId > MAX_STUDENT_ID) {
            System.out.println("No unused student IDs remain.");
            return null;
        }
        int id = nextStudentId;
        String course = readText("Course: ");

        Student student = new Student(name, id, course);
        System.out.println("Create a student password.");
        student.resetPassword(readNewPassword());
        students.add(student);
        nextStudentId++;
        System.out.println("Student registered. System-assigned ID: " + id);
        return student;
    }

    static void showRooms() {
        System.out.println("\n--- Rooms: 2 / 3 / 4-sharing, AC and Non-AC ---");
        for (int i = 0; i < rooms.length; i++) {
            System.out.print((i + 1) + ". ");
            rooms[i].display();
        }
        System.out.println("Total registered students: " + students.size() + "/" + MAX_STUDENTS);
        System.out.println("Total hostel capacity: 50 students.");
    }

    static void bookRoom(Student student) {
        if (student.getRoom() != null) {
            System.out.println("You already have Room " + student.getRoom().getRoomNo() + ".");
            return;
        }

        showRooms();
        int choice = readInt("Select a room (1-" + rooms.length + "): ");
        if (choice < 1 || choice > rooms.length) {
            throw new IllegalArgumentException("Please select a room from 1 to " + rooms.length + ".");
        }

        Room selectedRoom = rooms[choice - 1];
        if (!selectedRoom.hasSpace()) {
            throw new IllegalStateException("Room " + selectedRoom.getRoomNo() + " is full. Choose another room.");
        }

        if (!readText("Confirm booking at Rs. " + selectedRoom.getSemesterFee() +
                " per semester? (yes/no): ").equalsIgnoreCase("yes")) {
            System.out.println("Booking cancelled.");
            return;
        }
        selectedRoom.addStudent(student);
        Booking booking = new Booking(student, selectedRoom);
        System.out.println("Room allotted successfully.");
        booking.display();
    }

    static void studentMenu(Student student) {
        while (true) {
            System.out.println("\n===== STUDENT MENU: " + student.getName() + " =====");
            System.out.println("1. View my details");
            System.out.println("2. View rooms and book a room");
            System.out.println("3. View my fee");
            System.out.println("4. Pay my fee");
            System.out.println("5. Apply for leave");
            System.out.println("6. Register a visitor");
            System.out.println("7. View my attendance");
            System.out.println("8. View leave and visitor status");
            System.out.println("9. Change my password");
            System.out.println("0. Logout");

            int choice = readInt("Choice: ");
            try {
                switch (choice) {
                    case 1:
                        student.display();
                        break;
                    case 2:
                        bookRoom(student);
                        break;
                    case 3:
                        student.getFee().display();
                        break;
                    case 4:
                        double amount = readDouble("Payment amount: Rs. ");
                        String mode = readText("Payment mode (Cash/UPI/Bank): ");
                        String txn = readText("Transaction/reference ID: ");
                        student.getFee().addPayment(amount);

                        Payment payment = new Payment(student, amount, mode, txn);
                        student.addPayment(payment);
                        payment.showReceipt("PAYMENT RECEIPT");
                        student.getFee().display();
                        break;
                    case 5:
                        String from = readText("From date: ");
                        String to = readText("To date: ");
                        String reason = readText("Reason: ");
                        student.setLeave(new Leave(student, from, to, reason));
                        System.out.println("Leave request submitted.");
                        break;
                    case 6:
                        String visitorName = readText("Visitor name: ");
                        student.setVisitor(new Visitor(student, visitorName));
                        System.out.println("Visitor request submitted.");
                        break;
                    case 7:
                        student.showAttendance();
                        break;
                    case 8:
                        if (student.getLeave() == null) System.out.println("No leave request.");
                        else student.getLeave().display();
                        if (student.getVisitor() == null) System.out.println("No visitor request.");
                        else student.getVisitor().display();
                        break;
                    case 9:
                        changeMemberPassword(student);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    static void showAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
        for (Student student : students) {
            student.display();
        }
    }

    static Student chooseStudent() {
        showAllStudents();
        if (students.isEmpty()) {
            return null;
        }
        int id = readInt("Enter student ID: ");
        Student student = findStudent(id);
        if (student == null) {
            System.out.println("Student not found.");
        }
        return student;
    }

    static void wardenMenu(Warden warden, HostelNoticeBoard board) {
        while (true) {
            System.out.println("\n===== WARDEN MENU =====");
            System.out.println("1. Register a new student");
            System.out.println("2. View all students");
            System.out.println("3. View a student's fee and payment history");
            System.out.println("4. View room availability");
            System.out.println("5. Approve/reject a leave request");
            System.out.println("6. Approve a visitor request");
            System.out.println("7. Mark student attendance");
            System.out.println("8. Post hostel notice (thread)");
            System.out.println("9. Update student details");
            System.out.println("10. Change my password");
            System.out.println("11. Reset a student's password");
            System.out.println("12. Show OOP demonstrations");
            System.out.println("0. Logout");

            int choice = readInt("Choice: ");
            switch (choice) {
                case 1:
                    registerStudent();
                    break;
                case 2:
                    showAllStudents();
                    break;
                case 3: {
                    Student student = chooseStudent();
                    if (student != null) {
                        student.getFee().display();
                        System.out.println("--- Payment History ---");
                        student.showPayments();
                    }
                    break;
                }
                case 4:
                    showRooms();
                    break;
                case 5: {
                    Student student = chooseStudent();
                    if (student == null) break;
                    Leave leave = student.getLeave();
                    if (leave == null) {
                        System.out.println("No leave request from this student.");
                        break;
                    }
                    String decision = readText("Approve or reject? ");
                    if (decision.equalsIgnoreCase("approve")) {
                        leave.decide("Approved");
                    } else if (decision.equalsIgnoreCase("reject")) {
                        leave.decide("Rejected");
                    } else {
                        System.out.println("Invalid decision.");
                    }
                    leave.display();
                    break;
                }
                case 6: {
                    Student student = chooseStudent();
                    if (student == null) break;
                    Visitor visitor = student.getVisitor();
                    if (visitor == null) {
                        System.out.println("No visitor request from this student.");
                    } else {
                        String answer = readText("Approve visitor? (yes/no): ");
                        if (answer.equalsIgnoreCase("yes")) visitor.approve();
                        visitor.display();
                    }
                    break;
                }
                case 7: {
                    Student student = chooseStudent();
                    if (student == null) break;
                    String status = readText("Enter present or absent: ");
                    if (status.equalsIgnoreCase("present") ||
                            status.equalsIgnoreCase("absent")) {
                        student.setAttendance(status);
                        student.showAttendance();
                    } else {
                        System.out.println("Invalid attendance status.");
                    }
                    break;
                }
                case 8: {
                    String notice = readText("Enter notice: ");
                    NotificationThread thread = new NotificationThread(notice, board);
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Notice thread interrupted.");
                    }
                    break;
                }
                case 9: {
                    Student student = chooseStudent();
                    if (student == null) break;
                    String newName = readText("New name: ");
                    String newCourse = readText("New course: ");
                    student.updateDetails(newName, newCourse);
                    System.out.println("Student details updated.");
                    break;
                }
                case 10:
                    changeMemberPassword(warden);
                    break;
                case 11: {
                    if (!warden.checkPassword(readText("Your current warden password: "))) {
                        System.out.println("Incorrect password.");
                        break;
                    }
                    Student student = chooseStudent();
                    if (student != null) {
                        student.resetPassword(readNewPassword());
                        System.out.println("Student password reset. Tell the student their new password.");
                    }
                    break;
                }
                case 12:
                    showOopDemonstrations();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static void showOopDemonstrations() {
        // Demonstrate upcasting and downcasting.
        HostelMember member = new Student("Demo Student", 1, "CSE");
        member.showRole();
        if (member instanceof Student) {
            Student convertedStudent = (Student) member;
            System.out.println("Downcasting example: " + convertedStudent.getName());
        }

        // Demonstrate the Student copy constructor.
        Student originalStudent = new Student("Copy Demo", 2, "CSE");
        Student copiedStudent = new Student(originalStudent);
        System.out.println("\nCopy constructor demonstration:");
        System.out.print("Original: ");
        originalStudent.display();
        System.out.print("Copied:   ");
        copiedStudent.display();

        // Demonstrate basic-to-class and class-to-basic conversion.
        Room sampleRoom = new Room(999);
        System.out.println("Basic-to-class example: Room " + sampleRoom.getRoomNo());
        Fee sampleFee = new Fee(new Student("Sample", 3, "CSE"));
        double feeAsBasicValue = sampleFee.getTotalFee();
        System.out.println("Class-to-basic example: Rs. " + feeAsBasicValue);

    }
    public static void main(String[] args) {
        System.out.println("========== HOSTEL MANAGEMENT SYSTEM ==========");
        for (Warden warden : wardens) warden.resetPassword("WARDEN@123");
        System.out.println("17 rooms | 50 beds | 2/3/4-sharing | AC and Non-AC");
        System.out.println("Fees are per student for " + Fee.PERIOD + ".");
        System.out.println("Warden IDs: 1001, 1002 | Initial password: WARDEN@123");
        HostelNoticeBoard board = new HostelNoticeBoard();
        NotificationThread welcome = new NotificationThread(
                "Hostel Management System started.", board);
        // CHANGE 3: start BOTH threads before waiting for either one.
        NotificationThread reminder = new NotificationThread(
                "Please pay your hostel fee on time.", board);
        welcome.start();
        reminder.start();
        try {
            welcome.join();
            reminder.join();
            System.out.println("Notices posted: " + board.getNoticeCount());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        while (true) {
            System.out.println("\n1. Student");
            System.out.println("2. Warden");
            System.out.println("0. Exit");
            int role = readInt("Select role: ");

            if (role == 1) {
                System.out.println("\n1. Already registered (login)");
                System.out.println("2. New registration");
                System.out.println("0. Back");
                int option = readInt("Choice: ");
                Student student = null;
                if (option == 1) {
                    student = authenticateStudent();
                    if (student == null) System.out.println("Access denied. Ask a warden to reset a forgotten password.");
                } else if (option == 2) {
                    student = registerStudent();
                } else if (option != 0) {
                    System.out.println("Invalid choice.");
                }
                if (student != null) {
                    student.showRole();
                    studentMenu(student);
                }
            } else if (role == 2) {
                Warden warden = authenticateWarden();
                if (warden != null) {
                    warden.showRole();
                    wardenMenu(warden, board);
                } else System.out.println("Access denied.");
            } else if (role == 0) {
                break;
            } else {
                System.out.println("Invalid role choice.");
            }
        }
        System.out.println("Thank you for using the Hostel Management System.");
        sc.close();
    }
}