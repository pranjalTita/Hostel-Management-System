abstract class HostelMember {
    // CHANGE 1: private data, accessed through methods.
    private String name;
    private final int id;
    private String password;

    boolean checkPassword(String attempt) {
        return password != null && password.equals(attempt);
    }

    void resetPassword(String newPassword) {
        String checked = requireText(newPassword, "Password");
        if (checked.length() < 6) {
            throw new IllegalArgumentException("Password must have at least 6 characters.");
        }
        password = checked;
    }

    void changePassword(String current, String replacement) {
        if (!checkPassword(current)) {
            throw new IllegalArgumentException("Current password is incorrect.");
        }
        resetPassword(replacement);
    }

    HostelMember(String name, int id) {
        // CHANGE 2: validate before creating the member.
        this.name = requireText(name, "Name");
        if (id <= 0) throw new IllegalArgumentException("ID must be positive.");
        this.id = id;
    }
    static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " cannot be blank.");
        }
        return value.trim();
    }

    String getName() { return name; }
    int getId() { return id; }
    void setName(String name) { this.name = requireText(name, "Name"); }

    // Abstract method: every concrete hostel member must define its role.
    abstract void showRole();
}
