package com.filereader.watcher;

import java.io.File;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

import ch.qos.logback.classic.Logger;

@Configuration
public class DirectoryWatcherConfig {
	File directory = new File("/home/arundathi/Desktop/files");
	Logger log = (Logger) LoggerFactory.getLogger(DirectoryWatcherConfig.class);
	
	@Value("${spring.kafka.producer-topic}")
	private String neonTopic;
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

    @Bean
    RestTemplate restTemplate() {
		return new RestTemplate();
		
	}
    @Bean
    FileSystemWatcher getWatcher(RestTemplate restTemplate) {
		FileSystemWatcher watcher = new FileSystemWatcher();
		watcher.addSourceDirectory(directory);
		watcher.addListener(new CsvChangeHandler(restTemplate,neonTopic,kafkaTemplate));
		watcher.start();
		
		log.info("Watcher started at {}", directory.getAbsolutePath());
		return watcher;
		   
	}
		
	}
	

