// Attendance class
class Attendance {
    private int attendanceId;
    private Student student;
    private String date;
    private String time;
    private String status;
    private String wardenName;

    Attendance(int attendanceId, Student student,
               String date, String time, String wardenName) {
        this.attendanceId = attendanceId;
        this.student = student;
        this.date = date;
        this.time = time;
        this.wardenName = wardenName;
        this.status = "Not Marked";
    }

    void markPresent() {
        status = "Present";
        System.out.println("Attendance marked Present.");
    }

    void markAbsent() {
        status = "Absent";
        System.out.println("Attendance marked Absent.");
    }

    void displayAttendance() {
        System.out.println("\n--- Attendance Details ---");
        System.out.println("Attendance ID: " + attendanceId);
        System.out.println("Student: " + student.name);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        System.out.println("Status: " + status);
        System.out.println("Warden: " + wardenName);
    }
}
