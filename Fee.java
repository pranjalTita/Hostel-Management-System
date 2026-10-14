// Fee class
class Fee {
    private Student student;
    private double amount;
    private double paidAmount;
    private String dueDate;

    Fee(Student student, double amount, String dueDate) {
        this.student = student;
        this.amount = amount;
        this.dueDate = dueDate;
        this.paidAmount = 0;
    }

    void displayFee() {
        System.out.println("\n--- Fee Details ---");
        System.out.println("Student: " + student.name);
        System.out.println("Total Fee: " + amount);
        System.out.println("Paid: " + paidAmount);
        System.out.println("Due Date: " + dueDate);
        System.out.println("Outstanding: " + (amount - paidAmount));
    }

    void addPayment(double payment) {
        paidAmount += payment;
        System.out.println("Payment added successfully.");
    }
}
