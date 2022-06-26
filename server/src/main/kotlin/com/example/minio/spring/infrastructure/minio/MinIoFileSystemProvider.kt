package com.example.minio.spring.infrastructure.minio

import com.example.minio.spring.core.config.exceptions.ApiException
import com.example.minio.spring.core.config.exceptions.ExceptionType
import com.example.minio.spring.domain.model.Bucket
import com.example.minio.spring.domain.service.FileSystemProvider
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import org.springframework.stereotype.Component
import kotlin.streams.toList

@Component
class MinIoFileSystemProvider(
    private val minioClient: MinioClient
) : FileSystemProvider {
    override fun createBucket(name: String, objectLock: Boolean) {
        try {
            val args = MakeBucketArgs.builder().bucket(name)
                .objectLock(objectLock).build()
            minioClient.makeBucket(args)
        } catch (e: Exception) {
            throw ApiException(ExceptionType.CREATE_BUCKET_FAILURE, e.message!!)
        }
    }

    override fun findBuckets(): List<Bucket> {
        try {
            val buckets = minioClient.listBuckets()
            return buckets.stream().map {
                Bucket(
                    name = it.name(),
                    createdAt = it.creationDate().toLocalDateTime()
                )
            }.toList()

        } catch (e: Exception) {
            throw ApiException(ExceptionType.CREATE_BUCKET_FAILURE, e.message!!)
        }
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