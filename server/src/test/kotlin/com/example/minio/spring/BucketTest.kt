package com.example.minio.spring

import com.example.minio.spring.MinIoProperties.DEFAULT_BUCKET
import com.example.minio.spring.application.BucketFacade
import com.example.minio.spring.application.CreateBucketRequestDto
import io.minio.BucketExistsArgs
import io.minio.MinioClient
import io.minio.RemoveBucketArgs
import junit.framework.Assert.assertFalse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class BucketTest @Autowired constructor(
    private val minioClient: MinioClient,
    private val bucketFacade: BucketFacade
) : ContainerTestBase() {

    @Test
    @Order(1)
    fun createBucketTest() {
        val request = CreateBucketRequestDto(
            name = DEFAULT_BUCKET,
            objectLock = false
        )
        val bucketExistArgs = BucketExistsArgs.builder().bucket(request.name).build()

        assertFalse(minioClient.bucketExists(bucketExistArgs))
        assertDoesNotThrow { bucketFacade.createBucket(request) }
        assertTrue(minioClient.bucketExists(bucketExistArgs))
    }

    @Test
    @Order(2)
    fun checkBucketExist(){
        val buckets = bucketFacade.findBuckets()
        assertTrue(buckets.any { it.name == DEFAULT_BUCKET })
    }

    @Test
    @Order(3)
    fun deleteBucket(){
        val bucketExistArgs = BucketExistsArgs.builder().bucket(DEFAULT_BUCKET).build()

        assertTrue(minioClient.bucketExists(bucketExistArgs))
        assertDoesNotThrow { bucketFacade.deleteBucket(DEFAULT_BUCKET) }
        assertFalse(minioClient.bucketExists(bucketExistArgs))
    }

}
