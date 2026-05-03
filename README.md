# Parking Terminal Management System

A Java-based desktop application for managing staff and visitor parking slots. The system provides both a structured parking management backend and an interactive Swing GUI for adding slots, parking cars, removing cars, and viewing vehicle details.

The project was built to demonstrate object-oriented design, GUI event handling, input validation, and practical state management in a small desktop application.

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
