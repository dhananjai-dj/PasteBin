package com.example.learning.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.learning.entity.Folder;

public interface FolderRepository extends JpaRepository<Folder, Long>{

}
