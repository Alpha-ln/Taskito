# Taskito

A clean, focused to-do app for Android that helps you organize tasks by priority and track your progress with built-in analytics. See your completion trends, streaks, and daily productivity at a glance.

## Screenshots

<!-- Replace these with your own screenshots, e.g. put images in a /screenshots folder -->

| Home Screen | Add New Task | Insights |
|-------|----------|----------|
| ![Home](screenshots/tasks.png) | ![New Task](screenshots/calendar.png) | ![Insights](screenshots/insights.png) |

## Features

**Task management**
- Add, edit, complete, and delete tasks
- Priority levels: High, Medium, Low
- Due dates with overdue tracking

**Calendar**
- Month view with a dot under every date that has tasks
- Tap a date to see the tasks scheduled for it

**Insights**
- Completion rate donut chart (completed vs pending)
- Current streak and overdue count
- Tasks completed per day (weekly bar chart)
- Priority breakdown (stacked bar)
- Activity heatmap for the last 4 weeks

## Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose and Material 3
- **Architecture:** MVVM with a Repository layer
- **Local storage:** Room
- **Async:** Kotlin Coroutines and Flow
- **Charts:** Custom Compose `Canvas` components (no chart library)

## Project Structure

```
app/src/main/java/com/example/todoapp/
├── analytics/     # Insights screen and chart components
├── calendar/      # Month calendar with task indicators
├── data/          # Room entities, DAOs, repositories
├── ui/            # Shared components and theme
└── MainActivity.kt
```

## Getting Started

### Requirements
- Android Studio (latest stable)
- JDK 17
- Android SDK 26 or higher

### Build and run

```bash
git clone https://github.com/<your-username>/taskito.git
cd taskito
```

1. Open the project in Android Studio
2. Let Gradle sync finish
3. Select an emulator or a connected device
4. Press **Run**

## Roadmap

- [ ] Due-date reminders and notifications
- [ ] Search and sorting
- [ ] Dark mode polish
- [ ] Unit and UI tests
- [ ] Play Store release

