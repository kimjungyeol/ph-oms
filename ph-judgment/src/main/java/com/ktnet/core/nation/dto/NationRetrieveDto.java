package com.ktnet.core.nation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class NationRetrieveDto {

    private Long id;

    private String code;

    private String name;

    private String englishName;

    private String abbreviation;

    @Builder
    public NationRetrieveDto(Long id, String code, String name, String englishName, String abbreviation) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.englishName = englishName;
        this.abbreviation = abbreviation;
    }
}
