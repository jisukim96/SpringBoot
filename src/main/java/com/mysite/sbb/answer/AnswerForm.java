package com.mysite.sbb.answer;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerForm {

	@NotEmpty(message="내용이 비어있습니다. 반드시 내용을 입력하세요 ")
	private String content;
}
