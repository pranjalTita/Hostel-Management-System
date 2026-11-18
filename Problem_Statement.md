# Problem Statement

## Hostel Management System (HMS)

The Hostel Management System (HMS) is a console-based Java application that replaces the manual, paper-register process of a hostel with a single secure program. In the manual process, students visit the hostel office to check free rooms, pay fees, ask for leave or register a visitor, and the warden keeps every record by hand.

The HMS gives students and wardens separate password-protected menus. Students register (the system assigns the ID), choose a room by sharing type and AC / Non-AC, pay the fee that depends on the room, apply for leave, register visitors, check attendance and change their password. Wardens manage student records, fees and late penalties, leave, visitors, attendance, notices and passwords.

The system reduces manual work and errors, and it keeps room occupancy and fee status correct automatically.

## Scope

- 17 rooms with 50 beds: 5 two-sharing, 8 three-sharing and 4 four-sharing rooms, each AC or Non-AC.
- Semester fee (6 months) depends on the room: 2-sharing Rs. 50000, 3-sharing Rs. 40000, 4-sharing Rs. 35000, plus Rs. 15000 for AC.
- A one-time late penalty of Rs. 500 can be added by the Warden.
- Up to 50 students, with system-assigned IDs from 1 to 100.
- Passwords of at least 6 characters for students and wardens (Warden IDs 1001 and 1002), and 3 login attempts.
- Data is kept in memory while the program runs.

## Key Features

**For Students**
- Register with a system-assigned ID and a password, and log in
- View rooms with fees and book a bed after confirming
- View the fee and pay it in full or in parts (Cash / UPI / Bank) with a receipt
- Apply for leave, register a visitor and view their status
- View attendance and change the password

**For Wardens**
- Register students, view all students and update student details
- View fee and payment history and apply the late penalty
- View room availability
- Approve or reject leave and approve visitors
- Mark attendance and post hostel notices (using threads)
- Change own password and reset a student's password

## Objective

To digitize basic hostel operations so that registration, room booking, fee payment, leave, visitors and attendance are handled securely and correctly, with less manual work and fewer errors for both students and the warden.
