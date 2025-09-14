package com.example.learning.dto.file.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UploadFileRequest {
	@NotBlank(message = "Message data cannot be empty")
	private String data;
	private String fileName;
	private String password;
	private Boolean isLocked;
	private Boolean isOnceView;
	private Long userId;
	private Long folderId;
	private Integer burnTime;
}
