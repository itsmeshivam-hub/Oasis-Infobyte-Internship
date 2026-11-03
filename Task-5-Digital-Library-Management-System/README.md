# Digital Library Management System

A web-based library application built with **Java 17, Spring Boot, Spring Security, Spring Data JPA, MySQL, Thymeleaf and CSS**.

## Features

### Admin
- Admin login and dashboard
- Add, edit and delete books
- View registered members
- View all issued books and due dates
- View fines and mark fines paid
- View and resolve contact queries

### User
- Register and log in
- Browse catalogue by category
- Search by title or author
- Issue available books (14-day loan period)
- Return books
- Automatic overdue fine: **₹5 per day**
- Reserve an unavailable book
- Send a query to the admin

## Requirements
- JDK 17+
- Maven 3.8+
- MySQL 8+
- IntelliJ IDEA (recommended)

## Database setup
Create the database (or allow the app to create it):
```sql
CREATE DATABASE digital_library;
```
Configure credentials in `src/main/resources/application.properties`, or set environment variables:
- `DB_USERNAME`
- `DB_PASSWORD`

Default local settings use MySQL user `root` and an empty password. Change these to match your local MySQL setup.

## Run
From the project directory:
```bash
mvn spring-boot:run
```
Or open the project in IntelliJ IDEA and run `DigitalLibraryApplication.java`.

Open http://localhost:8080

## Initial admin account
- Username: `admin`
- Password: `Admin@12345`

**Change the default admin password before using this beyond a local demo.**

## Notes / scope
- New member registrations receive the USER role. The ADMIN role is never selectable on the public registration form.
- Loan duration is 14 days. Fines are created on return at ₹5 for each overdue day.
- Reservation requests are queued oldest-first. When a returned copy becomes available, the first waiting reservation is marked fulfilled; the user can then issue the available copy.
- Fine payment is recorded by an administrator; no online payment gateway is connected.
- This is an educational internship project. Add production-grade validation, audit logging, concurrency controls and deployment secrets management before real-world use.
