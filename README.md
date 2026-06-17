⚙️ Enterprise Employee Management API 

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)

A production-ready, stateless REST API built to manage employee records. This project demonstrates backend engineering best practices, including layered architecture, Role-Based Access Control (RBAC), and robust data mutation handling.

## 🚀 Project Overview

This API serves as a secure backend service for an employee directory. It goes beyond basic CRUD functionality by implementing strict authorization protocols and efficient partial update mechanisms, ensuring data integrity and system security.

### 🔥 Core Features

* **Multi-Tiered Architecture:** Strict separation of concerns across `entity`, `dao`, `service`, `rest`, and `security` packages for high maintainability.
* **Role-Based Access Control (RBAC):** Secured endpoints using Spring Security's `SecurityFilterChain` with distinct `EMPLOYEE`, `MANAGER`, and `ADMIN` permission levels.
* **Advanced Partial Updates:** Implemented custom HTTP `PATCH` logic using Jackson's `ObjectMapper` and `ObjectNode` to dynamically update specific fields without overwriting the entire record.
* **Data Integrity Protections:** Built-in safeguards, such as explicit ID overriding on `POST` requests and immutable ID checks on `PATCH` requests, to prevent malicious or accidental primary key modifications.
* **Stateless Design:** Configured to disable CSRF, adhering to modern stateless REST principles.
* **Zero-Boilerplate Persistence:** Leveraged Spring Data JPA (`JpaRepository`) for streamlined database interactions.

## 🛠️ Technology Stack

* **Language:** Java 
* **Framework:** Spring Boot (Spring Web, Spring Data JPA)
* **Security:** Spring Security (HTTP Basic Authentication)
* **ORM:** Hibernate / Jakarta Persistence
* **JSON Processing:** Jackson (`ObjectMapper`)

## 🔐 API Reference & Access Control

The API utilizes HTTP Basic Authentication. Access to endpoints is strictly governed by the authenticated user's role.

| Endpoint | HTTP Method | Required Role | Description |
| :--- | :---: | :---: | :--- |
| `/api/employees` | `GET` | `EMPLOYEE` | Retrieve a list of all employees. |
| `/api/employees/{id}` | `GET` | `EMPLOYEE` | Fetch details of a specific employee by ID. |
| `/api/employees` | `POST` | `MANAGER` | Add a new employee record. |
| `/api/employees` | `PUT` | `MANAGER` | Update an existing employee record fully. |
| `/api/employees/{id}` | `PATCH` | `MANAGER` | Partially update specific employee fields. |
| `/api/employees/{id}` | `DELETE` | `ADMIN` | Remove an employee record from the system. |

## 💡 Engineering Highlights

### Handling Dynamic PATCH Requests
Instead of forcing clients to send full object payloads for minor updates, the `PATCH` endpoint accepts a flexible `Map<String, Object>`. This map is seamlessly converted into a JSON node and merged with the existing database entity using Jackson, allowing for highly efficient, granular updates while protecting core identifiers.

### Bulletproof Entity Creation
To prevent clients from accidentally updating existing records during creation, the `POST` endpoint explicitly resets the entity ID (`theEmployee.setId(0);`) before passing it to the data access layer, guaranteeing a fresh `INSERT` command.

## ⚙️ Local Setup Instructions

1. Clone the repository: `git clone <your-repo-url>`
2. Configure your database connection in `application.properties` (e.g., MySQL or PostgreSQL).
3. Build the project using Maven: `mvn clean install`
4. Run the application: `mvn spring-boot:run`
5. Access the API at `http://localhost:8080/api/employees` using tools like Postman or cURL.

## 📸 API Testing & Security Demonstration

The following demonstrations validate the Role-Based Access Control (RBAC) and authentication mechanisms implemented via Spring Security. They illustrate how the API handles varying levels of access and authorization failures.

### 1. Authentication Failure (401 Unauthorized)
If a client attempts to access a protected endpoint with invalid credentials, the API intercepts the request. In this scenario, providing an incorrect password results in a `401 Unauthorized` status, preventing any interaction with the system.
<img width="1600" height="804" alt="WhatsApp Image 2026-06-17 at 11 10 49 AM" src="https://github.com/user-attachments/assets/158b66f7-735c-49e1-b772-2b139b524de2" />

![Authentication Failure - 401 Unauthorized]

### 2. Successful Authentication & Read Access (200 OK)
When valid credentials are provided, Spring Security authenticates the user. Here, the user `john` (configured with the `EMPLOYEE` role) requests the employee directory and successfully receives a `200 OK` status along with the JSON payload.

![Successful Authentication - 200 OK](image_a34fbe.png)

### 3. Authorization Failure: Insufficient Permissions (403 Forbidden)
While the user `john` is fully authenticated, his role only grants read access. Attempting to execute a `DELETE` request—which strictly requires `ADMIN` privileges—triggers a `403 Forbidden` response, protecting the data from unauthorized mutations.

![Authorization Failure - 403 Forbidden](image_a34fa0.jpg)

### 4. Successful Authorized Deletion (200 OK)
To perform destructive operations, the authenticated user must possess the correct role. Logging in as `susan`, who holds `ADMIN` authorities, allows the `DELETE` request to pass through the security filter chain successfully, resulting in the removal of the employee record.

![Authorized Deletion - 200 OK](WhatsApp%20Image%202026-06-17%20at%2011.10.49%20AM.jpeg)

---
   

---
*Designed and developed as a showcase of enterprise Java backend capabilities.*
