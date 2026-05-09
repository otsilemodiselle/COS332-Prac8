COS332 Practical Assignment 8
Overview

This project implements an FTP-based automatic backup client in Java using raw socket communication.

The program monitors a local directory for new or modified text files. Whenever a file change is detected, the client connects to an FTP server daemon and uploads the updated file to the remote server using the FTP protocol.

The implementation uses manually constructed FTP commands and socket connections without relying on any prebuilt FTP client libraries.

Features
Directory polling for file changes
Automatic detection of new .txt files
Detection of modified files using file timestamps
FTP login using raw socket communication
Passive mode FTP data connections
File uploads using the STOR FTP command
Versioned backups
Example:
notes.txt.000
notes.txt.001
notes.txt.002
Technologies Used
Java
Java Sockets
FTP Protocol
pyftpdlib FTP daemon (for testing)
Project Structure
prac8/
├── src/
│   ├── Main.java
│   ├── FileWatcher.java
│   └── FTPClient.java
├── watched/
├── out/
└── prac8.jar
FTP Server Setup

The project was tested using pyftpdlib.

Install:

pip install pyftpdlib

Run the FTP server:

python -m pyftpdlib -p 2121 -w

This starts a writable FTP server on port 2121.

Running the Program

Run the executable JAR:

java -jar prac8.jar

The program monitors the watched directory for .txt file changes.

Example Workflow
Place a .txt file inside the watched directory.
The program detects the file.
The file is uploaded to the FTP server.
If the file changes again, a new versioned backup is uploaded.

Example uploaded files:

notes.txt.000
notes.txt.001
notes.txt.002
FTP Commands Implemented

The following FTP commands are implemented manually:

USER
PASS
PASV
STOR
QUIT
Notes
The implementation uses passive FTP mode.
File monitoring is implemented using polling.
Only .txt files are monitored.
The FTP server used for testing was hosted locally on localhost:2121.
Author

Otsile