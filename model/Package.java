package model;

import java.time.LocalDateTime;

public class Package {
    private int packageId;
    private int photographerId;
    private String packageName;
    private double price;
    private String category;
    private int duration;
    private String eventType;
    private String packageType; // INDOOR or OUTDOOR
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String status;

    public Package(int photographerId, String packageName, double price, 
                   String category, int duration, String eventType, 
                   String packageType, String description) {
        this.photographerId = photographerId;
        this.packageName = packageName;
        this.price = price;
        this.category = category;
        this.duration = duration;
        this.eventType = eventType;
        this.packageType = packageType;
        this.description = description;
        this.status = "ACTIVE";
    }

    public Package(int packageId, int photographerId, String packageName, double price,
                   String category, int duration, String eventType, String packageType,
                   String description, LocalDateTime createdDate, LocalDateTime updatedDate, String status) {
        this.packageId = packageId;
        this.photographerId = photographerId;
        this.packageName = packageName;
        this.price = price;
        this.category = category;
        this.duration = duration;
        this.eventType = eventType;
        this.packageType = packageType;
        this.description = description;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.status = status;
    }

    public int getPackageId() { return packageId; }
    public void setPackageId(int packageId) { this.packageId = packageId; }
    public int getPhotographerId() { return photographerId; }
    public void setPhotographerId(int photographerId) { this.photographerId = photographerId; }
    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getPackageType() { return packageType; }
    public void setPackageType(String packageType) { this.packageType = packageType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    public LocalDateTime getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}