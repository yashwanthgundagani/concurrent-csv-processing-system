package com.example.concurrent_file_processor.controller;

import com.example.concurrent_file_processor.model.ProcessingResult;
import com.example.concurrent_file_processor.service.FileDiscoveryService;
import com.example.concurrent_file_processor.service.FileProcessingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@RestController
public class FileProcessingController {
    private final FileProcessingService fileProcessingService;
    private final FileDiscoveryService fileDiscoveryService;

    public FileProcessingController(FileProcessingService fileProcessingService, FileDiscoveryService fileDiscoveryService) {
        this.fileProcessingService = fileProcessingService;
        this.fileDiscoveryService = fileDiscoveryService;
    }

    @PostMapping("/process")
    public ResponseEntity<List<ProcessingResult>> processFiles() throws IOException {
        List<Path> files=fileDiscoveryService.discoverCsvFiles();
        List<ProcessingResult> results=fileProcessingService.processFiles(files);
        return ResponseEntity.ok(results);
    }
}
