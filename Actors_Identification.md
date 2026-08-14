# Hostel Management System — Identification of Actors

An actor is someone or something outside the Hostel Management System that interacts with it or exchanges information with it. In our system, actors are not limited to students and wardens. Some external services and automatic processes also interact with the system. Therefore, we have identified the following actors for our Hostel Management System.

## 1. Primary Human Actors

These are the people who directly use the system and perform different operations.

| Actor | Role |
|---|---|
| **Student** | Logs in to the system, checks room availability, books or views a room, pays hostel fees, submits maintenance complaints, and applies for leave. |
| **Warden / Administrator** | Handles student records, checks and approves leave requests, allocates rooms, manages complaints, and generates hostel-related reports. |

## 2. Secondary / System Actors

These are supporting modules or services that are used while a main use case is being performed.

| Actor | Role |
|---|---|
| **Authentication Service / Validator** | Checks the login credentials of students and wardens and confirms whether the entered details are valid. It is included whenever the Login use case is performed (`<<include>>`). |
| **Notification & Record Modules** | Sends notifications such as leave updates, fee receipts, and complaint status updates. It also helps the system search and update room and other records when required. |

## 3. External Systems

These are third-party services that work with the Hostel Management System but are not controlled by the system itself.

| Actor | Role |
|---|---|
| **Payment Gateway** | Processes online hostel fee payments through methods such as UPI, cards, or banking. The hostel system records the payment and generates a receipt after receiving a successful response. |
| **Email / SMS Gateway** | Sends messages to students through their registered email address or phone number, such as login alerts, leave decisions, and payment confirmations. |

These external systems are important because some HMS features depend on services outside the main application. For example, the payment process depends on the payment gateway, while notifications may depend on the email or SMS service.

## 4. Scheduled / Time-Triggered Actors

Some operations do not require a student or warden to manually start them. These operations can run automatically at a particular time or after a fixed interval.

| Actor | Trigger | Role |
|---|---|---|
| **Fee Due Reminder Scheduler** | Runs daily or weekly | Checks fee records and sends reminders to students whose fees are due soon or are already overdue. |
| **Automated Report Generator** | Runs at fixed intervals, such as the end of a month | Creates reports about room occupancy, fee collection, and complaints for the hostel warden. |
| **Room Vacancy Refresh Job** | Runs periodically | Updates room availability after events such as check-outs, cancellations, or new room allocations. This helps keep the room availability information up to date. |

## Actor Summary

| Category | Actors |
|---|---|
| **Primary Human Actors** | Student, Warden / Administrator |
| **Secondary / System Actors** | Authentication Service / Validator, Notification & Record Modules |
| **External Systems** | Payment Gateway, Email / SMS Gateway |
| **Scheduled / Time-Triggered Actors** | Fee Due Reminder Scheduler, Automated Report Generator, Room Vacancy Refresh Job |

In total, the Hostel Management System has **9 identified actors**: 2 primary human actors, 2 secondary/system actors, 2 external systems, and 3 scheduled or time-triggered processes.
