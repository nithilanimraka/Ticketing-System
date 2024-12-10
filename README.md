# Spring Boot Ticketing System

This project is a ticketing system with both CLI and GUI interfaces, developed using Spring Boot for the backend, React for the frontend, and Core Java for the CLI. 
The system demonstrates multithreading by simulating multiple customers and vendors simultaneously.

## Setup Instructions

### Prerequisites
- **XAMPP Server:** Ensure XAMPP is installed and running.
- **Database:** Create a database in PHPMyAdmin named `springbootticketingsystem1`.
- **Backend Requirements:** Java JDK (correct version: 19) and Maven installed.
- **Frontend Requirements:** Node.js, npm, and Bootstrap installed.

### Steps to Set Up

1. **Start XAMPP Server:**
   - Launch XAMPP and ensure both the Apache and MySQL services are running.
   - Create a database named `springbootticketingsystem1` in PHPMyAdmin.

2. **Run the Backend:**
   - Navigate to the backend directory and open it using IntelliJ IDEA and run it.

3. **Run the Frontend (for GUI operation):**
   - Navigate to the frontend directory in the terminal.
   - Install dependencies:
     ```bash
     npm install
     ```
   - Start the frontend:
     ```bash
     npm start
     ```

4. **Run the CLI (for CLI operation):**
   - Ensure both the backend server and XAMPP server are running.
   - Navigate to the CLI directory and open it using IntelliJ IDEA and run it.
     
---

## Usage Guidelines

### GUI Usage
- After starting the frontend, access the system via your web browser at: http://localhost:3000


**Important:** Make sure all dependencies, including `node_modules` and Bootstrap, are downloaded and installed for proper functioning.

### CLI Usage
- When you execute the CLI program, a **MENU** will be displayed to guide you through available options.
- The CLI is designed to showcase the multithreading capabilities of the system, allowing simulation of simultaneous operations by multiple customers and vendors.

---

## Troubleshooting

- **Issue: Backend not running properly**
- Ensure Java JDK and Maven are installed and properly configured.
- Check if the XAMPP server is running and the database `springbootticketingsystem1` exists.

- **Issue: Frontend not displaying correctly**
- Confirm that all dependencies are installed by running:
  ```bash
  npm install
  ```
- Verify that the backend server is running.

- **Issue: CLI not functioning as expected**
- Ensure the backend and XAMPP server are running.
- Verify that Java is installed and set up correctly in the system's PATH.

---

## Key Features
- **GUI Interface:** User-friendly React-based frontend.
- **CLI Interface:** Text-based interface showcasing multithreading.
- **Multithreading:** Simulates multiple customers and vendors interacting with the system.

---
