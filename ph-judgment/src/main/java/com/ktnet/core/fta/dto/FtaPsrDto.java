package com.ktnet.core.fta.dto;

import com.ktnet.core.hscode.constant.HscodeType;

public class FtaPsrDto {

    private Long id;

    private Long ftaId;

    private String beginDate;

    private String endDate = "29991231";

    private HscodeType hscodeType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFtaId() {
        return ftaId;
    }

    public void setFtaId(Long ftaId) {
        this.ftaId = ftaId;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public HscodeType getHscodeType() {
        return hscodeType;
    }

    public void setHscodeType(HscodeType hscodeType) {
        this.hscodeType = hscodeType;
    }

    public FtaPsrDto() {
    }

    public FtaPsrDto(Long id, Long ftaId, String beginDate, String endDate, HscodeType hscodeType) {
        this.id = id;
        this.ftaId = ftaId;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.hscodeType = hscodeType;
    }
}
