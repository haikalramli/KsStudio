package model;

public class IndoorPackage extends Package {
    private int indoorPackageId;
    private int numberOfPax;
    private String backgroundType;

    public IndoorPackage(int photographerId, String packageName, double price,
                        String category, int duration, String eventType,
                        String description, int numberOfPax, String backgroundType) {
        super(photographerId, packageName, price, category, duration, eventType, 
              "INDOOR", description);
        this.numberOfPax = numberOfPax;
        this.backgroundType = backgroundType;
    }

    public IndoorPackage(int packageId, int photographerId, String packageName, double price,
                        String category, int duration, String eventType,
                        String description, int numberOfPax, String backgroundType,
                        java.time.LocalDateTime createdDate, java.time.LocalDateTime updatedDate, String status) {
        super(packageId, photographerId, packageName, price, category, duration, eventType,
              "INDOOR", description, createdDate, updatedDate, status);
        this.numberOfPax = numberOfPax;
        this.backgroundType = backgroundType;
    }

    public int getIndoorPackageId() { return indoorPackageId; }
    public void setIndoorPackageId(int indoorPackageId) { this.indoorPackageId = indoorPackageId; }
    public int getNumberOfPax() { return numberOfPax; }
    public void setNumberOfPax(int numberOfPax) { this.numberOfPax = numberOfPax; }
    public String getBackgroundType() { return backgroundType; }
    public void setBackgroundType(String backgroundType) { this.backgroundType = backgroundType; }
}