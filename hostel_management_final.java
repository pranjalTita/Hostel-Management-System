import java.util.Scanner;

// Main class
public class hostel_management_final {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. Student
        System.out.println("=== STUDENT DETAILS ===");

        System.out.print("Enter student name: ");
        String name = sc.nextLine();

        System.out.print("Enter student ID: ");
        int studentId = Integer.parseInt(sc.nextLine());

        System.out.print("Enter course: ");
        String course = sc.nextLine();

        Student s1 = new Student(name, studentId, course);
        s1.displayStudent();

        // Copy constructor
        Student s2 = new Student(s1);
        System.out.println("\n=== COPIED STUDENT ===");
        s2.displayStudent();

        // 2. Room selection
        System.out.println("\n=== AVAILABLE ROOMS ===");
        System.out.println("1. Room 101 - Single - Capacity 1 - Floor 1");
        System.out.println("2. Room 102 - Double - Capacity 2 - Floor 1");
        System.out.println("3. Room 201 - Triple - Capacity 3 - Floor 2");

        System.out.print("Select a room (1-3): ");
        int choice = Integer.parseInt(sc.nextLine());

        Room r1;

        switch (choice) {
            case 1:
                r1 = new Room(101, "Single", 1, 1);
                break;
            case 2:
                r1 = new Room(102, "Double", 2, 1);
                break;
            case 3:
                r1 = new Room(201, "Triple", 3, 2);
                break;
            default:
                System.out.println("Invalid choice! Selecting Room 101.");
                r1 = new Room(101, "Single", 1, 1);
        }

        r1.displayRoom();
        r1.bookRoom();

        // 3. Booking
        System.out.println("\n=== BOOKING ===");

        System.out.print("Enter booking duration: ");
        String duration = sc.nextLine();

        Booking b1 = new Booking(s1, r1, duration);
        b1.displayBooking();

        // 4. Fee
        System.out.println("\n=== FEE DETAILS ===");

        System.out.print("Enter total fee: ");
        double totalFee = Double.parseDouble(sc.nextLine());

        System.out.print("Enter fee due date: ");
        String dueDate = sc.nextLine();

        Fee f1 = new Fee(s1, totalFee, dueDate);
        f1.displayFee();

        // 5. Payment
        System.out.println("\n=== PAYMENT ===");

        System.out.print("Enter payment amount: ");
        double amount = Double.parseDouble(sc.nextLine());

        System.out.print("Enter payment mode: ");
        String mode = sc.nextLine();

        System.out.print("Enter transaction ID: ");
        String transactionId = sc.nextLine();

        Payment p1 = new Payment(
                s1, f1, amount, mode, transactionId
        );

        p1.makePayment(amount, mode);
        p1.displayPayment();
        f1.displayFee();

        // Method overloading
        System.out.println("\n=== METHOD OVERLOADING ===");

        System.out.print("Enter another payment amount: ");
        double anotherPayment = Double.parseDouble(sc.nextLine());

        p1.makePayment(anotherPayment);
        p1.displayPayment();
        f1.displayFee();

        // 6. Leave
        System.out.println("\n=== LEAVE REQUEST ===");

        System.out.print("Enter leave start date: ");
        String fromDate = sc.nextLine();

        System.out.print("Enter leave end date: ");
        String toDate = sc.nextLine();

        System.out.print("Enter reason: ");
        String reason = sc.nextLine();

        Leave l1 = new Leave(s1, fromDate, toDate, reason);
        l1.displayLeave();

        System.out.print("Enter decision (approve/reject): ");
        String decision = sc.nextLine();

        if (decision.equalsIgnoreCase("approve")) {
            l1.approveLeave();
        } else if (decision.equalsIgnoreCase("reject")) {
            l1.rejectLeave();
        }

        l1.displayLeave();

        // 7. Runtime polymorphism
        System.out.println("\n=== RUNTIME POLYMORPHISM ===");

        HostelMember member = s1;
        member.showRole();

        // 8. Visitor
        System.out.println("\n=== VISITOR DETAILS ===");

        System.out.print("Enter visitor ID: ");
        int visitorId = Integer.parseInt(sc.nextLine());

        System.out.print("Enter visitor name: ");
        String visitorName = sc.nextLine();

        System.out.print("Enter contact number: ");
        String contactNo = sc.nextLine();

        System.out.print("Enter visit date: ");
        String visitDate = sc.nextLine();

        System.out.print("Enter entry time: ");
        String entryTime = sc.nextLine();

        System.out.print("Enter purpose of visit: ");
        String purpose = sc.nextLine();

        Friend friend1 = new Friend(
                visitorId, visitorName, contactNo,
                s1, visitDate, entryTime, purpose
        );

        friend1.displayVisitor();

        System.out.print("Approve visitor? (yes/no): ");
        String approval = sc.nextLine();

        if (approval.equalsIgnoreCase("yes")) {
            friend1.approveVisit();

            System.out.print("Enter exit time: ");
            String exitTime = sc.nextLine();

            friend1.recordExit(exitTime);
        }

        friend1.displayVisitor();

        // 9. Attendance
        System.out.println("\n=== ATTENDANCE ===");

        System.out.print("Enter attendance ID: ");
        int attendanceId = Integer.parseInt(sc.nextLine());

        System.out.print("Enter date: ");
        String date = sc.nextLine();

        System.out.print("Enter time: ");
        String time = sc.nextLine();

        System.out.print("Enter warden name: ");
        String wardenName = sc.nextLine();

        Attendance a1 = new Attendance(
                attendanceId, s1, date, time, wardenName
        );

        System.out.print("Enter attendance (present/absent): ");
        String attendanceStatus = sc.nextLine();

        if (attendanceStatus.equalsIgnoreCase("present")) {
            a1.markPresent();
        } else if (attendanceStatus.equalsIgnoreCase("absent")) {
            a1.markAbsent();
        } else {
            System.out.println("Invalid attendance status.");
        }

        a1.displayAttendance();

        // Cancel booking
        System.out.print("Cancel booking? (yes/no): ");

        if (sc.nextLine().equalsIgnoreCase("yes")) {
            b1.cancelBooking();
            b1.displayBooking();
        }

        sc.close();

        System.out.println("\nHostel Management System finished.");
    }
}
