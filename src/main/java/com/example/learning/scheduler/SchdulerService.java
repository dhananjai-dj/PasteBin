package com.example.learning.scheduler;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.learning.dao.FileRepsoitory;


@Service
public class SchdulerService {
	private final Logger logger = LoggerFactory.getLogger(SchdulerService.class);

	private final FileRepsoitory fileRepsoitory;

	public SchdulerService(FileRepsoitory fileRepsoitory) {
		this.fileRepsoitory = fileRepsoitory;
	}

	public void deleteExpiredFiles() {
		LocalDateTime now = LocalDateTime.now();
		fileRepsoitory.deleteByExpirationTimeBefore(now);
		logger.info("Files has been deleted at {}", now);
	}
}
