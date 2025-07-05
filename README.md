# SportsApp

SportsApp is a modern Android application designed to provide users with an engaging experience for exploring, tracking, and managing sports events and activities. Built with Kotlin and following clean architecture principles, the app is modularized for scalability and maintainability.

## Features
- Browse and search for sports events
- Mark favorite events
- View event details with rich UI
- Modular architecture (core, data, domain, ui)
- MVI pattern and use cases
- Navigation component integration

## Project Structure
```
SportsApp/
├── app/                # Main application module (Holds features, navigation, and UI)
├── core/               # Core modules
    ├── data/               # Data sources and repositories
    ├── domain/             # Business logic and use cases
    ├── ui/                 # UI components and presentation logic
```

## Architecture
- **MVI** (Model-View-ViewModel)
- **Clean Architecture** (Separation of concerns)
- **Navigation Component** for in-app navigation

![Architecture Diagram 1](/docs/images/app_arch.svg)
![Architecture Diagram 2](/docs/images/app_arch1.svg)
![Architecture Diagram 3](/docs/images/app_arch2.svg)

## Screenshots
See the `docs/images/` folder for UI previews:
- Home Screen

    ![Home Screen](/docs/images/home.png)

- Favorite Events
    
    ![Favorite Events](/docs/images/favorite_events.png)
    
- Expanded Card
    
    ![Expanded Card](/docs/images/expanded_card.png)

- Overview

    ![Overview](/docs/overview.mp4)

---


