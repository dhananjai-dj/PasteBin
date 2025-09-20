package com.example.learning.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.learning.entity.Folder;

public interface FolderRepository extends JpaRepository<Folder, Long>{
	
	 Optional<Folder> findByUser_IdAndId(Long userId, Long folderId);

}
