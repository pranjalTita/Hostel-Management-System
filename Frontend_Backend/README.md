# Simple Hostel Management Web App

## Run on Windows
1. Install/use JDK 17 or newer (a JDK includes javac).
2. Extract this ZIP completely.
3. Open the HostelWeb folder and double-click start.bat.
4. Keep the terminal open and visit http://localhost:8080 in a browser.
5. Press Ctrl+C in the terminal to stop.

Or open this folder in VS Code, open its terminal, and run:

```sh
javac HostelManagementSystem.java HostelServer.java
java HostelServer
```

On Linux/macOS, run `sh start.sh` from the extracted folder.
If port 8080 is occupied, stop the previous copy of the server first.

## Login and first demo
- Warden ID: 1001 or 1002
- Initial password for either warden: WARDEN@123
- Register a student on the home page. The success message gives the assigned ID.
- Log in as that student, book a room, record a payment, and submit leave/visitor requests.
- Log out and log in as a warden. Expand the student's name to view or update records.
- Log back in as the student to see the changes.

## What each file does
| File | Purpose |
| --- | --- |
| HostelManagementSystem.java | Your original classes and console application, with a few read-only getters/summary methods added for the web pages. |
| HostelServer.java | Starts the Java HTTP server, handles forms, checks roles, and generates HTML pages. |
| style.css | Frontend layout, colours, tables, forms, and mobile sizing. |
| start.bat / start.sh | Compile and start the application. |

The frontend is server-rendered HTML plus CSS. There is no separate index.html: the `page`, `studentPage`, and `wardenPage` methods build the HTML. A form sends a POST request to Java, Java calls your existing domain methods, then the browser loads the updated page. No Spring, Maven, JavaScript framework, or database is needed.

## Included features
- Student registration with automatically assigned unique IDs; maximum 50 students.
- Student and warden login; two wardens.
- The uploaded code's 17 rooms and exactly 50 beds, with 2/3/4 sharing and AC/non-AC options.
- Room booking with capacity checks; one room per student.
- Six-month fees based on room type; payment records and history; overpayment validation.
- Leave submission and approval/rejection; visitor registration and approval.
- Attendance marking, student detail updates, and one-time Rs. 500 late penalty.
- Password changes and warden-authorized student password resets.
- Notice posting using your NotificationThread and synchronized HostelNoticeBoard.

## Scope and important behaviour
- This is a local classroom demonstration. It binds only to this computer.
- All data, passwords, notices, and login sessions are in memory and reset on restart. The original password handling is retained; this is not a production authentication system.
- Recording a payment does not charge a bank account or transfer money.
- Leave and visitor entries retain the latest request, matching the uploaded model.
- The console application can still run using `java HostelManagementSystem`; it has its own independent in-memory records.
- Web registration/login replaces the console interaction. The web login does not reproduce the console's three-attempt menu loop.
- The existing OOP demonstration menu remains in the console application.
- Avoid compiling this folder alongside another copy of the same original classes; Java will report duplicate class definitions.

## Validation
Compiled successfully with Java 21. HTTP workflow checks passed for registration and its 50-student limit, role restrictions, room capacity, payment limits, date ordering, leave/visitor approvals, attendance, profile updates, single late penalty, escaped notice content, password reset/session invalidation, and logout. Browser appearance has not been visually tested.
