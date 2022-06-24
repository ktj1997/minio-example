package com.example.minio.spring.application

import com.example.minio.spring.domain.service.BucketService
import org.springframework.stereotype.Service

@Service
class BucketFacade(
    private val bucketService: BucketService
) {
}