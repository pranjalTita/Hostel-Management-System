# Hostel Management System — Full Use Case Specifications

This document describes three main use cases of our Hostel Management System: **Book Room**, **Pay Fees**, and **Apply for Leave**. These are some of the main activities a student would use in the system and they also involve other parts of the system such as the Warden, Authentication Service, Notification Module, Payment Gateway, and scheduled processes.

Each use case is described using the following points: Use Case ID and Name, Primary Actor, Stakeholders and Interests, Preconditions, Postconditions, Trigger, Main Flow, and Alternate Flows.

---

## Use Case 1: Book Room

| Field | Details |
|---|---|
| **Use Case ID** | UC-01 |
| **Use Case Name** | Book Room |
| **Primary Actor** | Student |

### Stakeholders and Interests

- **Student** — wants to find an available room and book a suitable one without having to visit the hostel office.
- **Warden / Administrator** — wants room allocation to be correct and wants to avoid assigning the same room to more than one student.
- **Notification & Record Modules** — needs correct, up-to-date room data so future searches stay accurate.
- **Hostel Management (College)** — wants proper records of occupied and vacant rooms.

### Preconditions

1. The student is registered in the system and has successfully logged in.
2. The student does not already have an active room allocation.
3. At least one room is available in the hostel that matches basic eligibility (e.g., correct hostel block for the student's course/gender, as applicable).

### Postconditions (Success Guarantee)

1. The selected room is temporarily reserved and becomes allocated after the Warden confirms it.
2. The student's profile is updated with the new room number and allocation date.
3. A confirmation notification is sent to the student.
4. The room's vacancy information is updated.

### Trigger

The student selects **"Book Room"** after checking the available rooms and choosing one.

### Main Flow

1. Student logs in and selects **"Check Room Availability."**
2. System runs the **Search Rooms** function and displays a list of currently vacant rooms with details (block, floor, sharing type, fee).
3. Student selects a preferred room and clicks **"Book Room."**
4. System temporarily reserves the room for the student and sends an allocation request to the Warden.
5. Warden reviews the request and approves it.
6. System permanently marks the room as **allocated** to that student and updates the room database.
7. System (via the Notification Module) sends a confirmation message to the student with the room number and move-in details.
8. Use case ends successfully.

### Alternate Flows

**A1 — No Matching Rooms Available**
- At Step 2, if there is no room matching the student's requirements, the system shows a message that no suitable room is available. The student can check another room option or leave the booking process.
- The student may either choose an alternative or exit the use case without booking.

**A2 — Warden Rejects the Allocation**
- At Step 5, the Warden may reject the request because of a hostel rule, incorrect information, or another valid reason. The temporary reservation is removed, the room becomes available again, and the student is informed about the rejection.
- The student may search again and select a different room.

**A3 — Room Gets Booked by Another Student First**
- If another student reserves the same room before the current request is completed, the system checks the latest room status. The student receives a message that the selected room is no longer available and is taken back to the updated room list.

---

## Use Case 2: Pay Fees

| Field | Details |
|---|---|
| **Use Case ID** | UC-02 |
| **Use Case Name** | Pay Fees |
| **Primary Actor** | Student |

### Stakeholders and Interests

- **Student** — wants to pay the hostel fee easily and receive confirmation of the payment.
- **Warden / Administrator** — wants the fee records to be accurate and properly maintained.
- **Payment Gateway (External System)** — needs correct transaction details to process the payment securely.
- **Hostel Accounts Department** — needs reliable payment records for checking fee collection.

### Preconditions

1. The student is logged in to the system.
2. The student has an outstanding or upcoming fee amount recorded in the system.
3. The Payment Gateway service is reachable (system has internet connectivity to the external payment service).

### Postconditions (Success Guarantee)

1. The paid amount is removed from the student's pending dues.
2. A digital fee receipt is generated and linked to the student's record.
3. The transaction is logged in the system's payment records for warden/accounts visibility.
4. The fee reminder system no longer treats the paid amount as pending.

### Trigger

The student selects **"Pay Fees"** from the dashboard to pay an outstanding hostel fee.

### Main Flow

1. Student logs in and selects **"Pay Fees."**
2. System displays the current outstanding amount and payment options.
3. Student enters/confirms the amount and proceeds to pay.
4. System redirects the request to the **Payment Gateway** (external system) with the transaction details.
5. Payment Gateway processes the payment and returns a "Success" response along with a transaction ID.
6. System (via **Get Fee Receipt**) automatically generates a digital receipt and updates the student's fee record as "Paid."
7. System sends a confirmation notification with the receipt to the student's registered email/SMS.
8. Use case ends successfully.

### Alternate Flows

**A1 — Payment Fails or Is Declined**
- At Step 5, if the Payment Gateway returns a failure response, the system does not mark the fee as paid. An error message is shown and the student can try again using another payment method.

**A2 — Payment Gateway Times Out / No Response**
- If the Payment Gateway does not respond within the expected time, the system shows a message such as **"Payment status is pending. Please check again shortly."** The system does not immediately mark the payment as failed and can check the transaction status again later.

**A3 — Student Enters an Incorrect or Partial Amount**
- If the student enters an amount that is not allowed according to the fee details, the system stops the payment before sending it to the Payment Gateway and asks the student to enter the correct amount.

---

## Use Case 3: Apply for Leave

| Field | Details |
|---|---|
| **Use Case ID** | UC-03 |
| **Use Case Name** | Apply for Leave |
| **Primary Actor** | Student |

### Stakeholders and Interests

- **Student** — wants to submit a leave request without repeatedly visiting the Warden's office and wants to know the decision quickly.
- **Warden / Administrator** — wants to keep a proper record of leave requests and make informed approval or rejection decisions.
- **Notification & Record Modules** — needs to deliver the approval/rejection decision back to the student promptly.
- **Parents/Guardians (indirect stakeholder)** — benefit from the hostel having an updated record of the student's approved leave.

### Preconditions

1. The student is logged in to the system.
2. The student does not already have an active, overlapping leave request pending or approved for the same dates.

### Postconditions (Success Guarantee)

1. A leave request is created in the student's record with a status such as **Pending**, **Approved**, or **Rejected**.
2. If approved, the student's record shows the approved leave duration, viewable by both the student and the warden.
3. The student receives a notification of the final decision.

### Trigger

The student selects **"Apply for Leave"** because they need to stay away from the hostel for a particular period.

### Main Flow

1. Student logs in and selects **"Apply for Leave."**
2. System displays a leave application form (from date, to date, reason).
3. Student fills in the details and submits the request.
4. System validates the dates (e.g., "to date" is after "from date," no past dates) and creates a leave record with status **"Pending."**
5. System forwards the leave request to the Warden for review.
6. Warden reviews the request and clicks **"Approve."**
7. System updates the leave record status to **"Approved"** and (via the Notification Module) sends a confirmation to the student.
8. Use case ends successfully.

### Alternate Flows

**A1 — Warden Rejects the Leave Request**
- At Step 6, the Warden may reject the leave request because of an exam, hostel rule, or another valid reason. The system changes the status to **"Rejected"**, saves the reason if provided, and informs the student.

**A2 — Student Submits an Invalid Date Range**
- At Step 4, if the to-date is before the from-date, a past date is entered, or the request overlaps with an existing approved leave, the system shows an appropriate error message. The request is not sent to the Warden until the details are corrected.

**A3 — Student Cancels the Leave Request Before Approval**
- After the request has been sent to the Warden but before it is approved or rejected, the student can select **"Cancel Request."** The system changes the status to **"Cancelled"** and removes it from the pending list.

---

## Summary Table

| Use Case | Primary Actor | Key External/Secondary Actor Involved | # Alternate Flows |
|---|---|---|---|
| UC-01: Book Room | Student | Notification & Record Modules, Warden | 3 |
| UC-02: Pay Fees | Student | Payment Gateway, Fee Due Reminder Scheduler | 3 |
| UC-03: Apply for Leave | Student | Notification & Record Modules, Warden | 3 |

These three use cases cover important parts of the Hostel Management System. **Book Room** deals with room allocation, **Pay Fees** shows how the system works with an external payment service, and **Apply for Leave** shows an approval process between the student and the Warden. Together, they give a clear idea of how the main users and supporting services interact with the system.
