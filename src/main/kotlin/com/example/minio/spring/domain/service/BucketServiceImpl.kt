package com.example.minio.spring.domain.service

import org.springframework.stereotype.Service

@Service
class BucketServiceImpl(
    private val fileSystemProvider: FileSystemProvider
) : BucketService {
    override fun findBuckets() {
        TODO("Not yet implemented")
    }

    override fun createBucket() {
        TODO("Not yet implemented")
    }

    override fun deleteBucket() {
        TODO("Not yet implemented")
    }
}