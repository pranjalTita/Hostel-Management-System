// Student class
class Student extends HostelMember {
    private String course;

    Student(String name, int studentId, String course) {
        super(name, studentId);
        this.course = course;
    }

    // Copy constructor
    Student(Student s) {
        super(s.name, s.studentId);
        this.course = s.course;
    }

    void displayStudent() {
        System.out.println("\n--- Student Details ---");
        System.out.println("Name: " + name);
        System.out.println("Student ID: " + studentId);
        System.out.println("Course: " + course);
    }

    @Override
    void showRole() {
        System.out.println("I am a student.");
    }
}
