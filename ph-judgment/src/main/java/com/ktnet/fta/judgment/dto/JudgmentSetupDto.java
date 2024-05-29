package com.ktnet.fta.judgment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class JudgmentSetupDto {

    private Long ftaId;

    private Boolean ctcUse;

    private Boolean deminimisUse;

    private Long deminimisBuffer;

    private Boolean rvcUse;

    private Long rvcBuffer;

    @Builder
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
