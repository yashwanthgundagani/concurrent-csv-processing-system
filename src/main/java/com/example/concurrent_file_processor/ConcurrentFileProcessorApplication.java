package com.example.concurrent_file_processor;

import com.example.concurrent_file_processor.service.FileDiscoveryService;
import com.example.concurrent_file_processor.service.FileProcessingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Future;

@SpringBootApplication
public class ConcurrentFileProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConcurrentFileProcessorApplication.class, args);
	}
	@Bean
	CommandLineRunner testConcurrentProcessing(FileDiscoveryService discoveryService,
											   FileProcessingService fileProcessingService){
		return args->{
			//Step 1: Discover of CSV files
			List<Path> files=discoveryService.discoverCsvFiles();

			System.out.println(
					"Discovered "+files.size()+" CSV files"
			);

			//files.forEach(System.out::println);
			//Step 2: Submit files for concurrent processing
			List<Future<?>> futures=
					fileProcessingService.processFiles(files);
			System.out.println(
					"Submitted "+futures.size()+" tasks"
			);
		};
	}
}
