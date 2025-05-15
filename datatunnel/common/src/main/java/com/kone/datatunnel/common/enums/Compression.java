package com.kone.datatunnel.common.enums;

// 支持的压缩格式
public enum Compression {
    NONE,
    UNCOMPRESSED,
    GZIP,
    BROTLI,
    SNAPPY,
    ZLIB,
    LZO,
    ZSTD,
    LZ4;
}
