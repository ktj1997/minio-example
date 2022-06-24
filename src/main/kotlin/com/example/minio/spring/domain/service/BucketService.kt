package com.example.minio.spring.domain.service

interface BucketService {
    fun findBuckets()
    fun createBucket()
    fun deleteBucket()
}