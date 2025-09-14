package com.example.learning.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.learning.dao.RepositoryFacade;
import com.example.learning.dto.folder.response.FolderDTO;
import com.example.learning.dto.user.requests.CreateUserRequest;
import com.example.learning.dto.user.requests.UpdateUserRequest;
import com.example.learning.dto.user.response.UserFolderList;
import com.example.learning.dto.user.response.UserDTO;
import com.example.learning.entity.File;
import com.example.learning.entity.Folder;
import com.example.learning.entity.User;
import com.example.learning.exception.UserException;
import com.example.learning.infra.BloomFilterService;
import com.example.learning.utility.GenericUtil;
import com.example.learning.utility.StringUtils;

@Service
public class UserService {
	private final Logger logger = LoggerFactory.getLogger(UserService.class);

	private final RepositoryFacade repositoryFacade;
	private final BloomFilterService bloomFilterService;

	public UserService(RepositoryFacade repositoryFacade, BloomFilterService bloomFilterService) {
		this.repositoryFacade = repositoryFacade;
		this.bloomFilterService = bloomFilterService;
	}

	public ResponseEntity<?> createUser(CreateUserRequest createUserRequest) {
		try {
			User user = new User();
			user.setEmail(createUserRequest.getEmail());
			user.setUserName(createUserRequest.getUserName());
			if (bloomFilterService.isContains(user.getUserName())) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User Name already taken.");
			}
			bloomFilterService.addUserNameToList(user.getUserName());
			repositoryFacade.saveUser(user);
			return ResponseEntity
					.ok(GenericUtil.generateGenericResponse(true, "User has been created Successfully!!!"));
		} catch (Exception e) {
			logger.error("Error in adding the user {} for request {}", createUserRequest.toString());
			throw new UserException("Unable to create user!!! Please try later.");
		}
	}

	public ResponseEntity<?> updateUser(UpdateUserRequest updateUserRequest) {
		try {
			Long id = updateUserRequest.getUserId();
			User user = repositoryFacade.getUser(id);
			if (user == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to find the user!!!");
			}
			String userName = updateUserRequest.getUserName();
			if (StringUtils.isValidString(userName)) {
				if (bloomFilterService.isContains(userName)) {
					return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User Name already taken.");
				}
				user.setUserName(userName);
				bloomFilterService.addUserNameToList(userName);
			}
			String email = updateUserRequest.getEmail();
			if (StringUtils.isValidString(email)) {
				user.setEmail(email);
			}
			List<Long> folderIds = updateUserRequest.getFolderIds();
			if (folderIds != null && folderIds.size() > 0) {
				for (Long folderId : folderIds) {
					Folder folder = repositoryFacade.getFolder(folderId);
					if (folder == null) {
						logger.debug("Unable to locate the file with folder {}", folderId);
						continue;
					}
					folder.setUser(user);
					user.getFolder().add(folder);
				}
			}
			List<Long> fileIds = updateUserRequest.getFileIds();
			if (fileIds != null && fileIds.size() > 0) {
				for (Long fileId : fileIds) {
					File file = repositoryFacade.getFile(fileId);
					if (file == null) {
						logger.debug("Unable to locate the file with fileId {}", fileId);
						continue;
					}
					file.setUser(user);
					user.getFiles().add(file);
				}
			}
			repositoryFacade.saveUser(user);
			return ResponseEntity
					.ok(GenericUtil.generateGenericResponse(true, "User has been updated Successfully!!!"));

		} catch (Exception e) {
			logger.error("Error in updating the user {} for request {}", updateUserRequest.toString());
			throw new UserException("Unable to update user!!! Please try later.");
		}
	}

	public ResponseEntity<?> getUserData(Long userId) {
		try {
			UserDTO userDTO = repositoryFacade.getUserDTO(userId);
			if (userDTO == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to find the user!!!");
			}
			return ResponseEntity.ok(userDTO);
		} catch (Exception e) {
			logger.error("Error in getting folders of the user {} for request {}", userId);
			throw new UserException("Unable to get folders of the user!!! Please try later.");
		}
	}

	public ResponseEntity<?> getFolders(Long userId) {
		try {
			UserDTO userDTO = repositoryFacade.getUserDTO(userId);
			if (userDTO == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to find the user!!!");
			}
			UserFolderList userFolderList = new UserFolderList();
			userFolderList.setUserId(userDTO.getUserId());
			userFolderList.setUserName(userDTO.getUserName());
			Map<Long, String> map = new HashMap<>();
			for (FolderDTO folderDTO : userDTO.getFolder()) {
				map.put(folderDTO.getFodlerId(), folderDTO.getFolderName());
			}
			userFolderList.setFolderMapping(map);
			return ResponseEntity.ok(userFolderList);
		} catch (Exception e) {
			logger.error("Error in getting folder mapping of the user {} for request {}", userId);
			throw new UserException("Unable to get folders of the user!!! Please try later.");
		}
	}

}
