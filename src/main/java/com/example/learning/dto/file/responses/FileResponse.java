package com.example.learning.dto.file.responses;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class FileResponse {
private Long id;
private String data;
private String fileName;
private Long userId;
private String UserName;
private Long folderId;
private String fodlerName;
private Timestamp expirationTime;
private Timestamp createdDate;
private Timestamp updatedDate;
}
