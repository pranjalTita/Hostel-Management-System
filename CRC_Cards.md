# CRC Cards - Hostel Management System

## 1. Surviving Classes
- Student
- Room
- Booking
- Fee
- Payment
- Leave
- Attendance
- Friend
---

## 2. CRC Cards

### Student

*Responsibilities:*
- Store personal and enrollment details of the student.
- Request a room booking.
- View assigned room and fee details.
- Submit a leave request.
- Track own payment history.
- Register and manage friend/visitor details.
- View own attendance record.
- Calculate own attendance percentage from Attendance records.

*Collaborators:*
- Booking
- Fee
- Leave
- Attendance
- Friend

---

### Room

*Responsibilities:*
- Store room details (number, type, capacity, floor).
- Maintain current availability status.
- Provide room information for a booking request.
- Update occupancy when a booking is made or vacated.

*Collaborators:*
- Booking
- Student

---

### Booking

*Responsibilities:*
- Create and manage a student's room booking.
- Associate a student with a specific room.
- Store booking-related information (dates, duration, status).
- Update room availability on confirmation or cancellation.
- Trigger fee generation once a booking is confirmed.

*Collaborators:*
- Student
- Room
- Fee

---

### Fee

*Responsibilities:*
- Calculate and store the hostel fee associated with a student.
- Maintain fee structure (amount, due date, category).
- Track outstanding and paid amounts.
- Link fee records to payments made against them.

*Collaborators:*
- Student
- Payment
- Booking

---

### Payment

*Responsibilities:*
- Record a payment made towards a student's hostel fee.
- Store payment details (amount, date, mode, transaction ID).
- Update the fee's paid/outstanding status.
- Generate payment receipts.

*Collaborators:*
- Fee
- Student

---

### Leave

*Responsibilities:*
- Represent a student's leave request.
- Store leave details (dates, reason, duration).
- Track the current status of the request (pending, approved, rejected).
- Record the decision made by the Warden/Admin and notify the student of status changes.

*Collaborators:*
- Student

---

### Attendance

*Responsibilities:*
- Record a student's daily attendance.
- Store attendance date and status (Present/Absent).
- Update attendance records when required.
- Provide attendance information for a student.

*Collaborators:*
- Student

---

### Friend

*Responsibilities:*
- Store visitor details such as name, contact number, and identification details.
- Record the student whom the friend wants to visit.
- Store visit date and time.
- Record the purpose of the visit.
- Maintain visitor entry and exit information.
- Track the current status of the visit.

*Collaborators:*
- Student

---

## 3. Class Relationships

-   A Student can make one or more Bookings.
-   A Booking is associated with exactly one Room.
-   A confirmed Booking results in a Fee being generated for the
    Student.
-   A Fee can have one or more Payments made against it.
-   A Student can submit one or more Leave requests.
-   A Student can have multiple Attendance records.
-   Each Attendance record belongs to one Student.
-   A Student can register one or more Friends as visitors.
-   A Friend can visit a Student at the hostel.
-   A Friend visit records the visit date, time, purpose, and entry/exit
    details.
-   Room availability updates whenever a Booking is created, cancelled,
    or completed.
