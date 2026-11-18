# Hostel Management System

Each class is in its own Java file. Keep all 12 `.java` files in the same folder, with no package declarations. Do not also put the older combined `HostelManagementSystem.java` in this folder, since it defines the same classes again.

## Run

Open a terminal in this folder, then run:

```bash
javac *.java
java HostelManagementSystem
```

In PowerShell, `javac *.java` works with a local JDK installed. If it is not found, install a JDK and configure PATH.

## GitHub

Copy this folder into your cloned repository, open a terminal at the repository root, and run:

```bash
git status
git add HostelProject/
git commit -m "Split hostel system into class files"
git push
```

If your repository uses a different directory name, change the `git add` path. The commit records the Git identity of whoever runs it; splitting classes does not create commits for other members.

## Demo login

Warden IDs: 1001 and 1002. Initial password: `WARDEN@123`.
Students register under Student > New registration; the system generates an ID and asks them to create a password. Data and changed passwords are in memory and reset on restart.
