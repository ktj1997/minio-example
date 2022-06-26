package com.example.minio.spring.application

import com.example.minio.spring.domain.service.BucketService
import org.springframework.stereotype.Service
import kotlin.streams.toList

@Service
class BucketFacade(
    private val bucketService: BucketService
) {
    fun findBuckets(): List<BucketInfoResponseDto> {
        return bucketService.findBuckets().stream().map { BucketInfoResponseDto(it) }.toList()
    }

    fun createBucket(requestDto: CreateBucketRequestDto) {
        bucketService.createBucket(
            name = requestDto.name,
            objectLock = requestDto.objectLock
        )
    }

    fun deleteBucket(bucket: String) {
        bucketService.deleteBucket(bucket)
    }
}