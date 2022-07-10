package com.example.minio.spring.presentation.controller

import com.example.minio.spring.application.BucketFacade
import com.example.minio.spring.application.BucketInfoResponseDto
import com.example.minio.spring.presentation.request.CreateBucketRequest
import com.example.minio.spring.presentation.response.CommonResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    fun findBuckets(): ResponseEntity<CommonResponse<List<BucketInfoResponseDto>>> {
        val response = CommonResponse(
            data = bucketFacade.findBuckets(),
            statusCode = 200
        )
        return ResponseEntity(response, HttpStatus.OK)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBucket(
        @RequestBody request: CreateBucketRequest
    ): ResponseEntity<CommonResponse<Unit>> {
        val requestDto = request.toDto()
        val responseDto = bucketFacade.createBucket(requestDto)

        return ResponseEntity(CommonResponse(responseDto, 201), HttpStatus.CREATED)
    }

    @DeleteMapping("/{bucket}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBucket(@PathVariable bucket: String): ResponseEntity<CommonResponse<Unit>> {
        val responseDto = bucketFacade.deleteBucket(bucket)
        return ResponseEntity(CommonResponse(responseDto, 204), HttpStatus.NO_CONTENT)
    }
}
