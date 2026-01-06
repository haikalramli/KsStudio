package model;

public class OutdoorPackage extends Package {
    private int outdoorPackageId;
    private double distance;
    private double distancePricePerKm;
    private String location;

    public OutdoorPackage(int photographerId, String packageName, double price,
                         String category, int duration, String eventType,
                         String description, double distance, double distancePricePerKm,
                         String location) {
        super(photographerId, packageName, price, category, duration, eventType,
              "OUTDOOR", description);
        this.distance = distance;
        this.distancePricePerKm = distancePricePerKm;
        this.location = location;
    }

    public OutdoorPackage(int packageId, int photographerId, String packageName, double price,
                         String category, int duration, String eventType,
                         String description, double distance, double distancePricePerKm,
                         String location, java.time.LocalDateTime createdDate, 
                         java.time.LocalDateTime updatedDate, String status) {
        super(packageId, photographerId, packageName, price, category, duration, eventType,
              "OUTDOOR", description, createdDate, updatedDate, status);
        this.distance = distance;
        this.distancePricePerKm = distancePricePerKm;
        this.location = location;
    }

    public int getOutdoorPackageId() { return outdoorPackageId; }
    public void setOutdoorPackageId(int outdoorPackageId) { this.outdoorPackageId = outdoorPackageId; }
    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }
    public double getDistancePricePerKm() { return distancePricePerKm; }
    public void setDistancePricePerKm(double distancePricePerKm) { this.distancePricePerKm = distancePricePerKm; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}