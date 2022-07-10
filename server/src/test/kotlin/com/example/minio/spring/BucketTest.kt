package com.example.minio.spring

import com.example.minio.spring.MinIoProperties.DEFAULT_BUCKET
import com.example.minio.spring.presentation.request.CreateBucketRequest
import com.fasterxml.jackson.databind.ObjectMapper
import io.minio.BucketExistsArgs
import io.minio.MinioClient
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class BucketTest @Autowired constructor(
    private val mvc: MockMvc,
    private val minioClient: MinioClient,
    private val objectMapper: ObjectMapper
) : ContainerTestBase() {

    @Test
    @Order(1)
    fun createBucketTest() {
        val body = CreateBucketRequest(
            name = DEFAULT_BUCKET,
            objectLock = false
        )
        mvc.post("/buckets") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(body)
        }.andExpect {
            status { isCreated() }
        }

        val bucketExistArgs = BucketExistsArgs.builder().bucket(body.name).build()
        assertTrue(minioClient.bucketExists(bucketExistArgs))
    }

    @Test
    @Order(2)
    fun checkBucketExist() {
        mvc.get("/buckets") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("statusCode") { value(200) }
            jsonPath("data[0].name") { value(DEFAULT_BUCKET) }
        }
    }

    @Test
    @Order(3)
    fun deleteBucket() {
        mvc.delete("/buckets/$DEFAULT_BUCKET") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNoContent() }
        }

        val bucketExistArgs = BucketExistsArgs.builder().bucket(DEFAULT_BUCKET).build()
        assertFalse(minioClient.bucketExists(bucketExistArgs))
    }
}
