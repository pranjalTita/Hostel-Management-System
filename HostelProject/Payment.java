class Payment {
    private Student student;
    private double amount;
    private String mode;
    private String transactionId;

    Payment(Student student, double amount, String mode, String transactionId) {
        if (student == null) throw new IllegalArgumentException("Student is required.");
        this.student = student;
        if (!Double.isFinite(amount) || amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be finite and positive.");
        }
        this.amount = amount;
        this.mode = HostelMember.requireText(mode, "Payment mode");
        this.transactionId = HostelMember.requireText(transactionId, "Transaction ID");
    }

    private static Booking requireBooking(Booking booking) {
        if (booking == null) throw new IllegalArgumentException("Booking is required.");
        return booking;
    }

    // Class-to-class conversion example
    Payment(Booking booking, double amount, String mode, String transactionId) {
        this(requireBooking(booking).getStudent(), amount, mode, transactionId);
    }

    void display() {
        System.out.println("Student: " + student.getName() +
                " | Amount: Rs. " + amount +
                " | Mode: " + mode +
                " | Transaction: " + transactionId);
    }
    // Method overloading
    void showReceipt() {
        display();
    }
    void showReceipt(String heading) {
        System.out.println("\n" + heading);
        display();
    }
}
