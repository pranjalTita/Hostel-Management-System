// Parent class
class HostelMember {
    protected String name;
    protected int studentId;

    HostelMember(String name, int studentId) {
        this.name = name;
        this.studentId = studentId;
    }

    void showRole() {
        System.out.println("I am a hostel member.");
    }
}
