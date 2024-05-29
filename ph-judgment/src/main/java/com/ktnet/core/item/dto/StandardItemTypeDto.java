package com.ktnet.core.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(exclude = "standardItem")
@NoArgsConstructor
public class StandardItemTypeDto {

    private Long id;

    private Long standardItemId;

    private StandardItemDto standardItem;

    private String code;

    private String description;

    @Builder
    public StandardItemTypeDto(Long id, Long standardItemId, StandardItemDto standardItem, String code,
            String description) {
        this.id = id;
        this.standardItemId = standardItemId;
        this.standardItem = standardItem;
        this.code = code;
        this.description = description;
    }

}