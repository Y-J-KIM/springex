package org.zerock.springex.dto;

import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

//롬복으로 toString 메소드, 겟셋메소드, 빌더, 전체변수생성자, 기본생성자
@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {

    private Long tno;           //번호
    //유효성 검증으로 검증할 어노테이션을 변수에 붙임
    @NotEmpty
    private String title;       //제목

    @Future
    private LocalDate dueDate;  //날짜

    private boolean finished;   //완료됨?

    @NotEmpty
    private String writer;      //새로 추가됨
}
