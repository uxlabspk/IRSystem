# IR System

## Overview
This project is an Information Retrieval (IR) System built with Spring Boot. It provides functionality for processing, analyzing, and retrieving information from documents, particularly PDFs.

If you find this code useful, please consider starring this repository!

## Project Structure
- `/src` - Source code directory
    - `/main/java` - Java source files
    - `/main/resources` - Configuration files and resources
    - `/test` - Test files
- `/pdfs` - Directory for PDF documents
- `/output` - Directory for generated output files
- `/.mvn`, `mvnw`, `mvnw.cmd` - Maven wrapper files

## Prerequisites
- Java 24
- Maven

## Getting Started
1. Clone the repository
2. Build the project using Maven:
   ```
   ./mvnw clean install
   ```
   or on Windows:
   ```
   mvnw.cmd clean install
   ```
3. Run the application as a Spring Boot app:
   ```
   ./mvnw spring-boot:run
   ```

## Features
- Document processing and analysis
- Information retrieval capabilities
- PDF handling functionality using Apache PDFBox
- Web-based interface using Thymeleaf templates

## Technologies
This project is developed using:
- Spring Boot 3.5.0
- Thymeleaf for server-side templating
- Apache PDFBox 2.0.27 for PDF processing
- Java 24

## Development
Spring Boot DevTools are included for enhanced development experience with features like:
- Automatic restarts when code changes
- LiveReload support automatically refreshing the browser
- Enhanced development-time property defaults
