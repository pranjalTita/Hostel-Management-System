# Hostel Management System — Full Use Case Specifications

This document describes four main use cases of the console-based Hostel Management System: **Register and Login**, **Book Room**, **Pay Fees**, and **Apply for Leave**.

---

## Use Case 1: Register and Login

| Field | Details |
|---|---|
| **Use Case ID** | UC-01 |
| **Use Case Name** | Register and Login |
| **Primary Actor** | Student (Warden for login) |

### Stakeholders and Interests
- **Student** — wants an account that only he or she can open.
- **Warden** — wants only valid students and wardens to use the system, and can reset a forgotten password.

### Preconditions
1. For registration, fewer than 50 students are registered and an unused ID remains (IDs 1 to 100).

### Postconditions (Success Guarantee)
1. A new Student is stored with a system-assigned ID, a course and a password of at least 6 characters.
2. A logged-in user sees the Student menu or the Warden menu.

### Trigger
The user selects **Student** or **Warden** on the first menu.

### Main Flow (New Student)
1. Student selects "New registration".
2. Student enters name and course.
3. System assigns the next student ID.
4. Student enters a new password and confirms it.
5. System saves the student, shows the assigned ID and opens the Student menu.

### Main Flow (Login)
1. User enters ID and password (Student ID 1-100, Warden ID 1001-1002).
2. System checks the password.
3. System opens the Student or Warden menu.

### Alternate Flows
**A1 — Hostel Full** — If 50 students are registered, the system shows "Hostel capacity is full."
**A2 — Weak or Unmatched Password** — The system asks again until the password has at least 6 characters and matches the confirmation.
**A3 — Wrong Credentials** — After 3 wrong attempts the access is denied. A student asks the Warden to reset the password.
**A4 — Blank Input** — The system keeps asking until a value is entered.

---

## Use Case 2: Book Room

| Field | Details |
|---|---|
| **Use Case ID** | UC-02 |
| **Use Case Name** | Book Room |
| **Primary Actor** | Student |

### Stakeholders and Interests
- **Student** — wants to see the rooms, their type and fee, and get a bed.
- **Warden** — wants each room to hold no more than its capacity.

### Preconditions
1. The student is logged in.
2. The student does not already have a room.

### Postconditions (Success Guarantee)
1. The student is added to the room and a Booking is created.
2. The student's fee is fixed according to the room (sharing type and AC / Non-AC).
3. The room's available beds are reduced by one.

### Trigger
The student chooses **"View rooms and book a room"**.

### Main Flow
1. System shows the 17 rooms with sharing type (2 / 3 / 4), AC or Non-AC, semester fee, capacity, occupied beds and beds available.
2. Student selects a room number.
3. System checks that the room has space.
4. System shows the room fee and asks "Confirm booking? (yes/no)".
5. Student confirms, and the system allots the room and displays the allotment message.

### Alternate Flows
**A1 — Student Already Has a Room** — The system shows the existing room and stops.
**A2 — Invalid Room Choice** — The system asks for a number within the room list.
**A3 — Room Is Full** — The system asks the student to choose another room.
**A4 — Student Does Not Confirm** — The system shows "Booking cancelled."

---

## Use Case 3: Pay Fees

| Field | Details |
|---|---|
| **Use Case ID** | UC-03 |
| **Use Case Name** | Pay Fees |
| **Primary Actor** | Student (Warden applies the late penalty) |

### Stakeholders and Interests
- **Student** — wants to pay the fee in full or in parts and get a receipt.
- **Warden** — wants accurate payment records and can add a late penalty.

### Preconditions
1. The student is logged in and has booked a room.

### Postconditions (Success Guarantee)
1. The paid amount is added and the outstanding amount is reduced.
2. A Payment record (amount, mode, transaction ID) is saved in the payment history.
3. A receipt and the updated fee status (Paid / Pending) are displayed.

### Trigger
The student chooses **"Pay my fee"**.

### Main Flow
1. Student enters the amount, payment mode (Cash / UPI / Bank) and transaction ID.
2. System validates the amount against the outstanding fee.
3. System updates the Fee, creates a Payment and shows the receipt and fee details.

### Alternate Flows
**A1 — No Room Booked** — The system shows "Book a room before paying fees."
**A2 — Amount Not Valid** — The amount must be a finite number above zero.
**A3 — Amount Exceeds Outstanding Fee** — The system shows "Payment exceeds the outstanding fee."
**A4 — Late Penalty** — When viewing a student's fee, the Warden may add a Rs. 500 late penalty once. A second attempt, a student with no room, or a student with nothing outstanding is refused.

---

## Use Case 4: Apply for Leave

| Field | Details |
|---|---|
| **Use Case ID** | UC-04 |
| **Use Case Name** | Apply for Leave |
| **Primary Actor** | Student (Warden decides) |

### Stakeholders and Interests
- **Student** — wants to submit leave and see the decision.
- **Warden** — wants to decide on each request.

### Preconditions
1. The student is logged in.

### Postconditions (Success Guarantee)
1. A Leave record (from date, to date, reason) is stored with status **Pending**.
2. After the Warden decides, the status is **Approved** or **Rejected**, and the student can see it under "View leave and visitor status".

### Trigger
The student chooses **"Apply for leave"**.

### Main Flow
1. Student enters from date, to date and reason.
2. System creates the Leave with status "Pending".
3. Warden chooses "Approve/reject a leave request" and selects the student.
4. Warden enters "approve" or "reject", and the system updates and displays the status.

### Alternate Flows
**A1 — Blank Field** — The system keeps asking until all three values are entered.
**A2 — No Leave Request** — The system shows "No leave request from this student."
**A3 — Invalid Decision** — The status stays unchanged.
**A4 — New Request Replaces the Old One** — A student keeps only one leave record.

---

## Summary Table

| Use Case | Primary Actor | Other Actor Involved | # Alternate Flows |
|---|---|---|---|
| UC-01: Register and Login | Student | Warden (reset password) | 4 |
| UC-02: Book Room | Student | - | 4 |
| UC-03: Pay Fees | Student | Warden (late penalty) | 4 |
| UC-04: Apply for Leave | Student | Warden (approves or rejects) | 4 |
