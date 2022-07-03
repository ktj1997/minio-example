package com.example.minio.spring.application

import com.example.minio.spring.domain.service.FileService
import org.springframework.stereotype.Service
import kotlin.streams.toList

@Service
class FileFacade(
    private val fileService: FileService
) {

    fun findFiles(requestDto: FileSearchRequestDto): List<FileInfoResponseDto> {
        val query = requestDto.toQuery()
        val files = fileService.findFiles(query)
        return files.stream().map { it.toFileInfoResponseDto() }.toList()
    }

    fun createFile(requestDto: FileUploadRequestDto): FileUploadResponseDto {
        val command = requestDto.toCommand()
        val info = fileService.createFile(command)
        return FileUploadResponseDto(
            bucket = info.bucket,
            path = info.path
        )
    }

    fun downloadFile(requestDto: FileDownloadRequestDto): FileDownloadResponseDto {
        val query = requestDto.toQuery()
        val info = fileService.downloadFile(query)

        return FileDownloadResponseDto(
            file = info.file,
            fileName = info.fileName
        )
    }

    fun deleteFile(bucket: String, path: String) {
        fileService.deleteFile(bucket, path)
    }
}
