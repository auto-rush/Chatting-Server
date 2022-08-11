package com.autorush.rushchat.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // Common
    INTERNAL_SERVER_ERROR(500, "C001", "서버 내부 에러입니다."),
    EXTERNAL_SERVER_ERROR(501, "C002", "외부 API 통신 에러입니다."),
    INVALID_INPUT_VALUE(400, "C003", "잘못된 요청 값입니다."),
    HANDLE_ACCESS_DENIED(403, "C004", "권한이 없는 사용자입니다."),
    FILE_READ_ERROR(500,"C005","파일 읽기 에러입니다."),
    PARSING_ERROR(500,"C006","파싱을 실패했습니다"),

    // Member

    // Room

    // Alarm

    ;

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}