// Leave class
class Leave {
    private Student student;
    private String fromDate;
    private String toDate;
    private String reason;
    private String status;

    Leave(Student student, String fromDate, String toDate, String reason) {
        this.student = student;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
        this.status = "Pending";
    }

    void approveLeave() {
        status = "Approved";
    }

    void rejectLeave() {
        status = "Rejected";
    }

    void displayLeave() {
        System.out.println("\n--- Leave Details ---");
        System.out.println("Student: " + student.name);
        System.out.println("From: " + fromDate);
        System.out.println("To: " + toDate);
        System.out.println("Reason: " + reason);
        System.out.println("Status: " + status);
    }
}
