@echo off
cd /d "%~dp0"
javac HostelManagementSystem.java HostelServer.java
if errorlevel 1 (
 echo Compilation failed. Install a JDK 17 or newer and make sure javac is on PATH.
 pause
 exit /b 1
)
echo Open http://localhost:8080 in your browser.
java HostelServer
pause
