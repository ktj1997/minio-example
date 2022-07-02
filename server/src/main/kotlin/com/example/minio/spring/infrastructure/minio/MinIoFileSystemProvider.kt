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
import org.springframework.core.io.InputStreamResource
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Paths
import kotlin.streams.toList

@Component
class MinIoFileSystemProvider(
    private val minioClient: MinioClient, private val minIoProperties: MinIoProperties
) : FileSystemProvider {
    override fun createBucket(name: String, objectLock: Boolean) {
        try {
            val args = MakeBucketArgs.builder().bucket(name).objectLock(objectLock).build()
            minioClient.makeBucket(args)
        } catch (e: Exception) {
            throw ApiException(ExceptionType.CREATE_BUCKET_FAILURE, e.message!!)
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
            throw ApiException(ExceptionType.FIND_BUCKETS_FAILURE, e.message!!)
        }
    }

    override fun deleteBucket(bucket: String) {
        try {
            val args = RemoveBucketArgs.builder().bucket(bucket).build()
            minioClient.removeBucket(args)
        } catch (e: Exception) {
            throw ApiException(ExceptionType.DELETE_BUCKET_FAILURE, e.message!!)
        }
    }

    override fun findFiles(query: FileSearchQuery): List<FileInfo> {
        val (bucket, path) = query

        if (!FileUtil.isDir(path)) throw ApiException(ExceptionType.FIND_FILE_FAILURE)
        try {
            val dirPath = "${path}/"
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
            throw ApiException(ExceptionType.FIND_FILE_FAILURE, e.message!!)
        }
    }

    /**
     * TODO 경로애 대한 리턴값 작성하기
     */
    override fun createFile(command: FileUploadCommand) {
        try {
            val args = PutObjectArgs.builder().bucket(command.bucket).contentType(command.contentType)
                .`object`(command.fileName).stream(command.inputStream, -1, minIoProperties.fileSize).build()

            minioClient.putObject(args)
        } catch (e: Exception) {
            throw ApiException(ExceptionType.UPLOAD_FILE_FAILURE, e.message!!)
        }
    }

    override fun downloadFile(query: FileDownloadQuery): FileDownloadInfo {
        val (bucket, path) = query
        val baseDirectory = File(minIoProperties.downloadFilePath)
        val tmpFilePath = "${minIoProperties.downloadFilePath}/${path}"

        baseDirectory.mkdirs()
        downloadObjectToLocal(bucket, path, tmpFilePath)

        val downloadFile = Paths.get(tmpFilePath).toFile()
        if (downloadFile.exists() && downloadFile.isFile) {
            val inputStream = downloadFile.inputStream()
            val file = InputStreamResource(inputStream)
            return FileDownloadInfo(
                file = file,
                fileName = FileUtil.getOriginalFileName(path)
            )
        } else throw ApiException(ExceptionType.DOWNLOAD_FILE_FAILURE)
    }


    private fun downloadObjectToLocal(bucket: String, path: String, tmpFilePath: String) {
        val args = DownloadObjectArgs.builder().bucket(bucket).`object`(path).filename(tmpFilePath).build()
        try {
            minioClient.downloadObject(args)
        } catch (e: Exception) {
            throw ApiException(ExceptionType.DOWNLOAD_FILE_FAILURE)
        }
    }

    override fun deleteFile(bucket: String, path: String) {
        TODO("Not yet implemented")
    }
}