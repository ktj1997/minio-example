package com.example.minio.spring.core.config.exceptions

enum class ExceptionType(
    val code: String,
    val message: String
) {
    CREATE_BUCKET_FAILURE("BUCKET_001", "Bucekt 생성에 실패하였습니다"),
    DELETE_BUCKET_FAILURE("BUCKET_002", "Bucket 삭제에 실패햐였습니다."),
    FIND_BUCKETS_FAILURE("BUCKET_003", "Bucket 탐색에 실패하였습니다."),

    FIND_FILE_FAILURE("FILE_001", "File 탐색에 실패하였습니다."),
    UPLOAD_FILE_FAILURE("FILE_002", "File 업로드에 실패하였습니다.")
}