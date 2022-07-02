package com.example.minio.spring.domain.service.impl

import com.example.minio.spring.domain.model.FileDownloadInfo
import com.example.minio.spring.domain.model.FileDownloadQuery
import com.example.minio.spring.domain.model.FileInfo
import com.example.minio.spring.domain.model.FileSearchQuery
import com.example.minio.spring.domain.model.FileUploadCommand
import com.example.minio.spring.domain.service.FileService
import com.example.minio.spring.domain.service.FileSystemProvider
import org.springframework.stereotype.Service

@Service
class FileServiceImpl(
    private val fileSystemProvider: FileSystemProvider
) : FileService {
    override fun findFiles(query: FileSearchQuery): List<FileInfo> {
        return fileSystemProvider.findFiles(query)
    }

    override fun downloadFile(query: FileDownloadQuery): FileDownloadInfo {
        return fileSystemProvider.downloadFile(query)
    }

    override fun createFile(command: FileUploadCommand) {
        fileSystemProvider.createFile(command)
    }

    override fun deleteFile(bucket: String, path: String) {
        TODO("Not yet implemented")
    }
}