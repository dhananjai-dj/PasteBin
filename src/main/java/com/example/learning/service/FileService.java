package com.example.learning.service;

import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.learning.dao.RepositoryFacade;
import com.example.learning.dto.GenericResponse;
import com.example.learning.dto.file.requests.SecureFileRequest;
import com.example.learning.dto.file.requests.UpdateFileRequest;
import com.example.learning.dto.file.requests.UploadFileRequest;
import com.example.learning.dto.file.responses.FileResponse;
import com.example.learning.dto.file.responses.PasswordRequiredResponse;
import com.example.learning.entity.File;
import com.example.learning.entity.Folder;
import com.example.learning.entity.User;
import com.example.learning.exception.FileException;
import com.example.learning.infra.CacheFactory;
import com.example.learning.utility.SecurityUtil;
import com.example.learning.utility.StringUtils;

@Service
public class FileService {

	private final Logger logger = LoggerFactory.getLogger(FileService.class);

	private final RepositoryFacade repositoryFacade;

	public FileService(RepositoryFacade repositoryFacade, CacheFactory cacheFactory) {
		this.repositoryFacade = repositoryFacade;

	}

	public ResponseEntity<?> addFile(UploadFileRequest uploadFileRequest) {
		try {
			File file = new File();
			file.setData(uploadFileRequest.getData());
			file.setFileName(uploadFileRequest.getFileName());
			file.setIsOnceView(uploadFileRequest.getIsOnceView());
			file.setIsLocked(uploadFileRequest.getIsLocked());

			file.setEndpoint(UUID.randomUUID());
			if (file.getIsLocked()) {
				String password = uploadFileRequest.getPassword();
				file.setPasswordHash(SecurityUtil.generatePasswordHash(password == null ? "" : password));
			}

			int burnTime = uploadFileRequest.getBurnTime();
			if (burnTime > 0) {
				Timestamp expiryTime = addMinutesToTime(burnTime);
				file.setExpirationTime(expiryTime);
			}

			repositoryFacade.saveFile(file);

			String message = "File has been saved successfully. Please use this link to access the file "
					+ StringUtils.LINK + file.getEndpoint();
			URI uri = URI.create(StringUtils.LINK + file.getEndpoint());

			return ResponseEntity.created(uri).body(new GenericResponse(true, message));
		} catch (Exception e) {
			logger.error("Error in add File {} for request", e.getMessage(), uploadFileRequest.toString());
			throw new FileException("Unable to create the file!!! Please try again later.");
		}
	}

	public ResponseEntity<?> updateFile(UpdateFileRequest updateFileRequest) {
		try {
			Long fileId = updateFileRequest.getFileId();
			File file = repositoryFacade.getFile(fileId);
			if (file == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to locate the file!!!");
			}
			if (StringUtils.isValidString(updateFileRequest.getData())) {
				file.setData(updateFileRequest.getData());
				repositoryFacade.saveFile(file);
			}
			String message = "File has been updated successfully. Please use this link to access the file"
					+ StringUtils.LINK + file.getEndpoint();
			return ResponseEntity.ok(new GenericResponse(true, message));
		} catch (Exception e) {
			logger.error("Error in update File {} for request {}", e.getMessage(), updateFileRequest.toString());
			throw new FileException("Unable to update the file!!!");
		}
	}

	public ResponseEntity<?> getFile(UUID endpoint) {
		try {
			File file = repositoryFacade.getFileByEndPoint(endpoint);
			if (file.getIsLocked()) {
				PasswordRequiredResponse passwordRequiredResponse = new PasswordRequiredResponse();
				passwordRequiredResponse.setFileId(file.getId());
				passwordRequiredResponse.setMessage("Please Enter the password to procced");
				return ResponseEntity.ok(passwordRequiredResponse);
			}
			FileResponse fileResponse = fileResposneMapper(file);
			if (file.getIsOnceView()) {
				repositoryFacade.deleteFile(file);
			}
			return ResponseEntity.ok(fileResponse);
		} catch (Exception e) {
			logger.error("Error in get File {} for request {}", e.getMessage(), endpoint);
			throw new FileException("Unable to get the file!!!");
		}
	}

	public ResponseEntity<?> getSecuredFile(SecureFileRequest secureFileRequest) {
		try {
			Long fileId = secureFileRequest.getId();
			String password = secureFileRequest.getPassword();
			File file = repositoryFacade.getFile(fileId);
			if (file == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to locate the file!!!");
			}
			if (!SecurityUtil.isSamePassword(password, file.getPasswordHash())) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body((new GenericResponse(false, "Wrong Password entred!!!")));
			}
			FileResponse fileResponse = fileResposneMapper(file);
			if (file.getIsOnceView()) {
				repositoryFacade.deleteFile(file);
			}
			return ResponseEntity.ok(fileResponse);
		} catch (Exception e) {
			logger.error("Error in get secured file {} for request {} ", e.getMessage(), secureFileRequest.toString());
			throw new FileException("Unable to get the secured file!!!");
		}
	}

	public ResponseEntity<?> deleteFile(Long fileId) {
		try {
			File file = repositoryFacade.getFile(fileId);
			if (file == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to delete the file!!!");
			}
			repositoryFacade.deleteFile(file);
			return ResponseEntity.ok(new GenericResponse(true, "File has been deleted success fully"));
		} catch (Exception e) {
			logger.error("Error in delete the file {} for request {} ", e.getMessage(), fileId);
			throw new FileException("Unable to delete the file!!!");
		}
	}

	private FileResponse fileResposneMapper(File file) {
		FileResponse fileResponse = new FileResponse();
		fileResponse.setId(file.getId());
		fileResponse.setData(file.getData());
		fileResponse.setFileName(file.getFileName());
		fileResponse.setExpirationTime(file.getExpirationTime());
		fileResponse.setCreatedDate(file.getCreatedDate());
		fileResponse.setUpdatedDate(file.getUpdatedDate());
		User user = file.getUser();
		if (user != null) {
			fileResponse.setUserId(user.getId());
			fileResponse.setUserName(user.getUserName());
		}
		Folder folder = file.getFolder();
		if (folder != null) {
			fileResponse.setFodlerName(folder.getFolderName());
			fileResponse.setFolderId(folder.getId());
		}
		return fileResponse;
	}
	
	private  Timestamp addMinutesToTime(int minutes) {
		LocalDateTime futureTime = LocalDateTime.now().plusMinutes(minutes);
		return Timestamp.valueOf(futureTime);
	}


}
