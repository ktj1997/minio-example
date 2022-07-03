package com.example.minio.spring.domain.service

import com.example.minio.spring.domain.model.FileDownloadInfo
import com.example.minio.spring.domain.model.FileDownloadQuery
import com.example.minio.spring.domain.model.FileInfo
import com.example.minio.spring.domain.model.FileSearchQuery
import com.example.minio.spring.domain.model.FileUploadCommand

interface FileService {
    fun findFiles(query: FileSearchQuery): List<FileInfo>
    fun downloadFile(query: FileDownloadQuery): FileDownloadInfo
    fun createFile(command: FileUploadCommand): FileInfo
    fun deleteFile(bucket: String, path: String)
}
