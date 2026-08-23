# 💳 PayPilot

## Payment Intelligence & Failure Recovery System

PayPilot is a Java-based payment intelligence and failure recovery system built using Spring Boot and MySQL.

The application allows users to create and manage payment transactions, monitor payment performance, analyze payment failures, and receive recovery recommendations based on failure reasons.

---

## 🚀 Features

- Create payment transactions
- View all payment transactions
- Delete payment transactions
- Track successful and failed payments
- Calculate payment failure rate
- Analyze payment statistics
- Generate failure recovery recommendations
- Store payment data using MySQL
- RESTful APIs using Spring Boot
- Web-based payment dashboard

---

## 🛠️ Technologies Used

### Backend
- Java
- Spring Boot
- Spring Data JPA
- Hibernate

### Database
- MySQL

### Frontend
- HTML
- CSS
- JavaScript

### Tools
- IntelliJ IDEA
- Maven
- Git
- GitHub

---

## 📊 Dashboard

The PayPilot dashboard provides:

- Total Payments
- Successful Payments
- Failed Payments
- Failure Rate
- Payment History

---

## 🔄 Payment Failure Recovery

PayPilot provides recommendations based on payment failure reasons.

Example:

| Failure Reason | Recommendation |
|---|---|
| BANK_TIMEOUT | Retry payment after a short delay |
| UPI_TIMEOUT | Retry UPI payment or use another method |
| INSUFFICIENT_FUNDS | Ask customer to use another payment method |
| INVALID_CARD | Verify card information |
| NETWORK_ERROR | Check connectivity and retry |

---

## 🔌 API Endpoints

### Create Payment

```http
POST /api/payments
