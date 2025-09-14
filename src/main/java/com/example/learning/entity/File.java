package com.example.learning.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(indexes = @Index(columnList = "endpoint"))
public class File implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String data;
	private String fileName;
	private String passwordHash;
	
	@Column(nullable = false)
	private Boolean isLocked = false;
	@Column(nullable = false)
	private Boolean isOnceView = false;
	
	@ManyToOne()
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@JsonIgnore
	private User user;
	
	
	@ManyToOne()
	@JoinColumn(name = "folder_id", referencedColumnName = "id")
	@JsonIgnore
	private Folder folder;
	
	@Column(nullable = false)
	private UUID endpoint;
	
	private Timestamp expirationTime;
	@CreationTimestamp
	private Timestamp createdDate;
	@UpdateTimestamp
	private Timestamp updatedDate;

}
