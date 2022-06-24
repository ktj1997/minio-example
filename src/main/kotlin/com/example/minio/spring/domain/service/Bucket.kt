package com.example.minio.spring.domain.service

import java.time.LocalDateTime

class Bucket(
    val name: String,
    val createdAt: LocalDateTime
) {
}