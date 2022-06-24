package com.example.minio.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MinioSpringApplication

fun main(args: Array<String>) {
    runApplication<MinioSpringApplication>(*args)
}
