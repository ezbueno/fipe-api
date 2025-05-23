# Fipe API

A Java project to consume the FIPE API and retrieve vehicle pricing information based on vehicle type, brand, and model.

## Technologies Used

- Java 24
- REST API consumption
- JSON parsing with Jackson

## Project Structure

```
fipe-api/
├── src/
│   └── main/
│       └── java/
│           └── developer/
│               └── ezandro/
│                   └── fipe/
│                       ├── model/
│                       │   ├── Car.java
│                       │   ├── Motorcycle.java
│                       │   ├── Truck.java
│                       │   ├── Vehicle.java
│                       │   └── VehicleType.java
│                       ├── service/
│                       │   ├── FipeApiClient.java
│                       │   ├── FipeDataDeserializer.java
│                       │   └── IFipeDataDeserializer.java
│                       ├── ui/
│                       │   └── Menu.java
│                       └── FipeApplication.java
├── README.md
├── .gitignore
└── lib/
```

## How to Run

1. Make sure you have Java 24 installed.
2. Compile the project with your preferred IDE or command line.
3. Run the main class `developer.ezandro.fipe.FipeApplication`.
4. Use the menu to interact with the application and retrieve vehicle pricing data.

## Author

Project developed by **Ezandro** as a practice in Java and REST API consumption.
