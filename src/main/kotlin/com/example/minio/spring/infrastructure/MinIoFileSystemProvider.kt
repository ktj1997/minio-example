package com.example.minio.spring.infrastructure

import com.example.minio.spring.domain.service.FileSystemProvider
import io.minio.MinioClient
import org.springframework.stereotype.Component

@Component
class MinIoFileSystemProvider(
    private val minioClient: MinioClient
) : FileSystemProvider {
    override fun createBucket() {
        TODO("Not yet implemented")
    }

    override fun findBuckets() {
        TODO("Not yet implemented")
    }

    override fun deleteBucket() {
        TODO("Not yet implemented")
    }

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