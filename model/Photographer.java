package model;

import java.time.LocalDateTime;

public class Photographer {
    private int photographerId;
    private String name;
    private String email;
    private String phone;
    private String password;
    private LocalDateTime createdDate;
    private String status;

    // Constructor tanpa ID (untuk register baru)
    public Photographer(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    // Constructor dengan ID
    public Photographer(int photographerId, String name, String email, 
                       String phone, String password, LocalDateTime createdDate, String status) {
        this.photographerId = photographerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.createdDate = createdDate;
        this.status = status;
    }

    // Getter dan Setter
    public int getPhotographerId() {
        return photographerId;
    }

    public void setPhotographerId(int photographerId) {
        this.photographerId = photographerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}