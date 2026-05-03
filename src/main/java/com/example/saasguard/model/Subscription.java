package com.example.saasguard.model;

import java.time.LocalDateTime;
import java.sql.Timestamp;

public class Subscription {
    private int id;
    private String name;
    private double monthlyCost;
    private int usageCount;
    private LocalDateTime lastLogin;

    // Constructor 1 (INSERT use)
    public Subscription(String name, double monthlyCost) {
        this.name = name;
        this.monthlyCost = monthlyCost;
    }

    // Constructor 2 (FETCH use)
    public Subscription(int id, String name, double monthlyCost, Timestamp lastLogin, int usageCount) {
        this.id = id;
        this.name = name;
        this.monthlyCost = monthlyCost;
        this.lastLogin = (lastLogin != null) ? lastLogin.toLocalDateTime() : null;
        this.usageCount = usageCount;
    }

    public void recordUsageCount() {
        usageCount++;
        lastLogin = LocalDateTime.now();
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMonthlyCost() {
        return monthlyCost;
    }

    public double getYearlyCost() {
        return monthlyCost * 12;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    // Setter
    public void setLastLogin(LocalDateTime date) {
        this.lastLogin = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMonthlyCost(double monthlyCost) {
        this.monthlyCost = monthlyCost;
    }

    public void setId(int id) {
        this.id = id;
    }
}

