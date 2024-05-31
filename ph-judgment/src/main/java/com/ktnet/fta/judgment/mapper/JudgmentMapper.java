package com.ktnet.fta.judgment.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ktnet.fta.judgment.dto.JudgmentDto;
import com.ktnet.fta.judgment.dto.JudgmentSetupDto;

@Mapper
public interface JudgmentMapper {

    public List<JudgmentDto> selectJudgment(Map<String, Object> params);

    public int deleteJudgment(Map<String, Object> map);

    public List<JudgmentSetupDto> selectJudgmentSetup(Map<String, Object> params);

    public void deleteJudgmentCondition(Map<String, Object> params);

}
