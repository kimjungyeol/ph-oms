package com.ktnet.fta.judgment.dto;

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

    public JudgmentSetupDto() {
    }

    public JudgmentSetupDto(Long ftaId, Boolean ctcUse, Boolean deminimisUse, Long deminimisBuffer, Boolean rvcUse,
            Long rvcBuffer) {
        this.ftaId = ftaId;
        this.ctcUse = ctcUse;
        this.deminimisUse = deminimisUse;
        this.deminimisBuffer = deminimisBuffer;
        this.rvcUse = rvcUse;
        this.rvcBuffer = rvcBuffer;
    }

}
