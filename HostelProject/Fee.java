class Fee {
    static final String PERIOD = "6 months (one semester)";

    private Student student;
    private double paidAmount = 0;

    Fee(Student student) {
        if (student == null) throw new IllegalArgumentException("Student is required.");
        this.student = student;
    }

    double getTotalFee() {
        return student.getRoom() == null ? 0 : student.getRoom().getSemesterFee();
    }

    double getOutstanding() {
        return getTotalFee() - paidAmount;
    }

    void addPayment(double amount) {
        if (student.getRoom() == null) throw new IllegalStateException("Book a room before paying fees.");
        if (!Double.isFinite(amount) || amount <= 0) {
            throw new IllegalArgumentException("Payment must be greater than zero.");
        }
        if (amount > getOutstanding()) {
            throw new IllegalArgumentException("Payment exceeds the outstanding fee.");
        }
        paidAmount += amount;
    }

    void display() {
        System.out.println("\n--- Fee Details ---");
        System.out.println("Student: " + student.getName() + " (ID: " + student.getId() + ")");
        System.out.println("Fee period: " + PERIOD);
        if (student.getRoom() == null) {
            System.out.println("Fee will be fixed after room booking.");
            return;
        }
        System.out.println("Total fee: Rs. " + getTotalFee());
        System.out.println("Paid: Rs. " + paidAmount);
        System.out.println("Outstanding: Rs. " + getOutstanding());
        System.out.println("Status: " + (getOutstanding() == 0 ? "Paid" : "Pending"));
    }
}
