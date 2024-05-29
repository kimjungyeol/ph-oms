package com.ktnet.core.psr.condition.dto;

import java.math.BigDecimal;

import com.ktnet.core.psr.base.dto.PsrDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class PsrConditionDto {

    private Long id;

    private Long groupId;

    private Long psrId;

    private PsrDto psr;

    private String type;

    private String description;

    private BigDecimal rate;

    private String hscode;

    @Builder
    public PsrConditionDto(Long id, Long groupId, Long psrId, PsrDto psr, String type, String description,
            BigDecimal rate, String hscode) {
        this.id = id;
        this.groupId = groupId;
        this.psrId = psrId;
        this.psr = psr;
        this.type = type;
        this.description = description;
        this.rate = rate;
        this.hscode = hscode;
    }
}
