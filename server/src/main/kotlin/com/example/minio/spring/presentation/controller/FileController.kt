package com.example.minio.spring.presentation.controller

import com.example.minio.spring.application.FileDownloadRequestDto
import com.example.minio.spring.application.FileFacade
import com.example.minio.spring.application.FileInfoResponseDto
import com.example.minio.spring.application.FileSearchRequestDto
import com.example.minio.spring.application.FileUploadRequestDto
import com.example.minio.spring.application.FileUploadResponseDto
import com.example.minio.spring.core.config.interceptor.FilePath
import com.example.minio.spring.presentation.response.CommonResponse
import org.springframework.core.io.Resource
import org.springframework.http.ContentDisposition
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
import java.net.URLEncoder

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
    ): ResponseEntity<CommonResponse<List<FileInfoResponseDto>>> {
        val requestDto = FileSearchRequestDto(bucket, filePath)
        val responseDto = fileFacade.findFiles(requestDto)

        val response = CommonResponse(data = responseDto, statusCode = 200)
        return ResponseEntity(response,HttpStatus.OK)
    }

    @GetMapping("/files/download")
    @ResponseStatus(HttpStatus.OK)
    fun downloadFile(
        @PathVariable bucket: String,
        @RequestParam path: String
    ): ResponseEntity<Resource> {
        val requestDto = FileDownloadRequestDto(bucket, path)
        val responseDto = fileFacade.downloadFile(requestDto)
        val headers = HttpHeaders()
        headers.contentDisposition =
            ContentDisposition.builder("attachment")
                .filename(URLEncoder.encode(responseDto.fileName, "UTF-8")).build()

        return ResponseEntity(
            responseDto.file,
            headers,
            HttpStatus.OK
        )
    }
    @PostMapping("/**")
    fun createFile(
        @PathVariable bucket: String,
        @FilePath path: String,
        @RequestPart(value = "file") multipartFile: MultipartFile
    ): ResponseEntity<CommonResponse<FileUploadResponseDto>> {
        val requestDto = FileUploadRequestDto(
            bucket = bucket,
            path = path,
            inputStream = multipartFile.inputStream,
            contentType = multipartFile.contentType!!,
            fileName = multipartFile.originalFilename!!
        )
        val responseDto = fileFacade.createFile(requestDto)
        return ResponseEntity(CommonResponse(responseDto, 201), HttpStatus.CREATED)
    }

    @DeleteMapping("/**")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteFile(
        @PathVariable bucket: String,
        @FilePath path: String
    ): ResponseEntity<Void> {
        fileFacade.deleteFile(bucket, path)
        return ResponseEntity.noContent().build()
    }
}
