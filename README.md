# Job Comparison App

An Android application for entering, evaluating, and comparing job offers using configurable compensation and benefits criteria.

The app helps users compare opportunities beyond base salary by organizing job details, storing offers locally, and applying user-defined comparison weights.

## Features

- Enter and save current job information
- Add and manage job offers
- View stored job details
- Select jobs for side-by-side comparison
- Configure comparison preferences and weighting
- Rank and compare job opportunities
- Persist application data locally
- Android-native user interface

## Tech Stack

- **Java** — application logic and Android activities
- **Android SDK** — native mobile application development
- **XML** — layouts and UI resources
- **SQLite** — local data persistence
- **Gradle** — build and dependency management
- **JUnit** — unit testing

## Application Structure

```text
job-comparison-app/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── id/taryntang/seclass/jobcompare/
│   │   │   │       ├── adapters/
│   │   │   │       ├── dbhelpers/
│   │   │   │       └── models/
│   │   │   └── res/
│   │   │       ├── layout/
│   │   │       ├── drawable/
│   │   │       └── values/
│   │   └── test/
│   ├── build.gradle
│   └── proguard-rules.pro
├── gradle/
├── build.gradle
├── gradle.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── README.md
```

## Architecture

The application separates UI behavior, domain models, and persistence responsibilities.

```text
Android UI
    │
    ▼
Activities
    │
    ├──────────────► RecyclerView Adapters
    │
    ▼
Domain Models
    │
    ▼
SQLite Database Helper
    │
    ▼
Local Persistence
```

### Activities

Dedicated Android activities support major workflows including job detail entry, comparison selection, comparison results, and preference adjustment.

### Models

Domain model classes represent jobs, job details, locations, comparison settings, and comparison results.

### Persistence

A SQLite database helper provides local persistence so job information and comparison settings can be retained between application sessions.

## Job Comparison Workflow

1. Enter a current job or job offers.
2. Store compensation and benefit information for each opportunity.
3. Configure the relative importance of comparison criteria.
4. Select opportunities to compare.
5. Review the resulting comparison to support a job decision.

## Testing

The project includes unit tests for core job-comparison functionality and model behavior.

```bash
./gradlew test
```

On Windows:

```bash
gradlew.bat test
```

## Getting Started

### Prerequisites

- Android Studio
- Android SDK
- Java Development Kit compatible with the project configuration

### Run the Application

Clone the repository:

```bash
git clone https://github.com/taryntang/job-comparison-app.git
```

Open the project in Android Studio, allow Gradle to synchronize dependencies, select an Android emulator or connected device, and run the `app` configuration.

## Software Engineering Concepts Demonstrated

- Object-oriented design
- Native Android development
- Separation of UI, domain, and persistence logic
- Local database persistence
- Configurable business rules
- RecyclerView-based UI components
- XML-based Android layouts
- Unit testing
- Gradle build management
- Team-based software development
