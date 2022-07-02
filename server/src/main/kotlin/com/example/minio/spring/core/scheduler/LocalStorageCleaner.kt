package com.example.minio.spring.core.scheduler

import com.example.minio.spring.core.config.minio.MinIoProperties
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.File

@Component
class LocalStorageCleaner(
    private val minIoProperties: MinIoProperties
) {
    /**
     * 한 시간 마다 비워주기
     */
    @Scheduled(fixedDelay = 1000 * 60 * 60)
    fun cleanLocalStorage() {
        val downloadDirectory = minIoProperties.downloadFilePath
        val file = File(downloadDirectory)

        if (file.exists() && file.isDirectory) {
            val subFiles = file.listFiles()
            subFiles?.forEach { subFile -> subFile.delete() }
        }
    }
}
