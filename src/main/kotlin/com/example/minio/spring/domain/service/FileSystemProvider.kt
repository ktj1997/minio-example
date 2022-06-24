package com.example.minio.spring.domain.service

interface FileSystemProvider {
    fun createBucket()
    fun findBuckets()
    fun deleteBucket()

    fun findFiles()
    fun createFile()
    fun updateFile()
    fun deleteFile()

}