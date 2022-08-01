package com.autorush.rushchat.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // Common
    INTERNAL_SERVER_ERROR(500, "C001", "서버 내부 에러입니다."),
    EXTERNAL_SERVER_ERROR(501, "C002", "외부 API 통신 에러입니다."),
    INVALID_INPUT_VALUE(400, "C003", "잘못된 요청 값입니다."),
    HANDLE_ACCESS_DENIED(403, "C004", "권한이 없는 사용자입니다."),

    // Member
    NOT_FOUND_MEMBER(400, "M003", "존재하지 않는 회원입니다."),

    // Room
    NOT_FOUND_ROOM(400, "R001", "존재하지 않는 채팅방입니다."),
    EXCEEDED_MAX_PARTICIPANTS_VALUE(400, "R002", "제한보다 큰 MaxParticipants 값입니다."),
    NOT_FOUND_MEMBER_IN_ROOM(400, "R001", "채팅방 내에 존재하지 않는 회원입니다."),

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