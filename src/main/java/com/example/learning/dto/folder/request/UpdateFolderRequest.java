package com.example.learning.dto.folder.request;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateFolderRequest {
	@NotNull(message = "Folder id is manditory")
	@Min(value = 1, message = "Folder id should be greater than 1")
	private Long folderId;
	private String folderName;
	private List<Long> fileIds;
}
