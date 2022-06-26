package com.example.minio.spring.core.config.exceptions

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ApiException::class)
    fun handleApiException(exception: ApiException): ExceptionResponse {
        val type = exception.type
        return ExceptionResponse(
            code = type.code,
            message = type.message,
            reason = exception.message,
            timeStamp = exception.timeStamp
        )
    }
}