# ETLProject – Java-Based ETL Automation Pipeline

![Java](https://img.shields.io/badge/Java-11%2B-blue.svg)
![Maven](https://img.shields.io/badge/Build-Maven-brightgreen)
![Platform](https://img.shields.io/badge/Platform-macOS%20%7C%20Linux-lightgrey)
![Status](https://img.shields.io/badge/Status-Production--Ready-success)

This project delivers a complete **ETL (Extract, Transform, Load)** solution using Java and SQLite. It automates the process of:
- Creating and populating a database
- Exporting data to a well-structured CSV file
- Compressing the output into a ZIP archive
- Sending it via email through SMTP
- Running automatically on a schedule via **`crontab`**

> Ideal for learning, automation tasks, and production-ready pipelines on macOS/Linux environments.

---

## 📝 Table of Contents
- [📁 Project Structure](#-project-structure)
- [⚙️ Configuration (config.properties)](#️-configuration-configproperties)
- [🛠️ Build & Run Instructions](#️-build--run-instructions)
- [📦 JAR Creation and Classpath](#-jar-creation-and-classpath)
- [🕒 Scheduling with Cron on macOS](#-scheduling-with-cron-on-macos)
- [🔄 ETL Execution Flow](#-etl-execution-flow)
- [✅ Prerequisites](#-prerequisites)
- [🧪 Testing](#-testing)
- [📫 Contact](#-contact)

---

## 📁 Project Structure

```text
ETLProject/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── org/
│   │           └── example/
│   │               ├── ETLRunner.java
│   │               ├── dto/
│   │               │   └── EmployeeDTO.java
│   │               ├── entity/
│   │               │   └── Employee.java
│   │               ├── mail/
│   │               │   └── SendEmail.java
│   │               ├── etl/
│   │               │   ├── CreateDB.java
│   │               │   ├── ExtractData.java
│   │               │   ├── LoadToCSV.java
│   │               │   └── ZipCSV.java
│   │               └── translator/
│   │                   └── EmployeeTranslator.java
│
│   ├── test/
│   │   └── java/
│   │       └── org/
│   │           └── example/
│   │               ├── mail/
│   │               │   └── SendEmailTest.java
│   │               ├── etl/
│   │               │   ├── CreateDBTest.java
│   │               │   ├── ExtractDataTest.java
│   │               │   ├── LoadToCSVTest.java
│   │               │   └── ZipCSVTest.java
│   │               └── translator/
│   │                   └── EmployeeTranslatorTest.java
│
├── lib/                                # External JARs
│   ├── jakarta.mail-2.0.1.jar
│   └── sqlite-jdbc-3.49.1.0.jar
├── config.properties.template          # Configuration file template
├── pom.xml                             # Maven project file
├── etl.db                              # SQLite database (optional)
├── README.md
```
---

## ⚙️ Configuration: config.properties

To set up your environment:

1. Copy the template:
   ```bash
   cp config.properties.template config.properties
   ``` 
2. Update values in config.properties:
   ```properties
   SMTP_SERVER=smtp.gmail.com
   SMTP_PORT=587
   
   EMAIL_SENDER=${EMAIL_SENDER}
   EMAIL_PASSWORD=${EMAIL_PASSWORD}
   
   EMAIL_RECIPIENTS=recipient1@example.com,recipient2@example.com
   ```

3. Set environment variables in your shell:
   ```bash
    export EMAIL_SENDER="your-email@gmail.com"
    export EMAIL_PASSWORD="your-app-password"
   ```

🔐 Use App Passwords if you're using Gmail.
Here the App Password (Gmail): https://myaccount.google.com/apppasswords

---

## 🧱 Build & Run Instructions
✅ Prerequisites
* Java 11 or higher
* Maven 3.x
* Internet access to download dependencies
* A Gmail (or compatible SMTP) account for email delivery

---

## 📦 Build the Project
```bash
mvn clean package
```
This will create a JAR file under target/ETLProject.jar.

---

## ▶️ Run the ETL Pipeline
```bash
java -cp "target/ETLProject.jar:lib/*" org.example.ETLRunner
```
Make sure the lib/ folder contains all required external JARs (jakarta.mail, sqlite-jdbc, etc.).

---

## 🕒 Scheduling with Cron
To automate the ETL process daily (e.g., 7:00 AM):

1. Open the crontab:
```bash
crontab -e
```

2. Add this line (edit paths as needed):
```cronexp
0 7 * * * /usr/bin/java -cp "/Users/rafaelpadron/IdeaProjects/ETLProject/target/ETLProject.jar:/Users/rafaelpadron/IdeaProjects/ETLProject/lib/*" org.example.ETLRunner >> ~/etl.log 2>&1
```

This sets the job to run silently and log output/errors to etl.log.

---

## 🔄 ETL Execution Flow
```text
CreateDB  →  ExtractData  →  EmployeeTranslator  →  LoadToCSV  →  ZipCSV  →  SendEmail
```
Each module has a single responsibility, making the pipeline easy to debug, extend, and maintain.

---

## 🧪 Run Tests
```bash
mvn test
```
Test coverage includes all core modules and email functionality.

---

## 📫 Contact
For questions, suggestions, or contributions, feel free to reach out:

Rafael Padron
📍 Farmington Hills, MI
📧 rafpad@gmail.com
🔗 LinkedIn

---

## 🚫 Notes
* ❗ Never commit passwords or secrets to version control.
* ✅ Always use environment variables or secure vaults to store credentials.
* 💡 You can extend this project to include validation, logging frameworks, or REST API triggers.

---



