package com.example.learning.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.learning.dto.folder.response.FolderDTO;
import com.example.learning.dto.user.response.UserDTO;
import com.example.learning.entity.File;
import com.example.learning.entity.Folder;
import com.example.learning.entity.User;
import com.example.learning.infra.CacheService;

@Service
public class RepositoryFacade {

	private final UserRepository userRepository;
	private final FolderRepository folderRepository;
	private final FileRepsoitory fileRepsoitory;

	private final CacheService cacheService;

	public RepositoryFacade(UserRepository userRepository, FolderRepository folderRepository,
			FileRepsoitory fileRepsoitory, CacheService cacheService) {
		this.userRepository = userRepository;
		this.folderRepository = folderRepository;
		this.fileRepsoitory = fileRepsoitory;
		this.cacheService = cacheService;
	}

	public File getFile(Long fileId) {
		File file = cacheService.getFile(fileId);
		if (file != null) {
			return file;
		}
		Optional<File> optioalFile = fileRepsoitory.findById(fileId);
		return optioalFile.orElse(null);
	}

	public void saveFile(File file) {
		fileRepsoitory.save(file);
		cacheService.reloadFileCache(file);
	}

	public File getFileByEndPoint(UUID endpoint) {
		File file = cacheService.getFile(endpoint);
		if (file == null) {
			file = fileRepsoitory.getByEndpoint(endpoint);
		}
		cacheService.reloadFileCache(file);
		return file;
	}

	public void deleteFile(File file) {
		fileRepsoitory.delete(file);
		cacheService.removeFile(file.getId());
	}

	public FolderDTO getFolderDTO(Long userId, Long folderId) {
		FolderDTO folderDTO = cacheService.getFolder(folderId);
		if (folderDTO == null) {
			folderDTO = FolderDTO.mapper(getFolder(userId,folderId));
		}
		return folderDTO;
	}

	public Folder getFolder(Long userId, Long folderId) {
		Optional<Folder> optionalFolder = folderRepository.findByUser_IdAndId(userId, folderId);
		Folder folder = optionalFolder.orElse(null);
		cacheService.reloadFolderCache(folder);
		return folder;
	}

	public void saveFolder(Folder folder) {
		folderRepository.save(folder);
		cacheService.reloadFolderCache(folder);
	}

	public void deleteFolder(Folder folder) {
		folderRepository.delete(folder);
		cacheService.removeFolder(folder.getId());
	}

	public void saveUser(User user) {
		userRepository.save(user);
		cacheService.reloadUserCache(UserDTO.mapper(user));
	}

	public User getUser(String userName) {
		return userRepository.getByUserName(userName);
	}

	public User getUser(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}

	public User getUserByEmail(String email) {
		return userRepository.getByEmail(email);
	}

	public UserDTO getUserDTO(Long userId) {
		UserDTO userDTO = cacheService.getUserDTO(userId);
		if (userDTO == null) {
			userDTO = UserDTO.mapper(getUser(userId));
			cacheService.reloadUserCache(userDTO);
		}
		return userDTO;
	}

}
