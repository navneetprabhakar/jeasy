# jeasy — Rule Engine with Easy Rules + Spring Boot

A Spring Boot REST service that demonstrates a pluggable rule engine built on top of [Easy Rules](https://github.com/j-easy/easy-rules). Rules are registered in an in-memory registry and fired on demand by ID, so a request can compose and order any subset of available rules.

The bundled example processes a student marksheet: one rule computes the score card (marks obtained, total, percentage and grade), and a second rule generates a teacher's remark based on the resulting grade.

## Features

- Pluggable **rule registry** — rules are mapped to IDs and looked up at runtime
- Execute any combination of rules per request, in the order supplied (priority-driven)
- **Score Card rule** — sums marks, computes percentage, assigns a grade (`O`/`A`/`B`/`C`/`D`/`E`/`P`/`F`)
- **Remark rule** — derives a remark from the computed grade
- Prototype-scoped rule beans so each request runs with isolated state
- Centralised error handling for invalid or empty rule-ID lists
- Swagger UI for interactive API exploration

## Tech Stack

| Component | Technology |
|-----------|-----------|
| Language | Java |
| Framework | Spring Boot (spring-boot-starter-web) |
| Rule Engine | Easy Rules (`easy-rules-core` 4.1.0) |
| API Docs | Springfox Swagger 3.0.0 |
| Utilities | Lombok |
| Build | Maven |

## Getting Started

### Prerequisites

- Java (JDK)
- Maven 3.8+

### Build

```bash
mvn clean install
```

### Run

```bash
mvn spring-boot:run
```

The application starts on port `8080` with a context path of `/jeasy/rules`.

## Configuration

Configuration lives in `src/main/resources/application.properties`:

```properties
spring.application.name=<APP_NAME>
server.port=<PORT>
server.servlet.context-path=/jeasy/rules
```

## Usage

Execute rules by POSTing a marksheet plus the list of rule IDs to run.

- **Method:** `POST`
- **URL:** `http://localhost:8080/jeasy/rules/v1/execute`

Rule IDs:

| Rule ID | Rule | Description |
|---------|------|-------------|
| `1` | Score Card | Computes marks obtained, total, percentage and grade |
| `2` | Remark | Generates a remark based on the grade from the Score Card rule |

Sample request:

```json
{
  "marksheet": [
    { "subject": "Math",    "marks": 95, "totalMarks": 100 },
    { "subject": "Science", "marks": 88, "totalMarks": 100 },
    { "subject": "English", "marks": 76, "totalMarks": 100 }
  ],
  "ruleIds": [1, 2]
}
```

Sample response:

```json
{
  "grade": "A",
  "marksObtained": 259,
  "total": 300,
  "percentage": 86.33,
  "remarks": "Very good Effort"
}
```

> Note: the Remark rule depends on the grade produced by the Score Card rule, so include rule `1` before rule `2`.

### API Documentation

Once running, the Swagger UI is available at:

```
http://localhost:8080/jeasy/rules/swagger-ui/
```

## Project Structure

```
src/main/java/com/navneet/jeasy/
├── JeasyApplication.java              # Spring Boot entry point
├── config/
│   ├── RuleRegistry.java              # Maps rule IDs to rule classes; fires rules
│   └── SwaggerConfig.java             # Springfox configuration
├── constants/Constants.java          # Rule ID enum
├── controller/RuleEngineController.java  # POST /v1/execute
├── models/                           # Request, Response, WorkflowProcess
├── rules/                            # RuleTemplate, ScoreCardRules, RemarkRules
├── service/                          # RuleEngineService (interface + impl)
└── exception/                        # GlobalExceptionHandler, SystemException
```
