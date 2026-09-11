# PayPilot 💳

### Payment Intelligence & Failure Recovery System

PayPilot is a Java-based payment intelligence and failure recovery platform built using **Spring Boot, Spring Data JPA, Hibernate, MySQL, HTML, CSS, and JavaScript**.

The system allows users to create and manage payment transactions, monitor payment performance, analyze failed payments, and generate recovery recommendations based on failure reasons.

---

## 🚀 Features

- Create payment transactions
- View all payment transactions
- Delete payment transactions
- Store payment data in MySQL
- Track successful and failed payments
- Calculate payment failure rate
- Analyze payment failure reasons
- Generate recovery recommendations
- Interactive payment dashboard
- Payment status badges
- RESTful APIs
- Persistent database storage
- Secure database password configuration using environment variables

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
- Git
- GitHub

---

## 🏗️ Project Architecture

```text
                         PayPilot
                            │
                 ┌──────────┴──────────┐
                 │                     │
             Frontend               Backend
          HTML/CSS/JS            Spring Boot
                                      │
                              ┌───────┴───────┐
                              │               │
                         Controller       Service
                                              │
                                         Repository
                                              │
                                              ▼
                                            MySQL
```

---

## 📂 Project Structure

```text
PayPilot/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── paypilot/
│   │   │           ├── PayPilotApplication.java
│   │   │           ├── controller/
│   │   │           ├── model/
│   │   │           ├── repository/
│   │   │           └── service/
│   │   │
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── index.html
│   │       │   ├── style.css
│   │       │   └── script.js
│   │       │
│   │       └── application.properties
│   │
├── pom.xml
├── README.md
└── .gitignore
```

---

## 💳 Payment Information

Each payment transaction contains information such as:

- Payment ID
- Payment amount
- Payment method
- Payment status
- Failure reason

Example:

```text
ID: 1
Amount: ₹4999
Method: UPI
Status: FAILED
Failure Reason: BANK_TIMEOUT
```

Successful payments do not require a failure reason.

---

## 📊 Dashboard

The PayPilot dashboard provides an overview of payment performance.

It displays:

- Total Payments
- Successful Payments
- Failed Payments
- Failure Rate
- Payment History
- Failure Reasons
- Recovery Recommendations

Example dashboard:

```text
┌──────────────────┬──────────────────┬──────────────────┬──────────────────┐
│ Total Payments   │ Successful       │ Failed           │ Failure Rate     │
│                  │                  │                  │                  │
│       2          │       1          │       1          │      50%          │
└──────────────────┴──────────────────┴──────────────────┴──────────────────┘
```

---

## 🔄 Failure Recovery

PayPilot analyzes failed payment transactions and provides recovery recommendations.

For example:

```text
Failure Reason:
BANK_TIMEOUT

Recommendation:
Retry the payment after a short delay.
```

This helps users understand why a payment failed and what action can be taken.

---

## 🌐 REST API

The application exposes REST APIs for managing payment transactions.

### Create Payment

```http
POST /api/payments
```

### Get All Payments

```http
GET /api/payments
```

### Delete Payment

```http
DELETE /api/payments/{id}
```

> API paths should be verified against the current `PaymentController.java` implementation before changing them.

---

## 🗄️ Database Configuration

PayPilot uses **MySQL** for persistent payment storage.

Create the database:

```sql
CREATE DATABASE paypilot;
```

The application connects to MySQL through:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/paypilot
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}
```

The database password is loaded using an environment variable instead of being stored directly in the source code.

### Set the Environment Variable

Windows PowerShell:

```powershell
$env:DB_PASSWORD="your_mysql_password"
```

Then start the Spring Boot application.

---

## ▶️ How to Run the Project

### 1. Clone the Repository

```bash
git clone https://github.com/Tiger-SUCKS/Pay-Pilot.git
```

### 2. Open the Project

Open the project in **IntelliJ IDEA**.

### 3. Configure MySQL

Make sure MySQL is running and create the database:

```sql
CREATE DATABASE paypilot;
```

### 4. Configure Database Password

Set the `DB_PASSWORD` environment variable.

Windows PowerShell:

```powershell
$env:DB_PASSWORD="your_mysql_password"
```

### 5. Run Spring Boot

Run:

```text
PayPilotApplication.java
```

Or use Maven:

```bash
mvn spring-boot:run
```

### 6. Open the Dashboard

Open your browser and visit:

```text
http://localhost:8080
```

---

## 🧪 Testing

The application can be tested by performing the following operations:

### Successful Payment

Create a payment with a successful status and verify:

- Payment appears in history
- Successful count increases
- Total payment count increases

### Failed Payment

Create a failed payment and verify:

- Failed count increases
- Failure reason is displayed
- Recovery recommendation is generated
- Failure rate is recalculated

### Delete Payment

Delete an existing transaction and verify:

- Transaction is removed
- Total payment count is updated
- Dashboard statistics are refreshed

---

## 🔐 Security

Sensitive database credentials should not be committed to GitHub.

The application uses:

```properties
spring.datasource.password=${DB_PASSWORD}
```

instead of storing the actual MySQL password in `application.properties`.

The `.gitignore` file should also prevent sensitive configuration files or environment files from being committed.

---

## 📈 Future Improvements

Possible future improvements include:

- Payment retry mechanism
- Advanced failure analytics
- Transaction search and filtering
- Date-based payment reports
- Payment charts and graphs
- Authentication and authorization
- Email/SMS notifications
- Export payment reports
- Automated payment recovery
- Docker deployment
- Cloud deployment

---

## 👨‍💻 Author

**Akshat Vishal Sinha**

### PayPilot

Payment Intelligence & Failure Recovery System

---

## 📄 License

This project is developed for educational and portfolio purposes.