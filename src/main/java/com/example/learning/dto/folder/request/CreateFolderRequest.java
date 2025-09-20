package com.example.learning.dto.folder.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateFolderRequest {
	@NotBlank(message = "Folder name cannot be empty!")
	private String folderName;
}
