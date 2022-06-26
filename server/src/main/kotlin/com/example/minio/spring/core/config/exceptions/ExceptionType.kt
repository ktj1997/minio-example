package com.example.minio.spring.core.config.exceptions

enum class ExceptionType(
    val code: String,
    val message: String
) {
    CREATE_BUCKET_FAILURE("BUCKET_001", ""),
    DELETE_BUCKET_FAILURE("BUCKET_002", "")
}