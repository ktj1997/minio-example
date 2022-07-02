package com.example.minio.spring.domain.model

import org.springframework.core.io.Resource
import java.io.InputStream


data class FileInfo(
    val bucket: String,
    val path: String,
    val isDir: Boolean,
)

data class FileDownloadInfo(
    val file: Resource,
    val fileName: String
)

data class FileSearchQuery(
    val bucket: String,
    val path: String
)

data class FileDownloadQuery(
    val bucket: String,
    val path: String
)

data class FileUploadCommand(
    val bucket: String,
    val path: String,
    val contentType: String,
    val fileName: String,
    val inputStream: InputStream
)