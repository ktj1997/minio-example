package com.example.minio.spring.application

import com.example.minio.spring.domain.service.FileService
import org.springframework.stereotype.Service
import java.io.File
import kotlin.streams.toList

@Service
class FileFacade(
    private val fileService: FileService
) {

    fun findFiles(bucket: String, path: String): List<FileInfoResponseDto> {
        val files = fileService.findFiles(bucket, path)
        return files.stream().map { it.toFileInfoResponseDto() }.toList()
    }

    fun createFile(path: String, file: File) {

    }

    fun updateFile(path: String) {

    }

    fun deleteFile(path: String) {

    }
}