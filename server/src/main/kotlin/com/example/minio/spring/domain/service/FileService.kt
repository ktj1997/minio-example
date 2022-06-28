package com.example.minio.spring.domain.service

import com.example.minio.spring.domain.model.FileInfo
import com.example.minio.spring.domain.model.FileSearchQuery
import com.example.minio.spring.domain.model.FileUploadCommand

interface FileService {
    fun findFiles(query: FileSearchQuery): List<FileInfo>
    fun createFile(command: FileUploadCommand)
    fun updateFile(bucket: String, path: String)
    fun deleteFile(bucket: String, path: String)
}