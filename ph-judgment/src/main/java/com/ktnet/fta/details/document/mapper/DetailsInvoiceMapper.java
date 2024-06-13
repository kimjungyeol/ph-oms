package com.ktnet.fta.details.document.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DetailsInvoiceMapper {

    public Long selectGroupId(Map<String, Object> map);

    public int updateGroupId(Map<String, Object> map);

    public Long selectInvoiceId(Map<String, Object> map);

}
