package com.example.learning.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.learning.dao.RepositoryFacade;
import com.example.learning.dto.GenericResponse;
import com.example.learning.dto.folder.request.CreateFolderRequest;
import com.example.learning.dto.folder.request.UpdateFolderRequest;
import com.example.learning.dto.folder.response.FolderDTO;
import com.example.learning.entity.File;
import com.example.learning.entity.Folder;
import com.example.learning.entity.User;
import com.example.learning.exception.FolderException;
import com.example.learning.utility.StringUtils;

@Service
public class FolderService {

	private final Logger logger = LoggerFactory.getLogger(FolderService.class);

	private final RepositoryFacade repositoryFacade;

	public FolderService(RepositoryFacade repositoryFacade) {
		this.repositoryFacade = repositoryFacade;
	}

	public ResponseEntity<?> createFolder(Long userId, CreateFolderRequest createFolderRequest) {
		try {
			Folder folder = new Folder();
			folder.setFolderName(createFolderRequest.getFolderName());
			User user = repositoryFacade.getUser(userId);
			if (user != null) {
				folder.setUser(user);
				user.getFolder().add(folder);
				repositoryFacade.saveUser(user);
			}
			return ResponseEntity.ok(new GenericResponse(true, "Folder has been generated!!!"));
		} catch (Exception e) {
			logger.error("Error in creating folder for request {}", createFolderRequest.toString());
			throw new FolderException("Error in creating folder");
		}

	}

	public ResponseEntity<?> updateFolder(Long userId, UpdateFolderRequest updateFolderRequest) {
		try {
			Long folderId = updateFolderRequest.getFolderId();
			Folder folder = repositoryFacade.getFolder(userId, folderId);
			if (folder == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to find the folder!!!");
			}
			String folderName = updateFolderRequest.getFolderName();
			if (StringUtils.isValidString(folderName)) {
				folder.setFolderName(folderName);
			}
			List<Long> fileIds = updateFolderRequest.getFileIds();
			if (fileIds != null && fileIds.size() > 0) {
				for (Long fileId : fileIds) {
					File file = repositoryFacade.getFile(fileId);
					if (file != null) {
						folder.getFiles().add(file);
						file.setFolder(folder);
					}
				}
			}
			repositoryFacade.saveFolder(folder);
			return ResponseEntity.ok(new GenericResponse(true, "Folder has been updated!!!"));
		} catch (Exception e) {
			logger.error("Error in updating folder for request {}", updateFolderRequest.toString());
			throw new FolderException("Error in updating folder");
		}
	}

	public ResponseEntity<?> deleteFolder(Long userId, Long folderId) {
		try {
			Folder folder = repositoryFacade.getFolder(userId, folderId);
			if (folder == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to find the folder!!!");
			}
			repositoryFacade.deleteFolder(folder);
			return ResponseEntity.ok(new GenericResponse(true, "Folder has been deleted!!!"));
		} catch (Exception e) {
			logger.error("Error in deleteing the folder for request {}", folderId);
			throw new FolderException("Error in deleting folder");
		}
	}

	public ResponseEntity<?> getFolderById(Long userId, Long folderId) {
		try {
			FolderDTO folderDTO = repositoryFacade.getFolderDTO(userId, folderId);
			if (folderDTO == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to find the folder!!!");
			}
			return ResponseEntity.ok(folderDTO);
		} catch (Exception e) {
			logger.error("Error in getting the folder for request {}", folderId);
			throw new FolderException("Error in getting the folder");
		}
	}

}
