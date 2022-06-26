package com.example.minio.spring.infrastructure.minio

import com.example.minio.spring.core.config.exceptions.ApiException
import com.example.minio.spring.core.config.exceptions.ExceptionType
import com.example.minio.spring.core.utils.FileUtil
import com.example.minio.spring.domain.model.Bucket
import com.example.minio.spring.domain.model.FileInfo
import com.example.minio.spring.domain.service.FileSystemProvider
import io.minio.GetObjectArgs
import io.minio.ListObjectsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.RemoveBucketArgs
import org.springframework.stereotype.Component
import java.io.File
import kotlin.streams.toList

@Component
class MinIoFileSystemProvider(
    private val minioClient: MinioClient
) : FileSystemProvider {
    override fun createBucket(name: String, objectLock: Boolean) {
        try {
            val args = MakeBucketArgs.builder().bucket(name)
                .objectLock(objectLock).build()
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
                    name = it.name(),
                    createdAt = it.creationDate().toLocalDateTime()
                )
            }.toList()

        } catch (e: Exception) {
            throw ApiException(ExceptionType.FIND_BUCKETS_FAILURE, e.message!!)
        }
    }

    override fun deleteBucket(bucket: String) {
        try {
            val args = RemoveBucketArgs.builder()
                .bucket(bucket).build()
            minioClient.removeBucket(args)
        } catch (e: Exception) {
            throw ApiException(ExceptionType.DELETE_BUCKET_FAILURE, e.message!!)
        }
    }

    override fun findFiles(bucket: String, path: String): List<FileInfo> {
        try {
            /**
             * Dir
             */
            return if (FileUtil.isDir(path)) {
                val dirPath = "${path}/"
                val args = ListObjectsArgs.builder()
                    .bucket(bucket).prefix(dirPath).build()
                val files = minioClient.listObjects(args)
                return files.asSequence()
                    .map { result -> result.get() }
                    .map { item ->
                        FileInfo(
                            bucket = bucket,
                            path = item.objectName(),
                            isDir = item.isDir,
                        )
                    }.toList()

            } else {
                val args = GetObjectArgs.builder().bucket(bucket)
                    .`object`(path).build()
                val response = minioClient.getObject(args)
                val fileInfo = FileInfo(
                    bucket = response.bucket(),
                    path = response.`object`(),
                    isDir = false,
                    payload = String(response.readAllBytes()),
                )

                listOf(fileInfo)
            }

        } catch (e: Exception) {
            throw ApiException(ExceptionType.FIND_FILE_FAILURE, e.message!!)
        }
    }

    override fun createFile(bucket: String, path: String, file: File) {
        TODO("Not yet implemented")
    }

    override fun updateFile(bucket: String, path: String) {
        TODO("Not yet implemented")
    }

    override fun deleteFile(bucket: String, path: String) {
        TODO("Not yet implemented")
    }
}