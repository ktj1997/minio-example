package com.example.minio.spring.core.config.exceptions

import java.time.LocalDateTime

open class ApiException : RuntimeException {
    val type: ExceptionType
    val timeStamp: LocalDateTime

    constructor(type: ExceptionType) : super(type.message) {
        this.type = type
        this.timeStamp = LocalDateTime.now()
    }

    constructor(type: ExceptionType, throwable: Throwable) : super(throwable) {
        this.type = type
        this.timeStamp = LocalDateTime.now()
    }
}
