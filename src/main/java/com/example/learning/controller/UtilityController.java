package com.example.learning.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.learning.infra.BloomFilterService;
import com.example.learning.utility.StringUtils;

@RestController
@RequestMapping("/util")
public class UtilityController {

	private final BloomFilterService bloomFilterService;

	public UtilityController(BloomFilterService bloomFilterService) {
		this.bloomFilterService = bloomFilterService;
	}

	@GetMapping("/username-availablility")
	public Boolean isUserNameAvailabe(@RequestParam String userName) {
		return StringUtils.isValidString(userName) && !bloomFilterService.isContains(userName);
	}

}
