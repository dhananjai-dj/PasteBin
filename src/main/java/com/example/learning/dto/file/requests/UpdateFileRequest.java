package com.example.learning.dto.file.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateFileRequest {
	@NotNull(message = "File id is manditory")
	@Min(value = 1, message = "Id must be greater than 0")
	private Long fileId;
	@NotBlank(message = "Blank data cannot be replace. You can delete the file instead")
	private String data;
}
