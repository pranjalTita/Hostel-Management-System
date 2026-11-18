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
