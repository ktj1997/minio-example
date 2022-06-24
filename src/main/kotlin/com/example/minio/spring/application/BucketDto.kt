package com.example.minio.spring.application

import com.example.minio.spring.domain.model.Bucket
import java.time.LocalDateTime

class CreateBucketRequestDto(
    val name: String,
    val objectLock: Boolean
)

class BucketInfoResponseDto(val name: String, val createdAt: LocalDateTime) {
    constructor(bucket: Bucket) : this(bucket.name, bucket.createdAt)
}