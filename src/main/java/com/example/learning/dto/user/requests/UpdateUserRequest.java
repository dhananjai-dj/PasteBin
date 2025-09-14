package com.example.learning.dto.user.requests;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserRequest {
	@NotNull(message = "User id is manditory")
	@Min(value = 1, message = "User id should be greater than 1")
	private Long userId;
	private String userName;
	private String email;
	private List<Long> folderIds;
	private List<Long> fileIds;

}
