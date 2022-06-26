package com.example.minio.spring.presentation.controller

import com.example.minio.spring.application.FileFacade
import com.example.minio.spring.application.FileInfoResponseDto
import com.example.minio.spring.core.config.interceptor.FilePath
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/buckets/{bucket}/**")
class FileController(
    private val fileFacade: FileFacade
) {

    @GetMapping
    fun getFiles(
        @PathVariable bucket: String,
        @FilePath filePath: String
    ): List<FileInfoResponseDto> {
        return fileFacade.findFiles(bucket, filePath)
    }

    @PostMapping
    fun createFile(
        @PathVariable bucket: String,
        @FilePath path: String
    ) {

    }

    @PutMapping
    fun updateFile(
        @PathVariable bucket: String,
        @FilePath path: String
    ) {

    }

    @DeleteMapping
    fun deleteFile(
        @PathVariable bucket: String,
        @FilePath path: String
    ) {

    }
}
