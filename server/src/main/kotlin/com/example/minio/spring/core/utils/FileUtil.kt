package com.example.minio.spring.core.utils

object FileUtil {
    fun isDir(path: String): Boolean {
        val fileExtensionDelimiterIndex = path.lastIndexOf(".")
        return fileExtensionDelimiterIndex == -1
    }
}