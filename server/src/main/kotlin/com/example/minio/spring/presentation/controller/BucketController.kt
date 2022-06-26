package com.example.minio.spring.presentation.controller

import com.example.minio.spring.application.BucketFacade
import com.example.minio.spring.application.BucketInfoResponseDto
import com.example.minio.spring.presentation.request.CreateBucketRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/buckets")
class BucketController(
    private val bucketFacade: BucketFacade
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findBuckets(): List<BucketInfoResponseDto> {
        return bucketFacade.findBuckets()
    }

    @PostMapping
    fun createBucket(
        @RequestBody request: CreateBucketRequest
    ) {
        val requestDto = request.toDto()
        bucketFacade.createBucket(requestDto)
    }

    @DeleteMapping("/{bucket}")
    fun deleteBucket(@PathVariable bucket: String) {

    }


}