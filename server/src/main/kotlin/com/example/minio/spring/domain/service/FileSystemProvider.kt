package com.example.minio.spring.domain.service

import com.example.minio.spring.domain.model.Bucket
import com.example.minio.spring.domain.model.FileInfo
import java.io.File

interface FileSystemProvider {
    fun createBucket(name: String, objectLock: Boolean)
    fun findBuckets(): List<Bucket>
    fun deleteBucket(name: String)

    fun findFiles(bucket: String, path: String): List<FileInfo>
    fun createFile(bucket: String, path: String, file: File)
    fun updateFile(bucket: String, path: String)
    fun deleteFile(bucket: String, path: String)

}