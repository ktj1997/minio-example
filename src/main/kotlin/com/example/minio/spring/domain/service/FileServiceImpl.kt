package com.example.minio.spring.domain.service

import org.springframework.stereotype.Service

@Service
class FileServiceImpl(
    private val fileSystemProvider: FileSystemProvider
) : FileService {
    override fun findFiles() {
        TODO("Not yet implemented")
    }

    override fun createFile() {
        TODO("Not yet implemented")
    }

    override fun updateFile() {
        TODO("Not yet implemented")
    }

    override fun deleteFile() {
        TODO("Not yet implemented")
    }
}