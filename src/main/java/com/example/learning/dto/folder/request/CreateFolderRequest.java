package com.example.learning.dto.folder.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateFolderRequest {
	@NotBlank(message = "Folder name cannot be empty!")
	private String folderName;
	@NotNull(message = "User id is manditory")
	@Min(value = 1, message = "User id should be greater than 1")
	private Long userId;
}
