# 🚆 Online Reservation System

## Oasis Infobyte – Java Development Internship
### Task 1: Online Reservation System

## 📌 Project Overview
The Online Reservation System is a Java-based desktop application that allows users to log in, book train reservations, and cancel existing bookings using a unique PNR number.

The application uses Java Swing for the graphical user interface and SQLite for storing reservation details.

## ✨ Features
- **Login Authentication:** Secure login with username and password.
- **Train Reservation:** Enter passenger and journey details.
- **Automatic Train Name Detection:** Displays the train name based on the train number.
- **PNR Generation:** Generates a unique PNR for every booking.
- **Booking Confirmation:** Displays reservation details after successful booking.
- **Ticket Cancellation:** Fetches booking details using PNR and allows cancellation.
- **Input Validation:** Checks required fields, train number, and journey date.
- **Database Integration:** Stores and manages reservation records using SQLite.

## 🛠️ Technologies Used
- Java 17
- Java Swing
- JDBC
- SQLite
- Maven

## 🔐 Login Credentials

| Field | Value |
|---|---|
| Username | `admin` |
| Password | `admin123` |

*Demo credentials for this project.*

## 🚉 Sample Train Details

| Train Number | Train Name |
|---|---|
| 12155 | Bhopal Express |
| 19321 | Indore Express |
| 12002 | Shatabdi Express |
| 12951 | Mumbai Rajdhani |
| 12627 | Karnataka Express |

## 📂 Project Structure

```text
Online-Reservation-System/
│
├── pom.xml
├── reservation.db
│
└── src/
    └── main/
        └── java/
            └── Main.java
```

## ⚙️ How to Run

1. Clone or download this repository.
2. Open the project in IntelliJ IDEA or VS Code.
3. Make sure Java 17 and Maven are installed.
4. Allow Maven to download the SQLite JDBC dependency.
5. Run `Main.java`.

## 🎯 Learning Outcomes
- Understanding Java Swing GUI development.
- Connecting Java applications with SQLite using JDBC.
- Performing database operations using PreparedStatement.
- Implementing login authentication.
- Generating unique PNR numbers.
- Handling user input and validation.

## 👨‍💻 Author
**Shivam Tiwari**

- GitHub: [itsmeshivam-hub](https://github.com/itsmeshivam-hub)
- LinkedIn: [Shivam Tiwari](https://www.linkedin.com/in/shivamtiwari-tech/)

---

**Developed as Task 1 of the Java Development Internship at Oasis Infobyte.**
