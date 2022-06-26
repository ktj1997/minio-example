package com.example.minio.spring.presentation.controller

import com.example.minio.spring.core.config.interceptor.FilePath
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/buckets/{bucket}/**")
class FileController {

    @GetMapping
    fun getFiles(
        @PathVariable bucket: String,
        @FilePath filePath: String
    ) {

    }
}
