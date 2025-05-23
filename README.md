# 🚗 FIPE API

A Java application that queries vehicle pricing data from the official FIPE table (Tabela FIPE).

## 📋 Overview

This application provides a simple and intuitive interface to access the FIPE table, which contains reference prices for vehicles in Brazil. Users can search for cars, motorcycles, and trucks to get their current market values according to the official FIPE database.

## 🔗 API Reference

This project consumes the public [FIPE API](https://deividfortuna.github.io/fipe/) to fetch vehicle pricing data. The API provides comprehensive information about various vehicle types, including:

- Brand listings
- Model listings by brand
- Year/version listings by model
- Detailed pricing information

## 🛠️ Technologies Used

- **Java 24** - Latest Java version with enhanced features
- **REST API** - HTTP requests for external API communication
- **Jackson** - Powerful JSON parsing and deserialization library
- **Maven** - Dependency management and build automation tool

## 📁 Project Structure

```
fipe-api/
├── src/
│   └── main/java/developer/ezandro/fipe/
│       ├── model/       # Vehicle entities
│       │   ├── Car.java             # Car model with specific attributes
│       │   ├── Motorcycle.java      # Motorcycle model with engine capacity
│       │   └── Truck.java           # Truck model with load capacity and axles
│       ├── service/     # API integration
│       │   ├── FipeApiClient.java        # HTTP client for API requests
│       │   └── FipeDataDeserializer.java # JSON data processing
│       ├── ui/          # User interface
│       │   └── Menu.java                 # Interactive console menu
│       └── FipeApplication.java  # Main class and entry point
├── README.md            # Project documentation
└── pom.xml              # Maven configuration and dependencies
```

## ▶️ How to Run

### Requirements

- Java 24 JDK installed
- Maven (included in project via wrapper)

### Running the Application

1. Clone the repository:
   ```bash
   git clone https://github.com/ezandro/fipe-api.git
   cd fipe-api
   ```

2. Build the project:
   ```bash
   ./mvnw clean package
   ```

3. Run using Maven wrapper:
   ```bash
   ./mvnw spring-boot:run
   ```
   
   Or directly via Java:
   ```bash
   java -cp target/classes developer.ezandro.fipe.FipeApplication
   ```

4. Follow the interactive menu to query vehicle data:
   - Select vehicle type (car, motorcycle, truck)
   - Choose brand from the list
   - Select model
   - Choose year/version
   - View detailed pricing information

## 🔍 Features

- **Multi-vehicle support**: Query prices for cars, motorcycles, and trucks
- **Interactive navigation**: User-friendly console interface
- **Detailed information**: Complete vehicle specifications and current market values
- **Error handling**: Robust error management for API communication issues

## 👨‍💻 Author

Project developed by **Ezandro** as a practice in Java and REST API consumption.

## 📄 License

This project is open source and available under the MIT License.
