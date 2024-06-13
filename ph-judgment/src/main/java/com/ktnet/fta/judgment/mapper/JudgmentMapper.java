package com.ktnet.fta.judgment.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ktnet.fta.judgment.dto.JudgmentConditionDetailDto;
import com.ktnet.fta.judgment.dto.JudgmentDto;
import com.ktnet.fta.judgment.dto.JudgmentErrorDetailDto;
import com.ktnet.fta.judgment.dto.JudgmentSetupDto;

@Mapper
public interface JudgmentMapper {

    public List<JudgmentDto> selectJudgmentList(Map<String, Object> params);

    public int deleteJudgment(Map<String, Object> map);

    public List<JudgmentSetupDto> selectJudgmentSetupList(Map<String, Object> params);

    public int deleteJudgmentCondition(Map<String, Object> params);

    public int insertJudgment(JudgmentDto judgment);

    public int insertJudgmentCondition(JudgmentConditionDetailDto condition);

    public int insertJudgmentError(JudgmentErrorDetailDto error);

    public Map<String, Object> selectJudgmentPurchase(Long eoId);

    public Map<String, Object> selectUserChoisePsr(Map<String, Object> params);

}
