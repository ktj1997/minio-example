package com.example.minio.spring.domain.service

import com.example.minio.spring.domain.model.Bucket
import com.example.minio.spring.domain.model.FileDownloadInfo
import com.example.minio.spring.domain.model.FileDownloadQuery
import com.example.minio.spring.domain.model.FileInfo
import com.example.minio.spring.domain.model.FileSearchQuery
import com.example.minio.spring.domain.model.FileUploadCommand

interface FileSystemProvider {
    fun createBucket(name: String, objectLock: Boolean)
    fun findBuckets(): List<Bucket>
    fun deleteBucket(name: String)

    fun findFiles(query: FileSearchQuery): List<FileInfo>
    fun createFile(command: FileUploadCommand): FileInfo
    fun deleteFile(bucket: String, path: String)
    fun downloadFile(query: FileDownloadQuery): FileDownloadInfo
}
