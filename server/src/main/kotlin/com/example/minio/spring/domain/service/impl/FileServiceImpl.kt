package com.example.minio.spring.domain.service.impl

import com.example.minio.spring.domain.model.FileInfo
import com.example.minio.spring.domain.service.FileService
import com.example.minio.spring.domain.service.FileSystemProvider
import org.springframework.stereotype.Service
import java.io.File

@Service
class FileServiceImpl(
    private val fileSystemProvider: FileSystemProvider
) : FileService {
    override fun findFiles(bucket: String, path: String): List<FileInfo> {
        return fileSystemProvider.findFiles(bucket, path)
    }

    override fun createFile(bucket: String, path: String, file: File) {
        TODO("Not yet implemented")
    }

    override fun updateFile(bucket: String, path: String) {
        TODO("Not yet implemented")
    }

    override fun deleteFile(bucket: String, path: String) {
        TODO("Not yet implemented")
    }
}