package com.example.learning.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.learning.dto.folder.request.CreateFolderRequest;
import com.example.learning.dto.folder.request.UpdateFolderRequest;
import com.example.learning.service.FolderService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/folder")
@Validated
public class FolderController {

	private final FolderService folderService;

	public FolderController(FolderService folderService) {
		this.folderService = folderService;
	}

	@PostMapping("/add")
	public ResponseEntity<?> createFolder(@Valid @RequestBody CreateFolderRequest createFolderRequest) {
		return folderService.createFolder(createFolderRequest);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateFolder(@Valid @RequestBody UpdateFolderRequest updateFolderRequest) {
		return folderService.updateFolder(updateFolderRequest);
	}

	@GetMapping("/get")
	public ResponseEntity<?> getFolder(@RequestParam  @Min(value = 1, message = "Folder Id must be greater than 1")  Long folderId) {
		return folderService.getFolderById(folderId);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteFolder(@RequestParam   @Min(value = 1, message = "Folder Id must be greater than 1") Long folderId) {
		return folderService.deleteFolder(folderId);
	}

}
