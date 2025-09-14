package com.example.learning.infra;


import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class CacheAdapterImp<K, V> implements CacheAdapter<K,V>{
	
	private final IMap<K, V> cache;
	
	public CacheAdapterImp(HazelcastInstance hazelcastInstance, String type) {
		this.cache = hazelcastInstance.getMap(type);
	}

	@Override
	public void put(K key, V value) {
		cache.put(key, value);
	}

	@Override
	public void remove(K key) {
		cache.remove(key);
		
	}

	@Override
	public V get(K key) {
		return cache.get(key);
	}


	
}
