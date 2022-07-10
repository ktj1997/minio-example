package com.example.minio.spring

import com.example.minio.spring.MinIoProperties.DEFAULT_BUCKET
import io.minio.GetObjectArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.RemoveBucketArgs
import io.minio.errors.ErrorResponseException
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ObjectTest @Autowired constructor(
    private val mvc: MockMvc,
    private val minIoClient: MinioClient
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

    private val fileName = "MockMultipartFIle.txt"

    @Test
    @Order(1)
    fun createFileTest() {
        val file = MockMultipartFile("file", fileName, MediaType.TEXT_PLAIN_VALUE, "hello".byteInputStream())
        mvc.perform(
            multipart("/buckets/$DEFAULT_BUCKET")
                .file(file)
                .servletPath("/buckets/$DEFAULT_BUCKET")
        ).andExpect() {
            status().isCreated
            jsonPath("statusCode").value(200)
        }

        val args = GetObjectArgs.builder().bucket(DEFAULT_BUCKET).`object`(fileName).build()
        val response = assertDoesNotThrow { minIoClient.getObject(args) }
        assertEquals(fileName, response.`object`())
    }

    @Test
    @Order(2)
    fun findObjectTest() {
        mvc.get("/buckets/$DEFAULT_BUCKET") {
            servletPath = "/buckets/$DEFAULT_BUCKET"
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("data[0].bucket") { value(DEFAULT_BUCKET) }
            jsonPath("data[0].path") { value(fileName) }
            jsonPath("data[0].isDir") { value("false") }
            jsonPath("statusCode") { value(200) }
        }
    }

    @Test
    @Order(3)
    fun deleteFileTest() {
        mvc.delete("/buckets/$DEFAULT_BUCKET/$fileName") {
            servletPath = "/buckets/$DEFAULT_BUCKET/$fileName"
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNoContent() }
            jsonPath("statusCode") { value(204) }
        }
        val args = GetObjectArgs.builder().bucket(DEFAULT_BUCKET).`object`(fileName).build()
        assertThrows<ErrorResponseException>() { minIoClient.getObject(args) }
    }
}
