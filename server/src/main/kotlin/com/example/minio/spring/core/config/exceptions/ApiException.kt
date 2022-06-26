package com.example.minio.spring.core.config.exceptions

import java.time.LocalDateTime

open class ApiException(
    val type: ExceptionType,
    override val message: String,
) : RuntimeException(message) {
    val timeStamp = LocalDateTime.now()

    constructor(type: ExceptionType) : this(type, type.message)
}