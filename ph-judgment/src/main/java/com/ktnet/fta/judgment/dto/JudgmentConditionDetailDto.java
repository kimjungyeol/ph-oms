package com.ktnet.fta.judgment.dto;

import java.math.BigDecimal;

import com.ktnet.fta.psr.dto.PsrConditionDto;

import lombok.ToString;

@ToString
public class JudgmentConditionDetailDto {

    private Long id;

    private Long groupId;

    private Long judgmentId;

    private String type;

    private String description;

    private BigDecimal rate;

    private String targetHsCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getJudgmentId() {
        return judgmentId;
    }

    public void setJudgmentId(Long judgmentId) {
        this.judgmentId = judgmentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getTargetHsCode() {
        return targetHsCode;
    }

    public void setTargetHsCode(String targetHsCode) {
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
