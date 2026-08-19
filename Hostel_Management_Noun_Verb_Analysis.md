# Hostel Management System — Noun–Verb Analysis

## 1. Introduction

Noun–verb analysis is used to identify possible classes and responsibilities from the use-case specifications. For this analysis, the three Hostel Management System specifications are:

1. **Book Room**
2. **Pay Fees**
3. **Apply for Leave**

Nouns from the specifications are first collected as candidate classes. The candidates are then checked using the four filters:

1. Outside the system
2. A property of something else
3. A synonym already listed
4. Vague or whole-system

Only the nouns that remain after applying these filters are considered surviving classes.

---

## 2. Raw Candidate Noun List

### From Book Room

- Student
- Room
- Booking
- Warden
- Administrator
- Request
- Room Availability
- Room Number
- Block
- Floor
- Sharing Type
- Fee
- Notification
- Move-in Details
- Hostel Management
- College

### From Pay Fees

- Student
- Fee
- Payment
- Payment Gateway
- Transaction
- Transaction ID
- Receipt
- Payment Method
- Amount
- Fee Record
- Payment Status
- Notification
- Email
- SMS
- Accounts Department
- System

### From Apply for Leave

- Student
- Leave
- Leave Request
- Leave Record
- Warden
- Administrator
- Notification
- Notification Module
- From Date
- To Date
- Reason
- Status
- Hostel
- Parents/Guardians

---

## 3. Noun Candidate Analysis

After removing repeated occurrences of the same concept, the candidates were checked against the four filters.

| Candidate Noun | Decision | Filter / Reason |
|---|---|---|
| **Student** | Keep | Important domain entity |
| **Room** | Keep | Important domain entity |
| **Booking** | Keep | Represents the room booking concept |
| Warden | Discard | Filter 1 — Outside the system; acts as an actor |
| Administrator | Discard | Filter 1 — Outside the system; acts as an actor |
| Request | Discard | Filter 3 — General concept already represented by Booking or Leave |
| Room Availability | Discard | Filter 2 — Property/state of Room |
| Room Number | Discard | Filter 2 — Property of Room |
| Block | Discard | Filter 2 — Property of Room |
| Floor | Discard | Filter 2 — Property of Room |
| Sharing Type | Discard | Filter 2 — Property of Room |
| **Fee** | Keep | Represents a hostel fee that the student has to pay |
| Notification | Discard | Supporting functionality rather than a core domain class |
| Move-in Details | Discard | Filter 2 — Information related to Booking |
| **Payment** | Keep | Represents the payment transaction |
| Payment Gateway | Discard | Filter 1 — Outside the system |
| Transaction | Discard | Covered by the Payment concept |
| Transaction ID | Discard | Filter 2 — Property of Payment |
| Receipt | Discard | Not required as a separate domain class |
| Payment Method | Discard | Filter 2 — Property of Payment |
| Amount | Discard | Filter 2 — Property of Fee or Payment |
| Fee Record | Discard | Filter 3 — Same concept already represented by Fee |
| Payment Status | Discard | Filter 2 — Property of Payment |
| Email | Discard | External communication detail; not a domain class |
| SMS | Discard | External communication detail; not a domain class |
| Accounts Department | Discard | Filter 1 — Outside the system |
| **Leave** | Keep | Represents a student's leave information/request |
| Leave Request | Discard | Filter 3 — Synonym of Leave |
| Leave Record | Discard | Filter 3 — Same concept as Leave |
| From Date | Discard | Filter 2 — Property of Leave |
| To Date | Discard | Filter 2 — Property of Leave |
| Reason | Discard | Filter 2 — Property of Leave |
| Status | Discard | Filter 2 — Property/state of another class |
| Hostel | Discard | Filter 4 — Too broad for these use cases |
| Parents/Guardians | Discard | Filter 1 — Outside the system |
| System | Discard | Filter 4 — Whole system |

---

## 4. Application of the Four Filters

### Filter 1 — Outside the System

The following candidates are outside the Hostel Management System and are therefore not treated as domain classes:

- **Warden**
- **Administrator**
- **Payment Gateway**
- **Accounts Department**
- **Parents/Guardians**

These entities interact with or are related to the system but are not classes inside the domain model.

---

### Filter 2 — A Property of Something Else

The following candidates describe information or properties belonging to another class:

- **Room Availability** → property/state of `Room`
- **Room Number** → attribute of `Room`
- **Block** → attribute of `Room`
- **Floor** → attribute of `Room`
- **Sharing Type** → attribute of `Room`
- **Move-in Details** → information related to `Booking`
- **Transaction ID** → attribute of `Payment`
- **Payment Method** → attribute of `Payment`
- **Amount** → attribute of `Fee` or `Payment`
- **Payment Status** → attribute of `Payment`
- **From Date** → attribute of `Leave`
- **To Date** → attribute of `Leave`
- **Reason** → attribute of `Leave`
- **Status** → property/state of the relevant class

These do not need to be separate classes.

---

### Filter 3 — A Synonym Already Listed

Some nouns describe concepts that are already represented by another candidate:

- **Request** → already represented through more specific concepts such as `Booking` and `Leave`
- **Leave Request** → `Leave`
- **Leave Record** → `Leave`
- **Fee Record** → `Fee`
- **Transaction** → represented by `Payment`

For the room-related terms, **Booking** is selected as the main concept instead of creating separate classes such as Room Booking or Reservation.

---

### Filter 4 — Vague or Whole-System

The following candidates are too broad to be useful as domain classes:

- **System** → represents the whole Hostel Management System
- **Hostel** → too broad for the three specifications
- **Hostel Management** → represents the overall system/function rather than a specific domain entity

---

## 5. Surviving Classes

After applying the four filters, the following six nouns remain as the main domain classes:

| No. | Surviving Class | Why It Survives |
|---:|---|---|
| 1 | **Student** | Represents the student using the hostel system |
| 2 | **Room** | Represents hostel rooms and their availability |
| 3 | **Booking** | Represents a student's room booking |
| 4 | **Fee** | Represents the hostel fee associated with a student |
| 5 | **Payment** | Represents a payment made towards hostel fees |
| 6 | **Leave** | Represents a student's leave request and its status |

### Final Result

```text
Student
Room
Booking
Fee
Payment
Leave
```

These six classes will be used as the basis for the next stages of the project, such as CRC cards, the domain class diagram, and the object diagram.
