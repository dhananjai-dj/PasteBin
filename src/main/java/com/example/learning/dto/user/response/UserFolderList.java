package com.example.learning.dto.user.response;


import java.util.Map;

import lombok.Data;

@Data
public class UserFolderList {
	private Long userId;
	private String userName;
	private Map<Long,String> folderMapping;
}
