package com.example.learning.infra;


public interface CacheAdapter<K, V> {

	void put(K key, V value);

	void remove(K key);

	V get(K key);

}
