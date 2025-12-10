package com.example.outsourcingprojects.domain.task.dto;

import com.example.outsourcingprojects.common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
//필요없는 import문은 반드시 제거해주세요.

@Getter
@AllArgsConstructor
public class CreateTaskResponseDto {
    //response 응답객체의 필드는 final로 지정하는것에 대해 고민해보시기 바랍니다.
    //final로 지정하면 상단의 생성자 어노테이션도 변경되어야 합니다.
    private Long id;

    private long assigneeId;

    private String title;

    private String description;

    private Long priority;

    private Long status;

    private LocalDateTime dueDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    //정적 팩터리 메서드를 활용하도록 연습해봐요
    //정적 팩토리 메서드란?
    /** static factory method?
     * 정적 팩토리 메서드는 객체를 생성하기 위한 정적 메서드로,
     * 객체의 생성과 초기화를 담당하는 메서드를 제공하는 디자인 패턴입니다.
     * 간단히 말하면, 클래스 내부에 정의된 정적 메서드를 통해 객체를 생성하는 방식입니다.
     * new 키워드를 직접 사용하는 대신, 메서드를 통해 객체를 반환받습니다.**/

}
