package com.example.minio.spring.domain.service

import com.example.minio.spring.domain.model.Bucket

interface BucketService {
    fun findBuckets(): List<Bucket>
    fun createBucket(name: String, objectLock: Boolean)
    fun deleteBucket(bucket: String)
}