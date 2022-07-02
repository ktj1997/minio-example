package com.example.minio.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class MinioSpringApplication

fun main(args: Array<String>) {
    runApplication<MinioSpringApplication>(*args)
}
