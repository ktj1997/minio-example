package com.example.minio.spring.presentation.response

class CommonResponse<T>(
    val data: T,
    val statusCode: Int
) {
}