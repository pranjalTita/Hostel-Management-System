// Room class
class Room {
    private int roomNo;
    private String roomType;
    private int capacity;
    private int floor;
    private boolean available;

    Room() {
        roomNo = 0;
        roomType = "Unknown";
        capacity = 0;
        floor = 0;
        available = true;
    }

    Room(int roomNo, String roomType, int capacity, int floor) {
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.capacity = capacity;
        this.floor = floor;
        this.available = true;
    }

    Room(Room r) {
        this.roomNo = r.roomNo;
        this.roomType = r.roomType;
        this.capacity = r.capacity;
        this.floor = r.floor;
        this.available = r.available;
    }

    void displayRoom() {
        System.out.println("\n--- Room Details ---");
        System.out.println("Room No: " + roomNo);
        System.out.println("Room Type: " + roomType);
        System.out.println("Capacity: " + capacity);
        System.out.println("Floor: " + floor);
        System.out.println("Available: " + available);
    }

    void bookRoom() {
        if (available) {
            available = false;
            System.out.println("Room booked successfully.");
        } else {
            System.out.println("Room is already occupied.");
        }
    }
}
