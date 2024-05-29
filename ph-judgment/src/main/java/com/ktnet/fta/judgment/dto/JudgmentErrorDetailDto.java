package com.ktnet.fta.judgment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class JudgmentErrorDetailDto {

    private Long id;

    private Long groupId;

    private Long judgmentId;

    private String errorType;

    private String contents;

    @Builder
    public JudgmentErrorDetailDto(Long id, Long groupId, Long judgmentId, String errorType, String contents) {
        this.id = id;
        this.groupId = groupId;
        this.judgmentId = judgmentId;
        this.errorType = errorType;
        this.contents = contents;
    }
}