package com.example.learning.infra;

import org.springframework.stereotype.Component;

import com.hazelcast.core.HazelcastInstance;

@Component
public class CacheFactory {
	private final HazelcastInstance hazelcastInstance;

	public CacheFactory(HazelcastInstance hazelcastInstance) {
		this.hazelcastInstance = hazelcastInstance;
	}

	public <K, V> CacheAdapter<K, V> getCache(String mapName) {
		return new CacheAdapterImp<>(hazelcastInstance, mapName);
	}
}
