package com.example.learning.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;

@Configuration
public class HazecastConfig {

	@Bean
	Config hazelCastConfig() {
		Config config = new Config();
		config.setInstanceName("hazelcast-pastebin");
		config.addMapConfig(new MapConfig().setName("USER").setTimeToLiveSeconds(0).setMaxIdleSeconds(0));
		config.addMapConfig(new MapConfig().setName("FOLDER").setTimeToLiveSeconds(0).setMaxIdleSeconds(0));
		config.addMapConfig(new MapConfig().setName("FILE").setTimeToLiveSeconds(0).setMaxIdleSeconds(0));
		config.addMapConfig(new MapConfig().setName("FAST_FILE").setTimeToLiveSeconds(0).setMaxIdleSeconds(0));
		return config;
	}
	
}
