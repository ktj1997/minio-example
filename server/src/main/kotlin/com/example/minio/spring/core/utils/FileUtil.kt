package com.example.minio.spring.core.utils

object FileUtil {
    fun isDir(path: String): Boolean {
        val fileExtensionDelimiterIndex = path.lastIndexOf(".")
        return fileExtensionDelimiterIndex == -1
    }

    fun getOriginalFileName(path: String): String {
        val pathDelimiterIndex = path.lastIndexOf("/")

        return if (pathDelimiterIndex == -1)
            path
        else
            path.substring(pathDelimiterIndex + 1)
    }
}
