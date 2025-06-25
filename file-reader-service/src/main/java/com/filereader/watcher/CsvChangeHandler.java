package com.filereader.watcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CsvChangeHandler implements FileChangeListener {

	Logger log = LoggerFactory.getLogger(CsvChangeHandler.class);

	private RestTemplate restTemplate;

	private KafkaTemplate<String, String> kafkaTemplate;

	private String neonTopic;

	public CsvChangeHandler(RestTemplate restTemplate, String neonTopic, KafkaTemplate<String, String> kafkaTemplate) {
		this.restTemplate = restTemplate;
		this.kafkaTemplate = kafkaTemplate;
		this.neonTopic = neonTopic;
	}

	public CsvChangeHandler(String neonTopic) {

	}
	
	public CsvChangeHandler() {

	}

	@Override
	public void onChange(Set<ChangedFiles> changeSet) {
		for (ChangedFiles files : changeSet) {
			for (ChangedFile file : files) {
				File changedFile = file.getFile();

				log.info("Changed Type : {}", file.getType());
				log.info("Changed File: {}", changedFile.getAbsolutePath());

				if (changedFile.getName().endsWith(".csv")) {
					readCsv(changedFile);
				}
			}
		}
	}

	private void readCsv(File file) {
		log.info("Reading CSV: {}", file.getName());

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				log.info("Row:{}", String.join("|", data));
				// restTemplate.postForObject("http://192.168.151.122:8275/rest/v1/print",
				// String.join("|", data), Void.class);
				log.info("neonTopic:{}", neonTopic);
				this.kafkaTemplate.send(neonTopic, String.join("|", data));

			}
		} catch (Exception e) {
			log.error("Error reading file: {}", file.getAbsolutePath(), e);
		}
	}
}
