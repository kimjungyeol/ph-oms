package com.ktnet.core.fta.dto;

import com.ktnet.core.nation.dto.NationRetrieveDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class FtaNationDto {

    private Long id;

    private Long ftaId;

    private String beginDate;
    private String endDate = "29991231";

    private Long nationId;
    private NationRetrieveDto nation;

    @Builder
    public FtaNationDto(Long id, Long ftaId, String beginDate, String endDate, Long nationId,
            NationRetrieveDto nation) {
        this.id = id;
        this.ftaId = ftaId;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.nationId = nationId;
        this.nation = nation;
    }
}
