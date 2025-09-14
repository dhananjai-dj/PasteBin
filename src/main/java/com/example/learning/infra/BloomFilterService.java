package com.example.learning.infra;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.learning.dao.UserRepository;
import com.example.learning.utility.StringUtils;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import jakarta.annotation.PostConstruct;

@Service
public class BloomFilterService {

	private final Logger logger = LoggerFactory.getLogger(BloomFilterService.class);

	private BloomFilter<CharSequence> bloomFilter;
	private final UserRepository userRepository;

	public BloomFilterService(UserRepository userRepository) {
		this.userRepository = userRepository;
		bloomFilter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), 100000, 0.01);
	}

	public void addUserNameToList(String userName) {
		if (StringUtils.isValidString(userName)) {
			bloomFilter.put(userName.toLowerCase());
		}
	}
	

	public boolean isContains(String userName) {
		return bloomFilter.mightContain(userName.toLowerCase());
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		File file = new File("usernames.bloom");
		if (file.exists()) {
			try (FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis)) {
				this.bloomFilter = (BloomFilter<CharSequence>) ois.readObject();
				logger.info("Data has been loaded from bloom file successfully");
			} catch (Exception e) {
				logger.error("Error in loading data from bloom file!!! {}", e.toString());
				extractFromDb();
			}
		} else {
			extractFromDb();
		}
	}

	private void extractFromDb() {
		try {
			List<String> usernames = userRepository.getAllUserNames();
			usernames.stream().filter(u -> u != null).forEach(u -> bloomFilter.put(u.toLowerCase()));
			saveToBloomFile();
		} catch (Exception e) {
			logger.error("Error in fetching userName from DB {}", e.toString());
		}
	}

	private void saveToBloomFile() {
		try (FileOutputStream fos = new FileOutputStream("usernames.bloom");
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(bloomFilter);
			logger.info("Data has been saved to bloom file successfully");
		} catch (Exception e) {
			logger.error("Error in saving data to bloom file!!!");
		}
	}

}
