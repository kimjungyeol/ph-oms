package com.ktnet.core.fta.dto;

import com.ktnet.core.hscode.constant.HscodeType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class FtaPsrDto {

    private Long id;

    private Long ftaId;

    private String beginDate;
    private String endDate = "29991231";

    // @Enumerated(EnumType.STRING)
    private HscodeType hscodeType;

    @Builder
    public FtaPsrDto(Long id, Long ftaId, String beginDate, String endDate, HscodeType hscodeType) {
        this.id = id;
        this.ftaId = ftaId;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.hscodeType = hscodeType;
    }
}
