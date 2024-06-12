package com.ktnet.fta.judgment.dto;

import lombok.ToString;

@ToString
public class JudgmentSetupDto {

    private Long ftaId;

    private Boolean ctcUse;

    private Boolean deminimisUse;

    private Long deminimisBuffer;

    private Boolean rvcUse;

    private Long rvcBuffer;

    public Long getFtaId() {
        return ftaId;
    }

    public void setFtaId(Long ftaId) {
        this.ftaId = ftaId;
    }

    public Boolean getCtcUse() {
        return ctcUse;
    }

    public void setCtcUse(Boolean ctcUse) {
        this.ctcUse = ctcUse;
    }

    public Boolean getDeminimisUse() {
        return deminimisUse;
    }

    public void setDeminimisUse(Boolean deminimisUse) {
        this.deminimisUse = deminimisUse;
    }

    public Long getDeminimisBuffer() {
        return deminimisBuffer;
    }

    public void setDeminimisBuffer(Long deminimisBuffer) {
        this.deminimisBuffer = deminimisBuffer;
    }

    public Boolean getRvcUse() {
        return rvcUse;
    }

    public void setRvcUse(Boolean rvcUse) {
        this.rvcUse = rvcUse;
    }

    public Long getRvcBuffer() {
        return rvcBuffer;
    }

    public void setRvcBuffer(Long rvcBuffer) {
        this.rvcBuffer = rvcBuffer;
    }

}
