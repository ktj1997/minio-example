package com.example.minio.spring

import com.example.minio.spring.MinIoProperties.COMMAND
import com.example.minio.spring.MinIoProperties.IMAGE
import com.example.minio.spring.MinIoProperties.MINIO_ROOT_PASSWORD
import com.example.minio.spring.MinIoProperties.MINIO_ROOT_USER
import com.example.minio.spring.MinIoProperties.PORT
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.web.WebAppConfiguration
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.Duration

@SpringBootTest
@Testcontainers
@WebAppConfiguration
@AutoConfigureMockMvc
class ContainerTestBase {

    companion object {
        @JvmStatic
        private val instance: KGenericContainer =
            KGenericContainer(IMAGE)
                .withExposedPorts(PORT)
                .withEnv("MINIO_ROOT_USER", MINIO_ROOT_USER)
                .withEnv("MINIO_ROOT_PASSWORD", MINIO_ROOT_PASSWORD)
                .withCommand(COMMAND)
                .withStartupTimeout(Duration.ofSeconds(60))
                .apply { start() }

        @JvmStatic
        @DynamicPropertySource
        fun registerMinIoProperty(registry: DynamicPropertyRegistry) {
            val host = instance.host
            val port = instance.getMappedPort(PORT)

            registry.add("minio.url") { "http://$host:$port" }
            registry.add("minio.accessKey") { MINIO_ROOT_USER }
            registry.add("minio.secretKey") { MINIO_ROOT_PASSWORD }
        }
    }
}

class KGenericContainer(image: String) : GenericContainer<KGenericContainer>(image)
