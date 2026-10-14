// Booking class
class Booking {
    private Student student;
    private Room room;
    private String duration;
    private String status;

    Booking(Student student, Room room, String duration) {
        this.student = student;
        this.room = room;
        this.duration = duration;
        this.status = "Confirmed";
    }

    void displayBooking() {
        System.out.println("\n--- Booking Details ---");
        System.out.println("Student: " + student.name);
        System.out.println("Duration: " + duration);
        System.out.println("Status: " + status);
    }

    void cancelBooking() {
        status = "Cancelled";
        System.out.println("Booking cancelled.");
    }
}
