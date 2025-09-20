package com.example.learning.dto.user.requests;

import java.util.List;

import lombok.Data;

@Data
public class UpdateUserRequest {
	private String userName;
	private String email;
	private List<Long> folderIds;
	private List<Long> fileIds;

}
