package com.example.minio.spring.domain.service

import com.example.minio.spring.domain.model.Bucket

interface FileSystemProvider {
    fun createBucket(name: String, objectLock: Boolean)
    fun findBuckets() : List<Bucket>
    fun deleteBucket()

    fun findFiles()
    fun createFile()
    fun updateFile()
    fun deleteFile()

}