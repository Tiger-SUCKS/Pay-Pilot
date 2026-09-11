# PayPilot 💳

### Payment Intelligence & Failure Recovery System

PayPilot is a backend-focused payment intelligence and failure recovery system built using **Java, Spring Boot, Spring Data JPA, Hibernate, and MySQL**.

The system provides REST APIs for payment transaction management, payment analytics, failure classification, recovery strategy selection, retry policy calculation, request validation, and idempotent payment creation.

It also includes an interactive web dashboard and OpenAPI/Swagger documentation for API testing.

---

## 🚀 Key Features

- Create payment transactions
- Idempotent payment creation using `Idempotency-Key`
- View payments using pagination
- Filter payments by:
    - Status
    - Payment method
    - Customer type
- Sort payments by supported fields
- Track successful and failed payments
- Calculate payment failure rate
- Classify payment failures into categories
- Determine recovery strategies
- Determine retry eligibility
- Calculate retry delays using exponential backoff
- Generate failure recovery recommendations
- Request validation using Jakarta Bean Validation
- Global exception handling
- Custom payment-not-found handling
- Persistent MySQL storage using JPA/Hibernate
- Interactive payment dashboard
- RESTful APIs
- OpenAPI/Swagger API documentation
- Environment-based database password configuration

---

## 🛠️ Tech Stack

### Backend

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- REST APIs
- Jakarta Bean Validation

### Database

- MySQL

### Frontend

- HTML
- CSS
- JavaScript

### API Documentation

- OpenAPI 3
- Swagger UI

### Development Tools

- IntelliJ IDEA
- Maven
- Git
- GitHub

---

## 🏗️ Architecture

```text
                         PayPilot
                            │
                     REST API Layer
                            │
                     ┌──────┴──────┐
                     │ Controller  │
                     └──────┬──────┘
                            │
                     DTO + Validation
                            │
                     ┌──────┴──────┐
                     │   Service   │
                     └──────┬──────┘
                            │
              ┌─────────────┼─────────────┐
              │             │             │
       Failure Analyzer  Recovery     Retry Policy
              │           Engine          Engine
              │             │             │
              └─────────────┼─────────────┘
                            │
                     Repository Layer
                            │
                    JPA Specification
                            │
                         Hibernate
                            │
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
│   │   │           │
│   │   │           ├── PayPilotApplication.java
│   │   │           │
│   │   │           ├── analyzer/
│   │   │           │   └── FailureAnalyzer.java
│   │   │           │
│   │   │           ├── config/
│   │   │           │   └── OpenApiConfig.java
│   │   │           │
│   │   │           ├── controller/
│   │   │           │   └── PaymentController.java
│   │   │           │
│   │   │           ├── dto/
│   │   │           │   ├── PaymentAnalyticsResponse.java
│   │   │           │   ├── PaymentRequest.java
│   │   │           │   └── PaymentResponse.java
│   │   │           │
│   │   │           ├── entity/
│   │   │           │   └── Payment.java
│   │   │           │
│   │   │           ├── enums/
│   │   │           │   ├── FailureCategory.java
│   │   │           │   ├── PaymentMethod.java
│   │   │           │   ├── PaymentStatus.java
│   │   │           │   └── RecoveryStrategy.java
│   │   │           │
│   │   │           ├── exception/
│   │   │           │   ├── ErrorResponse.java
│   │   │           │   ├── GlobalExceptionHandler.java
│   │   │           │   └── PaymentNotFoundException.java
│   │   │           │
│   │   │           ├── recovery/
│   │   │           │   └── RecoveryStrategyEngine.java
│   │   │           │
│   │   │           ├── repository/
│   │   │           │   └── PaymentRepository.java
│   │   │           │
│   │   │           ├── retry/
│   │   │           │   ├── RetryPolicy.java
│   │   │           │   └── RetryPolicyEngine.java
│   │   │           │
│   │   │           ├── service/
│   │   │           │   └── PaymentService.java
│   │   │           │
│   │   │           └── specification/
│   │   │               └── PaymentSpecification.java
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

## 💳 Payment Model

Each payment transaction contains information such as:

- Payment ID
- Amount
- Payment method
- Payment status
- Failure reason
- Customer type
- Creation timestamp
- Idempotency key

Example failed payment:

```text
ID: 1
Amount: ₹4999
Method: UPI
Status: FAILED
Failure Reason: BANK_TIMEOUT
Customer Type: RETURNING
```

Successful payments do not require a failure reason.

---

## 🧠 Payment Intelligence

PayPilot analyzes payment failures and categorizes them into different failure categories.

### Failure Categories

```text
TIMEOUT
NETWORK
CUSTOMER_ERROR
PAYMENT_METHOD_ERROR
SYSTEM_ERROR
UNKNOWN
```

For example:

```text
BANK_TIMEOUT
       ↓
TIMEOUT
       ↓
RETRY
```

Another example:

```text
INVALID_CARD
       ↓
PAYMENT_METHOD_ERROR
       ↓
FALLBACK_PAYMENT_METHOD
```

---

## 🔄 Recovery Strategy Engine

The recovery engine determines an appropriate action based on the failure category.

```text
TIMEOUT
   ↓
RETRY

NETWORK
   ↓
RETRY

CUSTOMER_ERROR
   ↓
CUSTOMER_ACTION

PAYMENT_METHOD_ERROR
   ↓
FALLBACK_PAYMENT_METHOD

UNKNOWN
   ↓
NO_ACTION
```

This separates failure analysis from recovery decision-making and keeps the business logic modular.

---

## 🔁 Retry Policy Engine

Retryable failures receive a retry policy.

The system determines:

- Whether the failure is retryable
- Maximum retry attempts
- Initial retry delay
- Retry delay for individual attempts

Example:

```text
Failure Category: TIMEOUT

Retryable: true
Maximum Attempts: 3
Initial Delay: 1000 ms

Attempt 1 → 2000 ms
Attempt 2 → 4000 ms
```

Retry delays are calculated using an exponential backoff strategy.

```text
delay = initialDelay × 2^attempt
```

---

## 🔐 Idempotency

PayPilot supports idempotent payment creation using an `Idempotency-Key`.

Example:

```http
POST /api/payments
Idempotency-Key: PAYMENT-001
```

If the same idempotency key is submitted again, the existing payment is returned instead of creating another transaction.

The idempotency key is also stored with a unique database constraint.

This helps prevent duplicate payment creation when clients retry requests.

---

## 📊 Payment Analytics

The analytics API provides:

- Total payments
- Successful payments
- Failed payments
- Failure rate
- Failure category counts
- Failed payment method counts

Example:

```json
{
  "totalPayments": 4,
  "successfulPayments": 3,
  "failedPayments": 1,
  "failureRate": 25.0,
  "failureCategoryCounts": {
    "TIMEOUT": 1
  },
  "failurePaymentMethodCounts": {
    "UPI": 1
  }
}
```

---

## 🔎 Filtering, Pagination & Sorting

The payment API supports dynamic querying using Spring Data JPA Specifications.

Supported filters include:

```text
status
paymentMethod
customerType
```

Pagination example:

```http
GET /api/payments?page=0&size=10
```

Filtering example:

```http
GET /api/payments?status=FAILED
```

Sorting example:

```http
GET /api/payments?sortBy=createdAt&direction=desc
```

Multiple parameters can also be combined.

---

## 🌐 REST API

### Create Payment

```http
POST /api/payments
```

Required header:

```http
Idempotency-Key: PAYMENT-001
```

Example request:

```json
{
  "amount": 3000,
  "paymentMethod": "CARD",
  "status": "SUCCESSFUL",
  "failureReason": null,
  "customerType": "NEW"
}
```

---

### Get Payments

```http
GET /api/payments
```

Supports:

- Pagination
- Filtering
- Sorting

---

### Get Payment Analytics

```http
GET /api/payments/analytics
```

Returns payment statistics and failure analytics.

---

### Get Recovery Recommendation

```http
GET /api/payments/{id}/recommendation
```

Returns a recovery recommendation based on the payment's failure reason.

Example:

```text
Timeout failure detected.
Retry the payment after a short delay.
```

---

## 📖 API Documentation

PayPilot includes OpenAPI/Swagger documentation.

After starting the application, open:

```text
http://localhost:8080/swagger-ui/index.html
```

Swagger UI can be used to:

- Explore available endpoints
- View request parameters
- View request/response schemas
- Execute API requests
- Test validation behavior
- Test payment analytics
- Test recovery recommendations

---

## 🗄️ Database Configuration

PayPilot uses **MySQL** for persistent payment storage.

Create the database:

```sql
CREATE DATABASE paypilot;
```

The application connects to MySQL using:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/paypilot
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}
```

The database password is loaded through an environment variable rather than being stored directly in the source code.

### Windows PowerShell

```powershell
$env:DB_PASSWORD="your_mysql_password"
```

---

## ▶️ How to Run

### 1. Clone the Repository

```bash
git clone https://github.com/Tiger-SUCKS/Pay-Pilot.git
```

### 2. Open the Project

Open the project in **IntelliJ IDEA**.

### 3. Start MySQL

Make sure MySQL is running.

Create the database:

```sql
CREATE DATABASE paypilot;
```

### 4. Configure Database Password

Set:

```powershell
$env:DB_PASSWORD="your_mysql_password"
```

### 5. Run the Application

Run:

```text
PayPilotApplication.java
```

Or use Maven:

```bash
mvn spring-boot:run
```

### 6. Open the Dashboard

```text
http://localhost:8080
```

### 7. Open Swagger UI

```text
http://localhost:8080/swagger-ui/index.html
```

---

## 🧪 Testing

The application has been verified through API testing using Swagger UI.

Tested functionality includes:

### Payment Creation

- Valid payment creation
- Request validation
- Invalid payment status rejection
- Invalid payment method rejection
- Idempotency-key based payment creation

### Payment Retrieval

- Payment listing
- Pagination
- Filtering
- Sorting

### Analytics

- Total payment count
- Successful payment count
- Failed payment count
- Failure rate
- Failure category analysis
- Failed payment method analysis

### Failure Recovery

- Failure classification
- Recovery strategy selection
- Retry eligibility
- Retry delay calculation
- Recovery recommendations

### Error Handling

- Payment-not-found handling
- Request validation errors
- Invalid request parameter handling

---

## 🔐 Security & Reliability

PayPilot follows several backend engineering practices:

- Database passwords are loaded using environment variables
- Request validation prevents invalid payment data
- Global exception handling provides consistent API errors
- Idempotency prevents duplicate requests from creating duplicate transactions
- Database uniqueness constraints protect idempotency keys
- Transaction management is used during payment creation
- Pagination prevents unnecessarily large result sets
- Sort fields are restricted to supported properties

---

## 📈 Future Improvements

Potential future improvements include:

- Authentication and authorization
- JWT-based security
- Docker deployment
- Cloud deployment
- Production monitoring and metrics
- Automated notification system
- Advanced time-based analytics
- Payment report exports
- Integration with real payment gateways
- Automated retry execution using scheduled/background processing

---

## 👨‍💻 Author

**Akshat Vishal Sinha**

### PayPilot

**Payment Intelligence & Failure Recovery System**

Built as a portfolio project to demonstrate backend development, REST API design, database persistence, failure analysis, reliability patterns, and Spring Boot architecture.

---

## 📄 License

This project is developed for educational and portfolio purposes.