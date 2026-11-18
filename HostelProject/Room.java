import java.util.ArrayList;

class Room {
    private int roomNo;
    private String type;
    private int capacity;
    private boolean ac;
    private ArrayList<Student> occupants = new ArrayList<>();

    Room(int roomNo, String type, int capacity) {
        // CHANGE 2: rooms must have a valid number, type and capacity.
        if (roomNo <= 0 || capacity <= 0) {
            throw new IllegalArgumentException("Room number and capacity must be positive.");
        }
        this.roomNo = roomNo;
        this.type = HostelMember.requireText(type, "Room type");
        this.capacity = capacity;
    }

    // Overloaded constructor for AC and non-AC rooms.
    Room(int roomNo, int capacity, boolean ac) {
        this(roomNo, "Shared", capacity);
        this.ac = ac;
    }

    double getSemesterFee() {
        double base = capacity == 2 ? 50000 : capacity == 3 ? 40000 : 35000;
        return base + (ac ? 15000 : 0);
    }

    // Basic-to-class conversion demonstration
    Room(int roomNo) {
        this(roomNo, "Shared", 2);
    }

    int getRoomNo() {
        return roomNo;
    }

    int getAvailableBeds() {
        return capacity - occupants.size();
    }

    boolean hasSpace() {
        return occupants.size() < capacity;
    }

    void addStudent(Student student) {
        if (student == null) throw new IllegalArgumentException("Student is required.");
        if (student.getRoom() != null) throw new IllegalStateException("Student already has a room.");
        if (!hasSpace()) {
            throw new IllegalStateException("This room is full.");
        }
        occupants.add(student);
        student.setRoom(this);
    }

    void removeStudent(Student student) {
        occupants.remove(student);
        student.setRoom(null);
    }

    void display() {
        System.out.println("Room " + roomNo + " | " + capacity + "-sharing | " + (ac ? "AC" : "Non-AC") +
                " | Semester fee: Rs. " + getSemesterFee() +
                " | Capacity: " + capacity +
                " | Occupied: " + occupants.size() +
                " | Beds available: " + getAvailableBeds());
    }
}
