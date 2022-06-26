package com.example.minio.spring.domain.service

interface FileService {
    fun findFiles()
    fun createFile()
    fun updateFile()
    fun deleteFile()
}