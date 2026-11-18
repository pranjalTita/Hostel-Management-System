#!/bin/sh
cd "$(dirname "$0")" || exit 1
javac HostelManagementSystem.java HostelServer.java || exit 1
java HostelServer
