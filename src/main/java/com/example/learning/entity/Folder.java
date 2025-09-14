package com.example.learning.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Folder implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String folderName;
	
	@ManyToOne()
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@JsonIgnore
	private User user;
	
	@OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<File> files = new ArrayList<>();
	

}
