package com.example.minio.spring.core.config.exceptions

import java.time.LocalDateTime

class ExceptionResponse(
    val code: String,
    val message: String,
    val timeStamp: LocalDateTime
)
