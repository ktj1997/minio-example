package com.example.minio.spring.core.utils

import org.springframework.web.multipart.MultipartFile
import java.io.File

object FileUtil {
    fun isDir(path: String): Boolean {
        val fileExtensionDelimiterIndex = path.lastIndexOf(".")
        return fileExtensionDelimiterIndex == -1
    }
}