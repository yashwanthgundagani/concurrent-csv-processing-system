package com.example.concurrent_file_processor.processor;

import com.example.concurrent_file_processor.model.ProcessingResult;
import com.example.concurrent_file_processor.service.CsvProcessingService;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.Callable;

public class CsvFileTask implements Callable<ProcessingResult> {
    private static final Logger log= LoggerFactory.getLogger(CsvFileTask.class);
    private final Path filePath;
    private final CsvProcessingService csvProcessingService;

    public CsvFileTask(Path filePath, CsvProcessingService csvProcessingService) {
        this.filePath = filePath;
        this.csvProcessingService = csvProcessingService;
    }

    @Override
    public ProcessingResult call() throws IOException, CsvValidationException {
        String threadName=Thread.currentThread().getName();
        log.info("{} started processing {}", threadName,filePath.getFileName());

        ProcessingResult result=
                csvProcessingService.processCsv(filePath);

        log.info("{} completed processing {}", threadName,
                filePath.getFileName());

        return result;
    }
}
