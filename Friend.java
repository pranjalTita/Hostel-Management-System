// Friend / Visitor class
class Friend {
    private int visitorId;
    private String name;
    private String contactNo;
    private Student student;
    private String visitDate;
    private String entryTime;
    private String exitTime;
    private String purpose;
    private boolean approved;
    private boolean insideHostel;

    Friend(int visitorId, String name, String contactNo,
           Student student, String visitDate,
           String entryTime, String purpose) {
        this.visitorId = visitorId;
        this.name = name;
        this.contactNo = contactNo;
        this.student = student;
        this.visitDate = visitDate;
        this.entryTime = entryTime;
        this.purpose = purpose;
        this.approved = false;
        this.insideHostel = false;
    }

    void approveVisit() {
        approved = true;
        insideHostel = true;
        System.out.println("Visitor entry approved.");
    }

    void recordExit(String exitTime) {
        if (insideHostel) {
            this.exitTime = exitTime;
            insideHostel = false;
            System.out.println("Visitor exit recorded.");
        } else {
            System.out.println("Visitor is not currently inside the hostel.");
        }
    }

    void displayVisitor() {
        System.out.println("\n--- Visitor Details ---");
        System.out.println("Visitor ID: " + visitorId);
        System.out.println("Name: " + name);
        System.out.println("Contact No: " + contactNo);
        System.out.println("Student Visited: " + student.name);
        System.out.println("Visit Date: " + visitDate);
        System.out.println("Entry Time: " + entryTime);
        System.out.println("Exit Time: " + exitTime);
        System.out.println("Purpose: " + purpose);
        System.out.println("Approved: " + approved);
        System.out.println("Inside Hostel: " + insideHostel);
    }
}
