package com.example.concurrent_file_processor;

import com.example.concurrent_file_processor.service.FileDiscoveryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.file.Path;
import java.util.List;

@SpringBootApplication
public class ConcurrentFileProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConcurrentFileProcessorApplication.class, args);
	}
	@Bean
	CommandLineRunner testFileDiscovery(FileDiscoveryService discoveryService){
		return args->{
			List<Path> files=discoveryService.discoverCsvFiles();

			System.out.println(
					"Discovered "+files.size()+" CSV files"
			);

			files.forEach(System.out::println);
		};
	}
}
