package com.example.concurrent_file_processor.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileDiscoveryService {
    private final Path inputDirectory;

    public FileDiscoveryService(@Value("${file.processing.input-dir}") String inputDir) {
        this.inputDirectory = Paths.get(inputDir);
    }

    public List<Path> discoverCsvFiles() throws IOException{
        if(!Files.exists(inputDirectory)){
            Files.createDirectories(inputDirectory);
        }

        //why do we need try-with?
        try(Stream<Path> files = Files.list(inputDirectory)){
            return files.filter(Files::isRegularFile)
                    .filter(path ->
                            path.getFileName()
                                    .toString()
                                    .toLowerCase(Locale.ROOT)
                                    .endsWith(".csv")
                    )
                    .sorted()
                    .collect(Collectors.toList());
        }
    }
}
