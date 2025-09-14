package com.example.learning.dto.user.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.learning.dto.folder.response.FolderDTO;
import com.example.learning.entity.File;
import com.example.learning.entity.Folder;
import com.example.learning.entity.User;

import lombok.Data;

@Data
public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long userId;
	private String userName;
	private String email;
	private List<FolderDTO> folder = new ArrayList<>();
	private List<File> files = new ArrayList<>();

	public static UserDTO mapper(User user) {
		UserDTO userDTO = new UserDTO();
		if (user != null) {
			userDTO.setUserId(user.getId());
			userDTO.setEmail(user.getEmail());
			userDTO.setUserName(user.getUserName());
			userDTO.getFiles().addAll(user.getFiles());
			for (Folder folder : user.getFolder()) {
				userDTO.getFolder().add(FolderDTO.mapper(folder));
			}

		}
		return userDTO;
	}
}
