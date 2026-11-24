package com.example.concurrent_file_processor.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class CsvFileTask implements Runnable{
    private static final Logger log= LoggerFactory.getLogger(CsvFileTask.class);
    private final Path filePath;

    public CsvFileTask(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        String threadName=Thread.currentThread().getName();
        log.info("{} started processing {}", threadName,filePath.getFileName());

        try{
            //simulate file processing
            Thread.sleep(5000);

            log.info("{} completed processing {}", threadName,
                    filePath.getFileName());

        } catch (InterruptedException e) {
            log.warn("{} was interrupted while processing {}",
                    threadName, filePath.getFileName());

            //restore interrupted status
            Thread.currentThread().interrupt();
        }
    }
}
