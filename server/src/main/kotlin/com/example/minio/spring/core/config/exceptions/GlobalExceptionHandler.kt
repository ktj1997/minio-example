package com.example.minio.spring.core.config.exceptions

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    companion object {
        private val logger = LoggerFactory.getLogger(this.javaClass)
    }

    @ExceptionHandler(ApiException::class)
    fun handleApiException(exception: ApiException): ExceptionResponse {
        logger.error(exception.stackTraceToString())
        val type = exception.type
        return ExceptionResponse(
            code = type.code,
            message = type.message,
            timeStamp = exception.timeStamp
        )
    }
}
