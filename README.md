
# Book Inventory Management System

## Overview

The Book Inventory Management System is a project designed to manage and track books in a library or inventory system. It provides RESTful APIs for various book-related operations.

## Swagger Documentation

Swagger docs can be accessed via this link:
http://localhost:8081/swagger-ui/index.html#/

## Features

- Add a new book
- Update book details
- Delete a book
- Get a list of all books
- Track the status of a book
- Change Status of a Book to deleted

## Technologies Used

- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- JSON Web Tokens (JWT)
- Jenkins for CICD

## Getting Started

### Prerequisites

- Java 18 or later
- Maven
- MySQL

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/Kagweitis/book-inventory.git

2. Run the application:

    ```bash
   mvn spring-boot:run

## Security

The application uses JSON Web Tokens (JWT) for authentication. Make sure to include the JWT token in the headers of your requests.
