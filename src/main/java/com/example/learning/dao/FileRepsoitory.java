package com.example.learning.dao;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.learning.entity.File;

@Repository
public interface FileRepsoitory extends JpaRepository<File, Long>{

	File getByEndpoint(UUID endpoint);

	void deleteByExpirationTimeBefore(LocalDateTime now);

}
