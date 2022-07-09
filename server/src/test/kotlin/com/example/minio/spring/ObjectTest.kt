package com.example.minio.spring

import com.example.minio.spring.MinIoProperties.DEFAULT_BUCKET
import com.example.minio.spring.application.FileFacade
import com.example.minio.spring.application.FileUploadRequestDto
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.RemoveBucketArgs
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class ObjectTest @Autowired constructor(
    private val minIoClient: MinioClient,
    private val fileFacade: FileFacade
) : ContainerTestBase() {
    companion object {
        @JvmStatic
        @BeforeAll
        fun createDefaultBucket(@Autowired minIoClient: MinioClient) {
            val args = MakeBucketArgs.builder().bucket(DEFAULT_BUCKET).build()
            minIoClient.makeBucket(args)
        }

        @JvmStatic
        @AfterAll
        fun deleteDefaultBucket(@Autowired minIoClient: MinioClient) {
            val args = RemoveBucketArgs.builder().bucket(DEFAULT_BUCKET).build()
            minIoClient.removeBucket(args)
        }
    }
}
