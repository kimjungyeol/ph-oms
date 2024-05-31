package com.ktnet.fta.common.dto;

import java.util.ArrayList;
import java.util.List;

public class StandardItemDto {

    private Long id;

    private Long ftaId;

    private FtaDto fta;

    private Long ftaPsrId;

    private String hscode;

    private String name;

    private String englishName;

    private List<StandardItemTypeDto> types = new ArrayList<>();

    private StandardItemTypeDto selectedType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFtaId() {
        return ftaId;
    }

    public void setFtaId(Long ftaId) {
        this.ftaId = ftaId;
    }

    public FtaDto getFta() {
        return fta;
    }

    public void setFta(FtaDto fta) {
        this.fta = fta;
    }

    public Long getFtaPsrId() {
        return ftaPsrId;
    }

    public void setFtaPsrId(Long ftaPsrId) {
        this.ftaPsrId = ftaPsrId;
    }

    public String getHscode() {
        return hscode;
    }

    public void setHscode(String hscode) {
        this.hscode = hscode;
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

    public List<StandardItemTypeDto> getTypes() {
        return types;
    }

    public void setTypes(List<StandardItemTypeDto> types) {
        this.types = types;
    }

    public StandardItemTypeDto getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(StandardItemTypeDto selectedType) {
        this.selectedType = selectedType;
    }

    public StandardItemDto() {
    }

    public StandardItemDto(Long id, Long ftaId, FtaDto fta, Long ftaPsrId, String name, String englishName,
            String hscode, List<StandardItemTypeDto> types) {
        this.id = id;
        this.ftaId = ftaId;
        this.fta = fta;
        this.ftaPsrId = ftaPsrId;
        this.name = name;
        this.englishName = englishName;
        this.hscode = hscode;
        this.types = types != null ? types : new ArrayList<>();

        this.setSelection(null);
    }

    public StandardItemDto setSelection(List<Long> itemTypeIds) {
        if (this.types == null) {
            return this;
        }

        if (this.types.size() == 1) {
            // 유형이 하나 밖에 없으면 자동 선택
            this.selectedType = this.types.get(0);
            return this;
        }

        if (itemTypeIds == null) {
            return this;
        }

        for (Long itemTypeId : itemTypeIds) {
            // 선택된 ID 중 해당 표준 폼목 유형 선택 처리
            for (StandardItemTypeDto itemType : this.types) {
                if (itemType.getId().equals(itemTypeId)) {
                    this.selectedType = itemType;
                    return this;
                }
            }
        }

        return this;
    }
}