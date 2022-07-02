package com.example.minio.spring.core.config.minio

import io.minio.MinioClient
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(MinIoProperties::class)
class MinIoConfig(
    private val minIoProperties: MinIoProperties
) {
    @Bean
    fun minIoClient(): MinioClient {
        return MinioClient.builder()
            .credentials(
                minIoProperties.accessKey,
                minIoProperties.secretKey
            ).endpoint(minIoProperties.url)
            .build()
    }
}

@ConstructorBinding
@ConfigurationProperties("minio")
data class MinIoProperties(
    val accessKey: String,
    val secretKey: String,
    val url: String,
    val fileSize: Long,
    val downloadFilePath: String
)