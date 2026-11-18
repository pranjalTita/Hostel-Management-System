# Hostel Management System — Identification of Actors

An actor is someone or something outside the Hostel Management System that interacts with it. The system is a console-based Java application, so the actors are the people who log in and use its menus.

## 1. Primary Human Actors

| Actor | Role |
|---|---|
| **Student** | Registers (the system assigns the ID and the student creates a password), logs in with ID and password, views details, views rooms and books a room, views and pays the fee, applies for leave, registers a visitor, views leave and visitor status, views attendance, and changes the password. |
| **Warden** | Logs in with a warden ID (1001-1002) and password (3 attempts). Registers students, views all students, views fee and payment history, applies the late penalty, views room availability, approves or rejects leave, approves visitors, marks attendance, posts hostel notices, updates student details, changes own password, and resets a student's password. |

## 2. People Outside the System

| Person | Why not an actor |
|---|---|
| **Visitor** | A visitor never uses the system. A Student registers the visitor's name and the Warden approves it, so Visitor is a domain class. |

## 3. Actors Not Used in This Version

- Payment Gateway (payments are entered manually with mode and transaction ID)
- Email / SMS Gateway and notification modules
- Fee Due Reminder Scheduler, Automated Report Generator, Room Vacancy Refresh Job

Password checking is done inside the program (`HostelMember.checkPassword`), so no separate authentication service is needed.

## Actor Summary

| Category | Actors |
|---|---|
| **Primary Human Actors** | Student, Warden |

In total, the system has **2 actors**: Student and Warden.

**Attendance** is marked by the Warden and stored as a field of Student. It is not a separate actor or class.
