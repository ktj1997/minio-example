package com.example.minio.spring.infrastructure.minio

import com.example.minio.spring.core.config.exceptions.ApiException
import com.example.minio.spring.core.config.exceptions.ExceptionType
import com.example.minio.spring.core.config.minio.MinIoProperties
import com.example.minio.spring.core.utils.FileUtil
import com.example.minio.spring.domain.model.Bucket
import com.example.minio.spring.domain.model.FileDownloadInfo
import com.example.minio.spring.domain.model.FileDownloadQuery
import com.example.minio.spring.domain.model.FileInfo
import com.example.minio.spring.domain.model.FileSearchQuery
import com.example.minio.spring.domain.model.FileUploadCommand
import com.example.minio.spring.domain.service.FileSystemProvider
import io.minio.DownloadObjectArgs
import io.minio.ListObjectsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.RemoveBucketArgs
import io.minio.RemoveObjectArgs
import org.springframework.core.io.InputStreamResource
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Paths
import kotlin.streams.toList

@Component
class MinIoFileSystemProvider(
    private val minioClient: MinioClient,
    private val minIoProperties: MinIoProperties
) : FileSystemProvider {

    override fun createBucket(name: String, objectLock: Boolean) {
        try {
            val args = MakeBucketArgs.builder().bucket(name).objectLock(objectLock).build()
            minioClient.makeBucket(args)
        } catch (e: Exception) {
            throw ApiException(ExceptionType.CREATE_BUCKET_FAILURE, e)
        }
    }

    override fun findBuckets(): List<Bucket> {
        try {
            val buckets = minioClient.listBuckets()
            return buckets.stream().map {
                Bucket(
                    name = it.name(), createdAt = it.creationDate().toLocalDateTime()
                )
            }.toList()
        } catch (e: Exception) {
            throw ApiException(ExceptionType.FIND_BUCKETS_FAILURE, e)
        }
    }

    override fun deleteBucket(bucket: String) {
        try {
            val args = RemoveBucketArgs.builder().bucket(bucket).build()
            minioClient.removeBucket(args)
        } catch (e: Exception) {
            throw ApiException(ExceptionType.DELETE_BUCKET_FAILURE, e)
        }
    }

    override fun findFiles(query: FileSearchQuery): List<FileInfo> {
        val (bucket, path) = query

        if (!FileUtil.isDir(path)) throw ApiException(ExceptionType.FIND_FILE_FAILURE)
        try {
            val dirPath = "$path/"
            val args = ListObjectsArgs.builder().bucket(bucket).prefix(dirPath).build()
            val files = minioClient.listObjects(args)
            return files.asSequence().map { result -> result.get() }.map { item ->
                FileInfo(
                    bucket = bucket,
                    path = item.objectName(),
                    isDir = item.isDir,
                )
            }.toList()
        } catch (e: Exception) {
            throw ApiException(ExceptionType.FIND_FILE_FAILURE, e)
        }
    }

    override fun createFile(command: FileUploadCommand): FileInfo {
        try {
            val filePath = "${command.path}/${command.fileName}"
            val args = PutObjectArgs.builder()
                .bucket(command.bucket)
                .contentType(command.contentType)
                .`object`(filePath)
                .stream(command.inputStream, -1, minIoProperties.fileSize).build()
            val response = minioClient.putObject(args)

            return FileInfo(
                bucket = response.bucket(),
                path = response.`object`(),
                isDir = false
            )
        } catch (e: Exception) {
            throw ApiException(ExceptionType.UPLOAD_FILE_FAILURE, e)
        }
    }

    override fun downloadFile(query: FileDownloadQuery): FileDownloadInfo {
        val (bucket, path) = query
        val baseDirectory = File(minIoProperties.downloadFilePath)
        val fileName = FileUtil.getOriginalFileName(path)
        val tmpFilePath = "${minIoProperties.downloadFilePath}/$fileName"

        baseDirectory.mkdirs()
        downloadObjectToLocal(bucket, path, tmpFilePath)

        val downloadFile = Paths.get(tmpFilePath).toFile()
        if (downloadFile.exists() && downloadFile.isFile) {
            val inputStream = downloadFile.inputStream()
            val file = InputStreamResource(inputStream)
            return FileDownloadInfo(
                file = file,
                fileName = fileName
            )
        } else throw ApiException(ExceptionType.DOWNLOAD_FILE_FAILURE)
    }

    private fun downloadObjectToLocal(bucket: String, path: String, tmpFilePath: String) {
        val args = DownloadObjectArgs.builder().bucket(bucket).`object`(path).filename(tmpFilePath).build()
        try {
            minioClient.downloadObject(args)
        } catch (e: Exception) {
            throw ApiException(ExceptionType.DOWNLOAD_FILE_FAILURE, e)
        }
    }

    override fun deleteFile(bucket: String, path: String) {
        val args = RemoveObjectArgs.builder()
            .bucket(bucket)
            .`object`(path)
            .build()
        try {
            minioClient.removeObject(args)
        } catch (e: Exception) {
            throw ApiException(ExceptionType.REMOVE_FILE_FAILURE, e)
        }
    }
}
