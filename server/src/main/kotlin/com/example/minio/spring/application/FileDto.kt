package com.example.minio.spring.application

import com.example.minio.spring.domain.model.FileInfo

class FileRequestDto {

}

class FileInfoResponseDto(
    val bucket: String,
    val path: String,
    val payload: String? = null,
    val isDir: Boolean,
) {
}

fun FileInfo.toFileInfoResponseDto(): FileInfoResponseDto {
    return FileInfoResponseDto(
        bucket = this.bucket,
        path = this.path,
        payload = this.payload,
        isDir = this.isDir,
    )
}