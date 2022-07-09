package com.example.minio.spring

object MinIoProperties {
    val IMAGE: String = "minio/minio"
    val MINIO_ROOT_USER ="test"
    val MINIO_ROOT_PASSWORD = "test1234"
    val COMMAND ="server /data --console-address :9001"

    val PORT = 9000
    val DEFAULT_BUCKET = "testbucket"
}
