package com.example.minio.spring.presentation.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/buckets")
class BucketController {

    @GetMapping
    fun getBuckets() {

    }

    @PostMapping
    fun createBucket() {

    }

    @DeleteMapping("/{bucket}")
    fun deleteBucket(@PathVariable bucket: String) {

    }


}