package com.ktnet.core.item.dto;

import java.util.ArrayList;
import java.util.List;

import com.ktnet.core.fta.dto.FtaDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Slf4j
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

    @Builder
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

        log.debug(">>> item type ids :" + itemTypeIds.size());
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