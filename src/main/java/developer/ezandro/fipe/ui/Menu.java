package developer.ezandro.fipe.ui;

import com.fasterxml.jackson.core.type.TypeReference;
import developer.ezandro.fipe.model.*;
import developer.ezandro.fipe.service.FipeApiClient;
import developer.ezandro.fipe.service.FipeDataDeserializer;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private static final int MIN_CHOICE = 1;
    private static final int MAX_CHOICE = 3;
    private static final String VALUES_HEADER = "\nAll vehicles with values by year:\n--------------------------------";
    private static final String VEHICLE_FORMAT = "Vehicle[value=%s, brand=%s, model=%s, year=%s, fuel=%s]%n";

    private final FipeDataDeserializer fipeDataDeserializer = new FipeDataDeserializer();
    private final FipeApiClient fipeApiClient = new FipeApiClient();
    private final Scanner scanner = new Scanner(System.in);

    public void displayMainMenu() {
        this.printVehicleOptions();
        VehicleType vehicleType = this.getSelectedVehicleType();

        try {
            this.processVehicleSelection(vehicleType);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private VehicleType getSelectedVehicleType() {
        int choice = this.getUserChoice();
        return switch (choice) {
            case 1 ->
                    VehicleType.CAR;
            case 2 ->
                    VehicleType.MOTORCYCLE;
            case 3 ->
                    VehicleType.TRUCK;
            default ->
                    throw new IllegalStateException("Unexpected value: " + choice);
        };
    }

    private void processVehicleSelection(VehicleType vehicleType) throws IOException {
        System.out.println("\nAvailable brands:");
        this.fetchAndDisplayBrands(vehicleType);

        System.out.print("\nEnter the brand code: ");
        int brandCode = this.readValidInt();
        scanner.nextLine(); // Consumir a quebra de linha pendente

        System.out.println("\nAvailable models for selected brand:");
        List<Vehicle> models = this.fetchAndDisplayModels(vehicleType, brandCode);

        String modelName;
        do {
            System.out.print("\nEnter part of the vehicle name to search: ");
            modelName = scanner.nextLine().trim();

            if (modelName.isEmpty()) {
                System.out.println("Error: Search term cannot be empty!");
            }
        } while (modelName.isEmpty());

        List<Vehicle> filteredModels = this.filterModelsByName(models, modelName);
        if (filteredModels.isEmpty()) {
            System.out.println("No models found with: " + modelName);
            return;
        }

        System.out.println("\nMatching models:");
        this.displayModels(filteredModels);

        int modelCode;
        while (true) {
            System.out.print("\nEnter the model code to check values: ");
            try {
                modelCode = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException _) {
                scanner.nextLine();
                System.out.println("Error: Please enter a valid number for the model code!");
            }
        }

        this.displayVehicleValuesByYear(vehicleType, brandCode, modelCode);
    }
    private void fetchAndDisplayBrands(VehicleType vehicleType) throws IOException {
        String brandsResponse = this.fipeApiClient.getData("/" + vehicleType.getApiPath() + "/marcas");
        List<Vehicle> brands = this.fipeDataDeserializer.getData(brandsResponse, new TypeReference<>() {});
        brands.forEach(System.out::println);
    }

    private List<Vehicle> fetchAndDisplayModels(VehicleType vehicleType, int brandCode) throws IOException {
        String modelsResponse = this.fipeApiClient.getData(
                "/" + vehicleType.getApiPath() + "/marcas/" + brandCode + "/modelos"
        );

        Map<String, Object> responseMap = this.fipeDataDeserializer.getData(modelsResponse, new TypeReference<>() {});

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> modelsMap = (List<Map<String, Object>>) responseMap.get("modelos");

        List<Vehicle> models = modelsMap.stream()
                .map(map -> createVehicle(vehicleType, map))
                .toList();

        models.forEach(System.out::println);
        return models;
    }

    private Vehicle createVehicle(VehicleType type, Map<String, Object> data) {
        String code = data.get("codigo").toString();
        String name = (String) data.get("nome");

        return switch (type) {
            case CAR ->
                    new Car(code, name);
            case MOTORCYCLE ->
                    new Motorcycle(code, name);
            case TRUCK ->
                    new Truck(code, name);
        };
    }

    private List<Vehicle> filterModelsByName(List<Vehicle> models, String name) {
        return models.stream()
                .filter(vehicle -> vehicle.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    private void displayModels(List<Vehicle> models) {
        models.forEach(System.out::println);
    }

    private void displayVehicleValuesByYear(VehicleType vehicleType, int brandCode, int modelCode) throws IOException {
        String yearsResponse = this.fipeApiClient.getData(
                "/" + vehicleType.getApiPath() + "/marcas/" + brandCode + "/modelos/" + modelCode + "/anos"
        );

        List<Map<String, Object>> yearsData = this.fipeDataDeserializer.getData(yearsResponse, new TypeReference<>() {});

        System.out.println(VALUES_HEADER);
        yearsData.forEach(yearData -> this.displayYearData(vehicleType, brandCode, modelCode, yearData));
    }

    private void displayYearData(VehicleType vehicleType, int brandCode, int modelCode, Map<String, Object> yearData) {
        try {
            String yearCode = (String) yearData.get("codigo");
            String vehicleResponse = this.fipeApiClient.getData(
                    "/" + vehicleType.getApiPath() + "/marcas/" + brandCode + "/modelos/" + modelCode + "/anos/" + yearCode
            );

            Map<String, Object> vehicleDetails = this.fipeDataDeserializer.getData(vehicleResponse, new TypeReference<>() {});
            this.printVehicleDetails(vehicleDetails);
        } catch (IOException _) {
            System.err.println("Error fetching data for year: " + yearData.get("codigo"));
        }
    }

    private void printVehicleDetails(Map<String, Object> vehicleData) {
        System.out.printf(VEHICLE_FORMAT,
                vehicleData.get("Valor"),
                vehicleData.get("Marca"),
                vehicleData.get("Modelo"),
                vehicleData.get("AnoModelo"),
                vehicleData.get("Combustivel")
        );
    }

    private void printVehicleOptions() {
        System.out.print("""
                
                === Vehicle Type ===
                1 - Car
                2 - Motorcycle
                3 - Truck
                
                Enter your choice:\s""");
    }

    private int getUserChoice() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    this.showError("Field cannot be empty!");
                    continue;
                }

                int choice = Integer.parseInt(input);

                if (choice >= MIN_CHOICE && choice <= MAX_CHOICE) {
                    return choice;
                }

                this.showError("Must be between " + MIN_CHOICE + " and " + MAX_CHOICE);
            } catch (NumberFormatException _) {
                this.showError("Invalid number format!");
            }
        }
    }

    private void showError(String message) {
        System.out.printf("%nError: %s%nEnter your choice: ", message);
    }

    private int readValidInt() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException _) {
                scanner.nextLine();
                this.showError("Invalid number format!");
            }
        }
    }
}