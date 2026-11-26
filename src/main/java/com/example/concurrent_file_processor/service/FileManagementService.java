package com.example.concurrent_file_processor.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileManagementService {
    private final Path archiveDirectory;
    private final Path errorDirectory;

    public FileManagementService(@Value("${file.processing.archive-dir}") String archiveDirectory,
                                 @Value("${file.processing.error-dir}")  String errorDirectory) {
        this.archiveDirectory = Path.of(archiveDirectory);
        this.errorDirectory = Path.of(errorDirectory);
    }

    public Path moveToArchive(Path source) throws IOException{
        return moveFile(source,archiveDirectory);
    }

    public Path moveToError(Path source) throws IOException {
        return moveFile(source,errorDirectory);
    }

    private Path moveFile(Path source, Path destinationDirectory) throws IOException {
        Files.createDirectories(destinationDirectory);
        Path destination= destinationDirectory.resolve(source.getFileName());
        return Files.move(source,destination);
    }
}
