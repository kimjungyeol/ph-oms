package com.ktnet.fta.judgment.dto;

import java.math.BigDecimal;

import com.ktnet.core.psr.condition.dto.PsrConditionDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class JudgmentConditionDetailDto {

    private Long id;

    private Long groupId;

    private Long judgmentId;

    private String type;

    private String description;

    private BigDecimal rate;

    private String targetHsCode;

    @Builder
    public JudgmentConditionDetailDto(Long id, Long groupId, Long judgmentId, String type, String description,
            BigDecimal rate, String targetHsCode) {
        this.id = id;
        this.groupId = groupId;
        this.judgmentId = judgmentId;
        this.type = type;
        this.description = description;
        this.rate = rate;
        this.targetHsCode = targetHsCode;
    }

    public JudgmentConditionDetailDto update(PsrConditionDto dto) {
        this.type = dto.getType();
        this.description = dto.getDescription();
        this.rate = dto.getRate();
        this.targetHsCode = dto.getHscode();
        return this;
    }
}
