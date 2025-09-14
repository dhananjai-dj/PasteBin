package com.example.learning.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PasteCleanupScheduler {
	
	private final SchdulerService schdulerService;

	public PasteCleanupScheduler(SchdulerService schdulerService) {
		this.schdulerService = schdulerService;
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void deleteExpiredPastes() {
		schdulerService.deleteExpiredFiles();
	}

}
