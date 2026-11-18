# CRC Cards - Hostel Management System

## 1. Surviving Classes
- HostelMember (abstract)
- Student
- Warden
- Room
- Booking
- Fee
- Payment
- Leave
- Visitor
- HostelNoticeBoard
- NotificationThread

Note: Attendance is stored as a field of Student (Present / Absent / Not marked), so it is not a separate class. `HostelManagementSystem` is the main class that holds the menus; it is not a domain class.

---

## 2. CRC Cards

### HostelMember (abstract)

*Responsibilities:*
- Keep name, ID and password private (encapsulation) and give access through methods.
- Validate that the name is not blank and the ID is positive.
- Check a password, reset it (at least 6 characters) and change it after verifying the current one.
- Force every subclass to define its role through the abstract method showRole().

*Collaborators:*
- Student
- Warden

---

### Student (extends HostelMember)

*Responsibilities:*
- Store course and validate that the student ID is between 1 and 100.
- Hold the allotted Room and its own Fee record.
- Keep the list of own Payments.
- Submit a leave request and register a visitor.
- Store and show own attendance status.
- Update name and course with validation.
- Create a copy of itself with the copy constructor.

*Collaborators:*
- Room
- Fee
- Payment
- Leave
- Visitor

---

### Warden (extends HostelMember)

*Responsibilities:*
- Define its role as Warden.
- Log in with a warden ID (1001-1002) and password.
- Represent the logged-in warden who manages the hostel (leave, visitors, attendance, notices, passwords, late penalty).

*Collaborators:*
- Student
- Leave
- Visitor
- Fee
- HostelNoticeBoard

---

### Room

*Responsibilities:*
- Store room number, type, capacity (2, 3 or 4 sharing) and AC / Non-AC.
- Calculate the semester fee of the room (2-sharing Rs. 50000, 3-sharing Rs. 40000, 4-sharing Rs. 35000, plus Rs. 15000 for AC).
- Keep the list of students living in the room and report available beds.
- Add or remove a student and update the student's room.

*Collaborators:*
- Student
- Booking
- Fee

---

### Booking

*Responsibilities:*
- Link one Student with the Room allotted to the student.
- Check that the student and the room are not null.
- Display the allotment message.
- Provide the Student for a Payment created from a Booking.

*Collaborators:*
- Student
- Room
- Payment

---

### Fee

*Responsibilities:*
- Get the room fee from the student's Room (fee is fixed only after a room is booked).
- Add the Rs. 500 late penalty once, when the Warden applies it.
- Track the paid amount and calculate the outstanding amount.
- Validate and add a payment (room must be booked, amount above zero and not above the outstanding amount).
- Display fee details and status (Paid / Pending).

*Collaborators:*
- Student
- Room
- Payment

---

### Payment

*Responsibilities:*
- Record a payment (amount, mode, transaction ID) after validating each value.
- Display the payment and print the receipt.
- Be created from a Student or from a Booking.

*Collaborators:*
- Student
- Fee
- Booking

---

### Leave

*Responsibilities:*
- Represent a student's leave request (from date, to date, reason, all required).
- Track the status (Pending, Approved, Rejected).
- Record the Warden's decision.

*Collaborators:*
- Student
- Warden

---

### Visitor

*Responsibilities:*
- Store the visitor's name and the student being visited.
- Track the status of the request (Pending, Approved).
- Be approved by the Warden.

*Collaborators:*
- Student
- Warden

---

### HostelNoticeBoard

*Responsibilities:*
- Post a hostel notice and count the notices posted.
- Allow only one thread at a time to post or read the count (synchronized).

*Collaborators:*
- NotificationThread
- Warden

---

### NotificationThread (extends Thread)

*Responsibilities:*
- Run in a separate thread and post a message on the notice board.
- Validate that the message and the board are provided.

*Collaborators:*
- HostelNoticeBoard

---

## 3. Class Relationships

- Student and Warden inherit from the abstract class HostelMember.
- NotificationThread inherits from Thread.
- The hostel has 17 rooms (5 two-sharing, 8 three-sharing, 4 four-sharing) with 50 beds in total.
- A Room holds 2, 3 or 4 Students depending on its capacity, and a Student lives in zero or one Room.
- A Booking links exactly one Student with one Room.
- Each Student has exactly one Fee, and the amount of the Fee depends on the student's Room.
- A Student can make zero or more Payments, and every Payment reduces the outstanding Fee.
- The Warden can apply a one-time late penalty to a Fee.
- A Student can have a Leave request, which the Warden approves or rejects.
- A Student can register a Visitor, which the Warden approves.
- The Warden marks each Student's attendance and can reset a Student's password.
- Two NotificationThreads post notices to the same HostelNoticeBoard at start-up, and the Warden posts more through the menu.
