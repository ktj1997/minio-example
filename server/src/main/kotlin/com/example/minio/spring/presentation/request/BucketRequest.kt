package com.example.minio.spring.presentation.request

import com.example.minio.spring.application.CreateBucketRequestDto

class CreateBucketRequest(
    val name: String,
    val objectLock: Boolean
) {
    fun toDto(): CreateBucketRequestDto {
        return CreateBucketRequestDto(
            name = name.lowercase(),
            objectLock = objectLock
        )
    }
}
