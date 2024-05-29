package com.ktnet.core.fta.dto;

import java.math.BigDecimal;
import java.util.List;

import com.ktnet.core.hscode.constant.HscodeType;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class FtaDto {

    private Long id;

    @EqualsAndHashCode.Include
    private String code;

    private String name;

    private String englishName;

    private String korchamCode;

    private String customsCode;

    private BigDecimal rvcRate;

    private BigDecimal deminimisRate;

    private String issueType;

    private String incoterms;

    // @Enumerated(EnumType.STRING)
    private HscodeType hscodeType;

    @Setter
    private List<FtaPsrDto> psrs;

    @Setter
    private List<FtaNationDto> nations;

    @Builder
    public FtaDto(Long id, String code, String name, String englishName, String korchamCode, String customsCode,
            BigDecimal rvcRate, BigDecimal deminimisRate, String issueType, String incoterms, HscodeType hscodeType,
            List<FtaPsrDto> psrs, List<FtaNationDto> nations) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.englishName = englishName;
        this.korchamCode = korchamCode;
        this.customsCode = customsCode;
        this.rvcRate = rvcRate;
        this.deminimisRate = deminimisRate;
        this.issueType = issueType;
        this.incoterms = incoterms;
        this.hscodeType = hscodeType;
        this.psrs = psrs;
        this.nations = nations;
    }
}