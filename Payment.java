// Payment class
class Payment {
    private Student student;
    private Fee fee;
    private double amount;
    private String mode;
    private String transactionId;

    Payment(Student student, Fee fee, double amount,
            String mode, String transactionId) {
        this.student = student;
        this.fee = fee;
        this.amount = amount;
        this.mode = mode;
        this.transactionId = transactionId;
    }

    // Method overloading
    void makePayment(double amount) {
        this.amount = amount;
        fee.addPayment(amount);
        System.out.println("Payment made: " + amount);
    }

    void makePayment(double amount, String mode) {
        this.amount = amount;
        this.mode = mode;
        fee.addPayment(amount);
        System.out.println("Payment made: " + amount);
        System.out.println("Payment Mode: " + mode);
    }

    void displayPayment() {
        System.out.println("\n--- Payment Details ---");
        System.out.println("Student: " + student.name);
        System.out.println("Amount: " + amount);
        System.out.println("Mode: " + mode);
        System.out.println("Transaction ID: " + transactionId);
    }
}
