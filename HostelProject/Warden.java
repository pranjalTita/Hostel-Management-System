class Warden extends HostelMember {
    Warden(String name, int id) {
        super(name, id);
    }

    @Override
    void showRole() {
        System.out.println("Role: Warden");
    }
}
