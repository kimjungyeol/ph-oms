package com.ktnet.core.fta.dto;

import java.math.BigDecimal;
import java.util.List;

import com.ktnet.core.hscode.constant.HscodeType;

public class FtaDto {

    private Long id;

    private String code;

    private String name;

    private String englishName;

    private String korchamCode;

    private String customsCode;

    private BigDecimal rvcRate;

    private BigDecimal deminimisRate;

    private String issueType;

    private String incoterms;

    private HscodeType hscodeType;

    private List<FtaPsrDto> psrs;

    private List<FtaNationDto> nations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getKorchamCode() {
        return korchamCode;
    }

    public void setKorchamCode(String korchamCode) {
        this.korchamCode = korchamCode;
    }

    public String getCustomsCode() {
        return customsCode;
    }

    public void setCustomsCode(String customsCode) {
        this.customsCode = customsCode;
    }

    public BigDecimal getRvcRate() {
        return rvcRate;
    }

    public void setRvcRate(BigDecimal rvcRate) {
        this.rvcRate = rvcRate;
    }

    public BigDecimal getDeminimisRate() {
        return deminimisRate;
    }

    public void setDeminimisRate(BigDecimal deminimisRate) {
        this.deminimisRate = deminimisRate;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getIncoterms() {
        return incoterms;
    }

    public void setIncoterms(String incoterms) {
        this.incoterms = incoterms;
    }

    public HscodeType getHscodeType() {
        return hscodeType;
    }

    public void setHscodeType(HscodeType hscodeType) {
        this.hscodeType = hscodeType;
    }

    public List<FtaPsrDto> getPsrs() {
        return psrs;
    }

    public void setPsrs(List<FtaPsrDto> psrs) {
        this.psrs = psrs;
    }

    public List<FtaNationDto> getNations() {
        return nations;
    }

    public void setNations(List<FtaNationDto> nations) {
        this.nations = nations;
    }

    public FtaDto() {
    }

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