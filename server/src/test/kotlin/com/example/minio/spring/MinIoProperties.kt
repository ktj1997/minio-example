package com.example.minio.spring

object MinIoProperties {
    const val IMAGE: String = "minio/minio"
    const val MINIO_ROOT_USER = "test"
    const val MINIO_ROOT_PASSWORD = "test1234"
    const val COMMAND = "server /data --console-address :9001"

    const val PORT = 9000
    const val DEFAULT_BUCKET = "testbucket"
}
