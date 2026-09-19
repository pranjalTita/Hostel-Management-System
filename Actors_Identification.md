# Hostel Management System — Identification of Actors

An actor is someone or something outside the Hostel Management System that interacts with it or exchanges information with it. In our system, actors include students, wardens, visitors, external services, and automatic processes that interact with the system.

## 1. Primary Human Actors

These are the people who directly use the Hostel Management System to perform important operations.

| Actor | Role |
|---|---|
| **Student** | Logs in to the system, checks room availability, books or views a room, pays hostel fees, submits maintenance complaints, applies for leave, and views attendance and fee details. |
| **Warden / Administrator** | Manages student records, allocates rooms, approves or rejects leave requests, manages complaints, records student attendance, manages visitors, and generates hostel-related reports. |

## 2. Secondary Human Actors

These are people who interact with the hostel system for specific purposes but do not perform the main administrative operations.

| Actor | Role |
|---|---|
| **Visitor / Friend** | Visits a student staying in the hostel. Provides visitor details, records entry and exit information, and follows the hostel's visitor approval process. |

## 3. Supporting System Actors

These are supporting services or modules that assist the main actors while performing different operations.

| Actor | Role |
|---|---|
| **Authentication Service / Validator** | Checks the login credentials of students and wardens and confirms whether the entered details are valid. It is included whenever the Login use case is performed (`<<include>>`). |
| **Notification & Record Modules** | Sends notifications such as leave updates, fee receipts, visitor approvals, and complaint status updates. It also helps the system search and update hostel records. |

## 4. External Systems

These are third-party services that interact with the Hostel Management System but are not controlled by it.

| Actor | Role |
|---|---|
| **Payment Gateway** | Processes online hostel fee payments through methods such as UPI, cards, or banking. The hostel system records the payment and generates a receipt after receiving a successful response. |
| **Email / SMS Gateway** | Sends messages to students and other users through their registered email address or phone number, such as login alerts, leave decisions, visitor approvals, and payment confirmations. |

These external systems are important because some HMS features depend on services outside the main application. For example, the payment process depends on the payment gateway, while notifications may depend on the email or SMS service.

## 5. Scheduled / Time-Triggered Actors

Some operations do not require a student or warden to manually start them. These operations can run automatically at a particular time or after a fixed interval.

| Actor | Trigger | Role |
|---|---|---|
| **Fee Due Reminder Scheduler** | Runs daily or weekly | Checks fee records and sends reminders to students whose fees are due soon or are already overdue. |
| **Automated Report Generator** | Runs at fixed intervals, such as the end of a month | Creates reports about room occupancy, fee collection, attendance, and complaints for the hostel warden. |
| **Room Vacancy Refresh Job** | Runs periodically | Updates room availability after events such as check-outs, cancellations, or new room allocations. |

## Actor Summary

| Category | Actors |
|---|---|
| **Primary Human Actors** | Student, Warden / Administrator |
| **Secondary Human Actors** | Visitor / Friend |
| **Supporting System Actors** | Authentication Service / Validator, Notification & Record Modules |
| **External Systems** | Payment Gateway, Email / SMS Gateway |
| **Scheduled / Time-Triggered Actors** | Fee Due Reminder Scheduler, Automated Report Generator, Room Vacancy Refresh Job |

In total, the Hostel Management System has **10 identified actors**:

- 2 Primary Human Actors
- 1 Secondary Human Actor
- 2 Supporting System Actors
- 2 External Systems
- 3 Scheduled / Time-Triggered Actors

The **Attendance** functionality is handled by the Warden and is represented as a class in the system rather than as a separate actor.
