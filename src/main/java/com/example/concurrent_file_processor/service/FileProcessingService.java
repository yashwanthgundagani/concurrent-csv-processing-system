package com.example.concurrent_file_processor.service;

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

    public FileProcessingService(ThreadPoolExecutor fileProcessingExecutor) {
        this.executor = fileProcessingExecutor;
    }

    public List<Future<?>> processFiles(List<Path> files){
        List<Future<?>> futures= new ArrayList<>();
        for(Path file: files){
            CsvFileTask task = new CsvFileTask(file);
            Future<?> future = executor.submit(task);
            futures.add(future);
        }
        return futures;
    }
}
