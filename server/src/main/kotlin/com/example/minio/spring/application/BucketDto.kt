package com.example.minio.spring.application

import com.example.minio.spring.domain.model.Bucket
import java.time.LocalDateTime

data class CreateBucketRequestDto(
    val name: String,
    val objectLock: Boolean
)

data class BucketInfoResponseDto(val name: String) {
    constructor(bucket: Bucket) : this(bucket.name)
}