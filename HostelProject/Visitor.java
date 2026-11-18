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
