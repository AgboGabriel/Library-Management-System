# Library Management System

A robust, object-oriented Library Management System designed to digitize inventory tracking, member management, and circulation workflows. Currently built as a resilient Java console application with strict separation of concerns, architected to transition into a full-stack web application using **Spring Boot**, **React**, and **PostgreSQL**.

---

## Architecture & Design Highlights

Rather than building a monolithic script, the core business logic is strictly decoupled from the user interface (`Library` contains zero console I/O). This ensures the domain model and service rules can be ported directly into a RESTful Spring Boot backend.

* **Object-Oriented Modeling:** Uses an abstract `LibraryItem` base class extended by concrete media types (`Book`, `DVD`, `Magazine`), leveraging polymorphism to handle varying loan periods and daily late-fee rates without conditional type-checking.
* **Interface-Driven Search & Sorting:** Implements a custom `Searchable` contract for case-insensitive multi-field queries, alongside `Comparable` and custom `Comparator` implementations for sorting by title and due date.
* **Defensive Domain Rules & Custom Exceptions:** Enforces business constraints (3-item borrowing cap, $5.00 unpaid fee threshold, duplicate ID prevention) via checked domain exceptions (`ItemNotFoundException`, `ItemNotAvailableException`, `MemberNotFoundException`, `MemberLimitExceededException`).
* **Encapsulation & Testability:** Protects internal collections via defensive copying and injects dates (`LocalDate`) as method parameters rather than hardcoding system clocks, making overdue and fee calculations deterministic and testable.

---

## Current Features (Console Edition)

* **Catalog Management:** Add and remove Books, DVDs, and Magazines with unique ID enforcement and active-loan deletion guards.
* **Member Circulation:** Register members, borrow/return items with automatic due-date calculation, and track/settle late fees.
* **Search & Reporting:** Keyword search across titles, authors, and directors; real-time views for available, borrowed, overdue, and sorted inventory.
* **Fault-Tolerant CLI:** Graceful input validation and exception handling ensuring the application never crashes on malformed input or invalid state transitions.
* **Session Date Simulation:** Built-in date override allowing testers to fast-forward the session clock to verify overdue reports and late-fee calculations.

---

## Roadmap: Full-Stack Migration

The next phase of this project involves evolving the standalone Java core into a decoupled full-stack application:

1. **Backend (Spring Boot & Java):**
   * Expose the `Library` service layer as RESTful API endpoints.
   * Map custom domain exceptions to standardized HTTP status codes via `@ControllerAdvice`.
2. **Database (PostgreSQL & Spring Data JPA):**
   * Replace in-memory `ArrayList`/`HashMap` storage with persistent relational tables.
   * Implement JPA inheritance (`@Inheritance`) for `LibraryItem` subclasses (`Book`, `DVD`, `Magazine`) and relational mappings for member loans.
3. **Frontend (React):**
   * Build a responsive librarian and member dashboard for catalog browsing, real-time search filtering, checkout/return workflows, and overdue fee management.

---

## Getting Started (Current Version)

### Prerequisites
* Java Development Kit (JDK) 17 or higher

### Running the Application
1. Clone the repository:
   ```bash
   git clone [https://github.com/AgboGabriel/Library-Management-System.git](https://github.com/AgboGabriel/Library-Management-System.git)
