package com.example.minio.spring.application

import com.example.minio.spring.domain.model.FileDownloadQuery
import com.example.minio.spring.domain.model.FileInfo
import com.example.minio.spring.domain.model.FileSearchQuery
import com.example.minio.spring.domain.model.FileUploadCommand
import org.springframework.core.io.Resource
import java.io.InputStream

data class FileUploadRequestDto(
    val bucket: String,
    val path: String,
    val contentType: String,
    val fileName: String,
    val inputStream: InputStream,
) {
    fun toCommand(): FileUploadCommand {
        return FileUploadCommand(
            bucket, path, contentType, fileName, inputStream
        )
    }
}

data class FileUploadResponseDto(
    val bucket: String,
    val path: String
)

data class FileDownloadRequestDto(
    val bucket: String,
    val path: String
) {
    fun toQuery(): FileDownloadQuery {
        return FileDownloadQuery(
            bucket, path
        )
    }
}

data class FileDownloadResponseDto(
    val file: Resource,
    val fileName: String
)

data class FileSearchRequestDto(
    val bucket: String,
    val path: String
) {
    fun toQuery(): FileSearchQuery {
        return FileSearchQuery(
            bucket, path
        )
    }
}

data class FileInfoResponseDto(
    val bucket: String,
    val path: String,
    val payload: String? = null,
    val isDir: Boolean,
)

fun FileInfo.toFileInfoResponseDto(): FileInfoResponseDto {
    return FileInfoResponseDto(
        bucket = this.bucket,
        path = this.path,
        isDir = this.isDir,
    )
}
