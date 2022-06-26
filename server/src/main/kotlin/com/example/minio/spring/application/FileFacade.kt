package com.example.minio.spring.application

import com.example.minio.spring.domain.service.FileService
import org.springframework.stereotype.Service

@Service
class FileFacade(
    private val fileService: FileService
) {
}