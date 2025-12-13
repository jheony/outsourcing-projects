package com.example.outsourcingprojects.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 400 Bad Request
    EMPTY_TITLE_AND_ASSIGNEE(HttpStatus.BAD_REQUEST,"제목과 담당자는 필수입니다."),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "올바른 이메일 형식이 아닙니다."),
    REQUIRED_FIELD_MISSING(HttpStatus.BAD_REQUEST, "필수 입력 값이 누락되었습니다."),
    INVALID_QUERY_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 요청 파라미터입니다."),
    INVALID_STATUS_VALUE(HttpStatus.BAD_REQUEST, "유효하지 않은 상태 값입니다."),
    EMPTY_COMMENT(HttpStatus.BAD_REQUEST, "댓글 내용은 필수입니다."),
    EMPTY_SEARCH_KEYWORD(HttpStatus.BAD_REQUEST, "검색어를 입력해주세요."),
    EMPTY_TEAM_NAME(HttpStatus.BAD_REQUEST, "팀 이름은 필수입니다."),

    // 401 Unauthorized
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다."),
    NO_PERMISSION(HttpStatus.UNAUTHORIZED, "비밀번호가 올바르지 않습니다."),
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다."),

    // 403 Forbidden
    NO_DELETE_PERMISSION(HttpStatus.FORBIDDEN, "삭제 권한이 없습니다."),
    NO_UPDATE_PERMISSION(HttpStatus.FORBIDDEN, "수정 권한이 없습니다."),
    NO_REMOVE_PERMISSION(HttpStatus.FORBIDDEN, "제거 권한이 없습니다."),
    NO_COMMENT_DELETE_PERMISSION(HttpStatus.FORBIDDEN, "댓글을 삭제할 권한이 없습니다."),
    NO_COMMENT_UPDATE_PERMISSION(HttpStatus.FORBIDDEN, "댓글을 수정할 권한이 없습니다."),
    NO_TASK_DELETE_PERMISSION(HttpStatus.FORBIDDEN, "작업을 삭제할 권한이 없습니다."),
    NO_READ_PERMISSION(HttpStatus.FORBIDDEN, "권한이 없습니다."),

    // 404 Not Found
    ASSIGNEE_NOT_FOUND(HttpStatus.NOT_FOUND, "담당자를 찾을 수 없습니다."),
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "작업을 찾을 수 없습니다."),
    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "팀을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    TEAM_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "팀 멤버를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    TASK_OR_PARENT_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "작업 또는 부모 댓글을 찾을 수 없습니다."),
    TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 타입이 존재하지 않습니다."),

    // 409 Conflict
    TEAM_HAS_MEMBERS(HttpStatus.CONFLICT, "팀에 멤버가 존재하여 삭제할 수 없습니다."),
    ALREADY_TEAM_MEMBER(HttpStatus.CONFLICT, "이미 팀에 속한 멤버 입니다."),
    DUPLICATE_TEAM_NAME(HttpStatus.CONFLICT, "이미 존재하는 팀 이름 입니다."),
    USER_NAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 사용자명 입니다."),
    DUPLICATE_USER_NAME(HttpStatus.CONFLICT, "이미 사용 중인 이름 입니다."),
    DUPLICATE_USER_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일 입니다."),
    DUPLICATE_USER_PASSWORD(HttpStatus.CONFLICT, "이미 사용 중인 비밀번호 입니다.")
    ;
    private final HttpStatus status;
    private final String message;
}
