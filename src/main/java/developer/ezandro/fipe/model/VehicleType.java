package developer.ezandro.fipe.model;

public enum VehicleType {
    CAR("carros", "Car"),
    MOTORCYCLE("motos", "Motorcycle"),
    TRUCK("caminhoes", "Truck");

    private final String apiPath;
    private final String displayName;

    VehicleType(String apiPath, String displayName) {
        this.apiPath = apiPath;
        this.displayName = displayName;
    }

    public String getApiPath() {
        return apiPath;
    }

    public String getDisplayName() {
        return displayName;
    }
}