package com.example.minio.spring.presentation.controller

import com.example.minio.spring.application.FileDownloadRequestDto
import com.example.minio.spring.application.FileFacade
import com.example.minio.spring.application.FileInfoResponseDto
import com.example.minio.spring.application.FileSearchRequestDto
import com.example.minio.spring.application.FileUploadRequestDto
import com.example.minio.spring.core.config.interceptor.FilePath
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/buckets/{bucket}")
class FileController(
    private val fileFacade: FileFacade
) {

    @GetMapping("/**")
    @ResponseStatus(HttpStatus.OK)
    fun findFiles(
        @PathVariable bucket: String,
        @FilePath filePath: String
    ): List<FileInfoResponseDto> {
        val requestDto = FileSearchRequestDto(
            bucket, filePath
        )
        return fileFacade.findFiles(requestDto)
    }

    @GetMapping("/files/download")
    @ResponseStatus(HttpStatus.OK)
    fun downloadFile(
        @PathVariable bucket: String,
        @RequestParam path: String
    ) {
        val requestDto = FileDownloadRequestDto(
            bucket, path
        )

    }

    @PostMapping("/**")
    @ResponseStatus(HttpStatus.CREATED)
    fun createFile(
        @PathVariable bucket: String,
        @FilePath path: String,
        @RequestPart(value = "file") multipartFile: MultipartFile
    ) {
        val requestDto = FileUploadRequestDto(
            bucket = bucket,
            path = path,
            inputStream = multipartFile.inputStream,
            contentType = multipartFile.contentType!!,
            fileName = multipartFile.originalFilename!!
        )
        fileFacade.createFile(requestDto)

    }

    @DeleteMapping("/**")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteFile(
        @PathVariable bucket: String,
        @FilePath path: String
    ) {

    }
}
