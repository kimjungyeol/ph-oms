package com.ktnet.fta.psr.dto;

import java.math.BigDecimal;

import lombok.ToString;

@ToString
public class PsrConditionDto {

    private Long id;

    private Long groupId;

    private Long psrId;

    private PsrDto psr;

    private String type;

    private String description;

    private BigDecimal rate;

    private String hscode;

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

    public Long getPsrId() {
        return psrId;
    }

    public void setPsrId(Long psrId) {
        this.psrId = psrId;
    }

    public PsrDto getPsr() {
        return psr;
    }

    public void setPsr(PsrDto psr) {
        this.psr = psr;
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

    public String getHscode() {
        return hscode;
    }

    public void setHscode(String hscode) {
        this.hscode = hscode;
    }
}
