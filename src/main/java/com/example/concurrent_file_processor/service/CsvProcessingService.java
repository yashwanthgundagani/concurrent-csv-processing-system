package com.example.concurrent_file_processor.service;

import com.example.concurrent_file_processor.model.Employee;
import com.example.concurrent_file_processor.model.ProcessingResult;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvProcessingService {
    public ProcessingResult processCsv(Path filePath)
            throws IOException {
        int totalRecords = 0;
        int invalidRecords = 0;
        double totalSalary = 0;

        //why the list is created inside the method?
        List<Employee> employees=new ArrayList<>();

        try (CSVReader reader =
                new CSVReader(Files.newBufferedReader(filePath))) {
            //Skip header
            reader.readNext();
            String[] row;
            while ((row = reader.readNext()) != null) {
                totalRecords++;
                try {
                    if (row.length != 4) {
                        throw new IllegalArgumentException(
                                "Invalid column count");
                    }

                    int id = Integer.parseInt(row[0].trim());
                    String name = row[1].trim();
                    String department = row[2].trim();
                    double salary = Double.parseDouble(row[3].trim());

                    if (id <= 0 || name.isBlank() || department.isBlank() ||
                            !Double.isFinite(salary) || salary < 0) {
                        throw new IllegalArgumentException(
                                "Invalid employee data");
                    }
                    Employee employee = new Employee(
                            id, name, department, salary);
                    employees.add(employee);
                    totalSalary += salary;

                } catch (IllegalArgumentException e) {
                    invalidRecords++;
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        int validRecords= employees.size();
        double averageSalary = validRecords==0 ? 0: totalSalary/validRecords;
        return new ProcessingResult(
                filePath.getFileName().toString(),
                totalRecords,
                validRecords,
                invalidRecords,
                totalSalary,
                averageSalary);
    }
}
