import java.util.ArrayList;

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
