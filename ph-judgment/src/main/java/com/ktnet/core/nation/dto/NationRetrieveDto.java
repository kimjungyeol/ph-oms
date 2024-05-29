package com.ktnet.core.nation.dto;

public class NationRetrieveDto {

    private Long id;

    private String code;

    private String name;

    private String englishName;

    private String abbreviation;

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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public NationRetrieveDto() {
    }

    public NationRetrieveDto(Long id, String code, String name, String englishName, String abbreviation) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.englishName = englishName;
        this.abbreviation = abbreviation;
    }
}
