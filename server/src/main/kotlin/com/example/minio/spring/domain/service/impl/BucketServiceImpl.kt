package com.example.minio.spring.domain.service.impl

import com.example.minio.spring.domain.model.Bucket
import com.example.minio.spring.domain.service.BucketService
import com.example.minio.spring.domain.service.FileSystemProvider
import org.springframework.stereotype.Service

@Service
class BucketServiceImpl(
    private val fileSystemProvider: FileSystemProvider
) : BucketService {

    override fun createBucket(name: String, objectLock: Boolean) {
        fileSystemProvider.createBucket(name, objectLock)
    }

    override fun findBuckets(): List<Bucket> {
        return fileSystemProvider.findBuckets()
    }

    override fun deleteBucket(bucket: String) {
        fileSystemProvider.deleteBucket(bucket)
    }
}
