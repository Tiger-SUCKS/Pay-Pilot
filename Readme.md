# PayPilot 💳

### Payment Intelligence & Failure Recovery System

PayPilot is a Java-based payment intelligence platform built with Spring Boot and MySQL. It allows users to manage payment transactions, monitor payment performance, analyze failures, and generate recovery recommendations for failed payments.

---

## 🚀 Features

- Create payment transactions
- View all payment transactions
- Store payment data in MySQL
- Track successful and failed payments
- Calculate payment failure rate
- Analyze payment failure reasons
- Generate recovery recommendations
- Delete transactions
- Interactive web dashboard
- Payment status badges
- RESTful APIs

---

## 🛠️ Tech Stack

### Backend
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- REST APIs

### Database
- MySQL

### Frontend
- HTML
- CSS
- JavaScript

### Development Tools
- IntelliJ IDEA
- Maven
- Git / GitHub

---

## 🏗️ Project Architecture

```text
                    PayPilot
                       │
              ┌────────┴────────┐
              │                 │
          Frontend           REST API
       HTML/CSS/JS          Spring Boot
              │                 │
              │          ┌──────┴──────┐
              │          │             │
              │      Controller      Service
              │                         │
              │                    Repository
              │                         │
              └────────────────────┬────┘
                                   │
                                MySQL