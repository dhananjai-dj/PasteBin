package com.example.learning.infra;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.learning.dto.folder.response.FolderDTO;
import com.example.learning.dto.user.response.UserDTO;
import com.example.learning.entity.File;
import com.example.learning.entity.Folder;
import com.example.learning.entity.User;

@Service
public class CacheService {

	private final CacheAdapter<Long, File> fileCache;
	private final CacheAdapter<Long, FolderDTO> folderCache;
	private final CacheAdapter<Long, UserDTO> userCache;
	private final CacheAdapter<UUID, File> fastFileCache;

	public CacheService(CacheFactory cacheFactory) {
		this.fileCache = cacheFactory.getCache("USER");
		this.folderCache = cacheFactory.getCache("FOLDER");
		this.userCache = cacheFactory.getCache("FILE");
		this.fastFileCache = cacheFactory.getCache("FAST_FILE");
	}

	public void reloadUserCache(UserDTO userDTO) {
		userCache.put(userDTO.getUserId(), userDTO);
		userDTO.getFiles().forEach(file -> {
			fileCache.put(file.getId(), file);
			fastFileCache.put(file.getEndpoint(), file);
		});
		userDTO.getFolder().forEach(folder -> folderCache.put(folder.getFodlerId(), folder));
	}

	public void reloadFolderCache(Folder folder) {
		UserDTO userDTO = UserDTO.mapper(folder.getUser());
		reloadUserCache(userDTO);
	}

	public void reloadFileCache(File file) {
		User user = file.getUser();
		Folder folder = file.getFolder();
		if (user != null) {
			UserDTO userDTO = UserDTO.mapper(user);
			reloadUserCache(userDTO);
		} else if (folder != null) {
			reloadFolderCache(folder);
		} else {
			fileCache.put(file.getId(), file);
			fastFileCache.put(file.getEndpoint(), file);
		}

	}

	public UserDTO getUserDTO(Long userId) {
		return userCache.get(userId);
	}

	public FolderDTO getFolder(Long folderId) {
		return folderCache.get(folderId);
	}

	public File getFile(Long fileId) {
		return fileCache.get(fileId);
	}

	public File getFile(UUID endpoint) {
		return fastFileCache.get(endpoint);
	}

	public void removeFile(Long fileId) {
		fileCache.remove(fileId);
	}

	public void removeFolder(Long folderId) {
		folderCache.remove(folderId);
	}
}
