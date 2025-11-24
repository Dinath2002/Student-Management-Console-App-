# Student Management

Console-based tool to manage an in-memory list of students. Add, view, sort, search, update, remove, and summarize records directly from the terminal.

## Requirements
- Java 17 or newer (uses switch expressions and records).

## Build and Run
```bash
# from the repo root
cd StudentManagement
javac -d out/production/StudentManagement src/*.java
java -cp out/production/StudentManagement Main
```

## Usage
- Follow the menu prompts (1-10) to perform actions such as adding, searching, sorting by name/age, updating, removing, or viewing stats.
- Student IDs are case-insensitive and must be unique.
- Data is stored only in memory for the current session.
