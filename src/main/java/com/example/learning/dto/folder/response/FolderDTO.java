package com.example.learning.dto.folder.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.learning.entity.File;
import com.example.learning.entity.Folder;
import com.example.learning.entity.User;

import lombok.Data;

@Data
public class FolderDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long fodlerId;
	private Long userId;
	private String folderName;
	private String userName;
	private List<File> files = new ArrayList<>();

	public static FolderDTO mapper(Folder folder) {
		FolderDTO FolderDTO = new FolderDTO();
		if (folder != null) {
			FolderDTO.setFodlerId(folder.getId());
			FolderDTO.setFolderName(folder.getFolderName());
			FolderDTO.getFiles().addAll(folder.getFiles());
			User user = folder.getUser();
			if (user != null) {
				FolderDTO.setUserId(user.getId());
				FolderDTO.setUserName(user.getUserName());
			}
		}
		return FolderDTO;
	}
}
