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
