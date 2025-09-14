package com.example.learning.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.learning.dto.file.requests.SecureFileRequest;
import com.example.learning.dto.file.requests.UpdateFileRequest;
import com.example.learning.dto.file.requests.UploadFileRequest;
import com.example.learning.service.FileService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/file")
@Validated
public class FileController {

	private final FileService fileService;

	public FileController(FileService fileService) {
		this.fileService = fileService;
	}

	@PostMapping("/add")
	public ResponseEntity<?> addFile(@Valid @RequestBody UploadFileRequest uploadFileRequest) {
		return fileService.addFile(uploadFileRequest);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateFolder(@Valid @RequestBody UpdateFileRequest updateFileRequest) {
		return fileService.updateFile(updateFileRequest);
	}

	@GetMapping("/get/{endpoint}")
	public ResponseEntity<?> getFileData(@NotNull(message = "please search with valid endpoint") @PathVariable UUID endpoint) {
		return fileService.getFile(endpoint);
	}

	@PostMapping("/get/{endpoint}")
	public ResponseEntity<?> getSecuredFileData(@Valid @RequestBody SecureFileRequest getSecureFileRequest) {
		return fileService.getSecuredFile(getSecureFileRequest);
	}

	@DeleteMapping("/remove")
	public ResponseEntity<?> deleteFile(@RequestParam  @Min(value = 1, message = "File Id must be greater than 1") Long fileId) {
		return fileService.deleteFile(fileId);
	}

}
