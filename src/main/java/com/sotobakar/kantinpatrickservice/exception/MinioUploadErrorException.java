package com.sotobakar.kantinpatrickservice.exception;

public class MinioUploadErrorException extends RuntimeException {
    public MinioUploadErrorException() {
        super("Terjadi kesalahan saat mengunggah gambar.");
    }
}
