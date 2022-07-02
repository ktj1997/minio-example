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

    fun createFile(requestDto: FileUploadRequestDto) {
        val command = requestDto.toCommand()
        fileService.createFile(command)
    }

    fun downloadFile(requestDto: FileDownloadRequestDto): FileDownloadResponseDto {
        val query = requestDto.toQuery()
        val responseDto = fileService.downloadFile(query)

        return FileDownloadResponseDto(
            file = responseDto.file,
            fileName = responseDto.fileName
        )
    }

    fun deleteFile(bucket: String, path: String) {

    }
}