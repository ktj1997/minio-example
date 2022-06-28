package com.example.minio.spring.domain.service.impl

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

    override fun createFile(command: FileUploadCommand) {
        fileSystemProvider.createFile(command)
    }

    override fun updateFile(bucket: String, path: String) {
        TODO("Not yet implemented")
    }

    override fun deleteFile(bucket: String, path: String) {
        TODO("Not yet implemented")
    }
}