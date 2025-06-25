package com.fileprinter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
public class PrintController {

	Logger log = LoggerFactory.getLogger(PrintController.class);

	@PostMapping("/v1/print")
	public void printRow(@RequestBody String row) {
		log.info("Received CSV Row: {}", row);
	}
}
