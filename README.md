
# DishCoveryApp

**Smart Recipe Suggestions for a Zero-Waste Kitchen**  
_A JavaFX application that helps users reduce food waste by generating recipes based on their available ingredients._

---

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Architecture](#architecture)
- [Technologies Used](#technologies-used)
- [Team Members](#team-members)
- [Screenshots](#screenshots)
- [Future Improvements](#future-improvements)
- [License](#license)

---

## Overview

DishCoveryApp is a Java-based desktop application designed to address the growing problem of food waste, especially among students and young adults. By inputting ingredients currently available in their pantry, users can discover practical recipes that promote sustainability and responsible consumption—aligned with UN Sustainable Development Goals (SDGs 2 and 12).

---

## Features

- **Pantry Management**: Users can input the ingredients they currently have at home. The app then suggests recipes based on these ingredients. It also highlights which ingredients are **missing** for each suggested recipe, helping users plan meals or grocery trips more efficiently.
- **Smart Recipe Matching**: Suggests meals based on ingredient matches and displays missing ingredients if needed.
- **Favorites**: Mark recipes as favorites and view them later.
- **Add Custom Recipes**: Save your own recipes with ingredient lists and cooking instructions.
- **Image Upload**: Attach images for visual recipe cards.
- **Persistent Storage**: Saves data locally via JSON files.
- **Error Handling**: Input validation and user-friendly error popups.
- **Polished GUI**: Built with JavaFX and styled using CSS.

---

## Installation

1. **Clone the Repository**  
   ```bash
   git clone https://github.com/yourusername/DishCoveryApp.git
   cd DishCoveryApp

2. **Open the Project**

   * Use **IntelliJ IDEA** (recommended).
   * Import as a Maven/Gradle/JavaFX project as applicable.

3. **Install Requirements**

   * Java 8 or higher
   * JavaFX SDK
   * Gson (for JSON handling)
   * SceneBuilder (optional but useful for editing FXML)

4. **Run the App**
   Run `MainApp.java` to launch the JavaFX GUI.

---

## Usage

* Launch the app and navigate through the **Main Menu**.
* Use **Add Recipe** to create new recipes with image, ingredients, and steps.
* Use **Pantry Manager** to update what ingredients you have at home.

  * Matched recipes will be displayed based on ingredient overlap.
  * The app also shows **missing ingredients** needed to complete the recipe.
* Click **Find Recipe** to search for recipes by name.
* View and manage your **Favorites** via a dedicated section.

---

## Architecture

The app follows a modular, Object-Oriented Programming (OOP) structure with MVC-like principles.

### Main Packages:

* **controller**: JavaFX Controllers for UI interaction (e.g., `AddRecipeController`)
* **model**: Core logic/data (e.g., `Recipe`, `RecipeRepository`)
* **view**: FXML files for UI layout (e.g., `MainMenu.fxml`, `AddRecipe.fxml`)

### OOP Concepts Applied:

* **Encapsulation**: Private fields, getter/setter control
* **Abstraction**: Abstract base controller for navigation logic
* **Inheritance**: All controllers inherit from `BaseController`
* **Polymorphism**: Overridden methods like `initialize()` across scenes

### Key Controllers

* **PantryManagerController**:

  * Handles pantry input and matching logic.
  * Filters saved recipes based on overlap with available ingredients.
  * Displays matched recipes **and** missing ingredients for each one.

---

## Technologies Used

| Tech/Library  | Purpose                      |
| ------------- | ---------------------------- |
| Java          | Core programming language    |
| JavaFX        | Graphical User Interface     |
| Gson          | JSON file parsing            |
| SceneBuilder  | Designing FXML layouts       |
| IntelliJ IDEA | Main development environment |

---

## Group Members

| Name                        | Student ID | Roles                               |
| --------------------------- | ---------- | ----------------------------------- |
| **Elaiza Yvon M. Balono**   | 12478970   | Backend Dev, Testing, Documentation |
| **Valbert R. Butas Jr.**    | 12475432   | Backend Dev, Model Dev              |
| **Elisha Mielle A. Catiis** | 12475424   | Backend Dev, UI/UX, Model Dev       |

---

## Future Improvements

* Implement a **Login/User Profile** system
* Enhance **ingredient matching** with fuzzy logic
* Support for **unit conversion**
* Integration with **online recipe APIs**
* Dark mode & accessibility features

---

This project is for academic purposes under LBYCPEI - De La Salle University.
All rights reserved © 2025.

---
