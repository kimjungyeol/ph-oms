package com.ktnet.fta.psr.dto;

import java.util.ArrayList;
import java.util.List;

import com.ktnet.common.util.DateUtil;

public class PsrSearchParamsDto {

    private String applicationDate = DateUtil.now();

    private Long ftaId;

    private List<String> hscodes = new ArrayList<>();

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Long getFtaId() {
        return ftaId;
    }

    public void setFtaId(Long ftaId) {
        this.ftaId = ftaId;
    }

    public List<String> getHscodes() {
        return hscodes;
    }

    public void setHscodes(List<String> hscodes) {
        this.hscodes = hscodes;
    }
}
