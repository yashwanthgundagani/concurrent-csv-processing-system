package com.example.concurrent_file_processor;

import com.example.concurrent_file_processor.model.ProcessingResult;
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
			List<Path> files=discoveryService.discoverCsvFiles();

			System.out.println(
					"Discovered "+files.size()+" CSV files"
			);

			List<Future<ProcessingResult>> futures=
					fileProcessingService.processFiles(files);

			for(Future<ProcessingResult> future : futures){
				ProcessingResult result=future.get();

				System.out.println("\nProcessing Summary");
				System.out.println("File: "+result.getFileName());
				System.out.println("Total Records: "+result.getTotalRecords());
				System.out.println("Valid Records: "+result.getValidRecords());
				System.out.println("Invalid Records: "+result.getInvalidRecords());
				System.out.println("Total Salary: "+ result.getTotalSalary());
				System.out.println("Average Salary: "+ result.getAverageSalary());
			}
		};
	}
}
