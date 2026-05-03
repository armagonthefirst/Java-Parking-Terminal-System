# Parking Terminal Management System

A Java-based desktop application for managing staff and visitor parking slots that I built in 2024. The system provides both a structured parking management backend and an interactive Swing GUI for adding slots, parking cars, removing cars, and viewing vehicle details.

The project was built to demonstrate object-oriented design, GUI event handling, input validation, and practical state management in a small desktop application.

---

## How to Run

### Running in Visual Studio Code

1. Open the project folder in **Visual Studio Code**.
2. Make sure the **Java Extension Pack** is installed.
3. Open `GUIParkingTerminal.java`.
4. Click the **Run** button above the `main` method.

Alternatively, run the project from the VS Code terminal:

```bash
javac *.java
java GUIParkingTerminal
```

### Running in BlueJ

1. Open the project in **BlueJ**.
2. Right-click the `GUIParkingTerminal` class.
3. Select:

```text
void main(String[] args)
```

4. Run it without entering any arguments.

---

## Features

- Create and manage staff and visitor parking slots
- Add and delete parking slots
- Park cars into available slots
- Prevent duplicate car registrations from being parked
- Prevent cars from being parked in occupied slots
- Search for parked cars by registration number
- Remove cars from parking slots
- View car details, including parking duration and calculated parking fee
- Interactive Java Swing GUI with clickable parking slot cards
- Error and confirmation messages displayed through dialog boxes
- Separate backend logic from GUI presentation for cleaner structure

---

## Tech Stack

- **Language:** Java
- **GUI Framework:** Java Swing
- **Data Structure:** ArrayList
- **IDE Used:** Visual Studio Code / BlueJ compatible
- **Java Version:** JDK 11+

---

## Project Overview

The application simulates a parking terminal used to manage a small car park. It supports two parking categories:

- **Staff parking slots**
- **Visitor parking slots**

Each slot can either be empty or occupied by a car. When a car is parked, the system stores its registration details and parking time. The application can then calculate how long the car has been parked and display the related parking fee.

The system was designed using object-oriented programming principles, with separate classes responsible for cars, parking slots, car park operations, and the user interface.

---

## Main Components

### `Car.java`

Represents a parked car. It stores car details such as the registration number, owner details, parking time, and parking duration. It also handles displaying car information and calculating the parking fee.

### `Parkingslot.java`

Represents an individual parking slot. Each slot has a slot ID, a slot type, and an occupancy status. A slot can either be empty or contain a parked car.

### `Carpark.java`

Manages the overall collection of parking slots using an `ArrayList`. It contains the core logic for adding slots, deleting slots, parking cars, removing cars, searching for cars, and listing parking slot information.

### `Application.java`

Provides the original terminal-based version of the system. This allows the parking system to be used through a text-based menu.

### `GUIParkingTerminal.java`

Provides the graphical user interface using Java Swing. It displays staff and visitor parking slots visually and allows users to manage the car park through buttons, dialogs, and clickable slot cards.

---

## Object-Oriented Design

The system separates responsibilities across different classes:

- `Car` handles car-related data and parking fee calculation.
- `Parkingslot` manages the state of each parking slot.
- `Carpark` controls the main parking operations.
- `Application` and `GUIParkingTerminal` handle user interaction.

This structure keeps the business logic separate from the interface, making the project easier to understand, test, and extend.

---

## Validation and Error Handling

The system includes validation for common parking management issues, including:

- Parking in a slot that does not exist
- Parking in a slot that is already occupied
- Parking the same car registration more than once
- Removing a car from an empty slot
- Deleting an invalid parking slot
- Entering incomplete or invalid car details

In the GUI version, errors and confirmation messages are shown using dialog boxes instead of console output.

---
