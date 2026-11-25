package com.example.concurrent_file_processor.service;

import com.example.concurrent_file_processor.model.ProcessingResult;
import com.example.concurrent_file_processor.processor.CsvFileTask;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class FileProcessingService {
    private final ThreadPoolExecutor executor;
    private final CsvProcessingService csvProcessingService;

    public FileProcessingService(ThreadPoolExecutor fileProcessingExecutor, CsvProcessingService csvProcessingService) {
        this.executor = fileProcessingExecutor;
        this.csvProcessingService = csvProcessingService;
    }

    public List<Future<ProcessingResult>> processFiles(List<Path> files){
        List<Future<ProcessingResult>> futures= new ArrayList<>();
        for(Path file: files){
            CsvFileTask task = new CsvFileTask(file,csvProcessingService);
            Future<ProcessingResult> future = executor.submit(task);
            futures.add(future);
        }
        return futures;
    }
}
