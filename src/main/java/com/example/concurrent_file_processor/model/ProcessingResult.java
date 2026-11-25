package com.example.concurrent_file_processor.model;

public class ProcessingResult {
    private String fileName;
    private int totalRecords;
    private int validRecords;
    private int invalidRecords;
    private double totalSalary;
    private double averageSalary;

    public ProcessingResult(String fileName, int totalRecords, int validRecords, int invalidRecords, double totalSalary, double averageSalary) {
        this.fileName = fileName;
        this.totalRecords = totalRecords;
        this.validRecords = validRecords;
        this.invalidRecords = invalidRecords;
        this.totalSalary = totalSalary;
        this.averageSalary = averageSalary;
    }

    public String getFileName() {
        return fileName;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public int getValidRecords() {
        return validRecords;
    }

    public int getInvalidRecords() {
        return invalidRecords;
    }

    public double getTotalSalary() {
        return totalSalary;
    }

    public double getAverageSalary() {
        return averageSalary;
    }
}
