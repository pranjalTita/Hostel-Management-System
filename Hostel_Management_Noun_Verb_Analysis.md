# Hostel Management System — Noun–Verb Analysis

## 1. Introduction

Noun–verb analysis identifies possible classes from the use-case specifications (Register and Login, Book Room, Pay Fees, Apply for Leave) and the Warden functions. Candidates are checked with four filters:

1. Outside the system
2. A property of something else
3. A synonym already listed
4. Vague or whole-system

## 2. Raw Candidate Noun List

- **Register and Login:** Student, Warden, ID, Name, Course, Password, Hostel Member
- **Book Room:** Room, Booking, Room Number, Sharing, Capacity, AC, Beds Available, Room Fee
- **Pay Fees:** Fee, Payment, Amount, Mode, Transaction ID, Receipt, Late Penalty, Outstanding Amount
- **Apply for Leave:** Leave, From Date, To Date, Reason, Status
- **Warden functions:** Visitor, Visitor Name, Attendance, Notice, Notice Board, Notice Count, Thread

## 3. Noun Candidate Analysis

| Candidate Noun | Decision | Filter / Reason |
|---|---|---|
| **Hostel Member** | Keep | Abstract parent of Student and Warden |
| **Student** | Keep | Main domain entity |
| **Warden** | Keep | Class that extends Hostel Member |
| **Room** | Keep | Holds students and calculates the room fee |
| **Booking** | Keep | Links a Student with a Room |
| **Fee** | Keep | Room fee, late penalty and outstanding amount |
| **Payment** | Keep | A payment made by a student |
| **Leave** | Keep | Leave request with status |
| **Visitor** | Keep | Visitor request with status |
| **Notice Board** | Keep | Posts and counts notices (synchronized) |
| **Notification Thread** | Keep | Thread that posts a notice |
| ID, Name, Course | Discard | Filter 2 — properties of Hostel Member / Student |
| Password | Discard | Filter 2 — property of Hostel Member |
| Room Number, Sharing, Capacity, AC, Beds Available, Room Fee | Discard | Filter 2 — properties of Room |
| Amount, Mode, Transaction ID | Discard | Filter 2 — properties of Payment |
| Late Penalty, Outstanding Amount | Discard | Filter 2 — properties of Fee |
| Receipt | Discard | Filter 3 — a display of Payment |
| From Date, To Date, Reason, Status | Discard | Filter 2 — properties of Leave / Visitor |
| Visitor Name | Discard | Filter 2 — property of Visitor |
| Attendance | Discard | Filter 2 — property of Student |
| Notice, Notice Count | Discard | Filter 2 — handled by Notice Board |
| Thread | Discard | Filter 3 — represented by Notification Thread |
| Hostel, System | Discard | Filter 4 — whole system |

## 4. Application of the Four Filters

- **Filter 1 — Outside the System:** Nothing is discarded. Warden is a class in the code, and Visitor is stored as a record. Payment gateway, email and SMS are not used.
- **Filter 2 — Property of Something Else:** Personal details, password, room details, fee details, payment details, leave details and attendance belong to the classes listed above.
- **Filter 3 — Synonym:** Receipt is a display of Payment, and Thread is represented by Notification Thread.
- **Filter 4 — Vague:** Hostel and System are too broad.

## 5. Surviving Classes

| No. | Surviving Class | Why It Survives |
|---:|---|---|
| 1 | **HostelMember** | Abstract parent with private name, id, password |
| 2 | **Student** | Student using the hostel |
| 3 | **Warden** | Warden managing the hostel |
| 4 | **Room** | 2/3/4-sharing, AC or Non-AC room |
| 5 | **Booking** | Student–Room allotment |
| 6 | **Fee** | Room-based fee with late penalty |
| 7 | **Payment** | Payment toward the fee |
| 8 | **Leave** | Leave request and status |
| 9 | **Visitor** | Visitor request and status |
| 10 | **HostelNoticeBoard** | Posts and counts notices |
| 11 | **NotificationThread** | Posts a notice in a separate thread |

```text
HostelMember
Student
Warden
Room
Booking
Fee
Payment
Leave
Visitor
HostelNoticeBoard
NotificationThread
```
