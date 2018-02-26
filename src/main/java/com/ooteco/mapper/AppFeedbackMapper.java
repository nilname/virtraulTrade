package com.ooteco.mapper;

import com.ooteco.model.AppFeedback;

public interface AppFeedbackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppFeedback record);

    int insertSelective(AppFeedback record);

    AppFeedback selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppFeedback record);

    int updateByPrimaryKey(AppFeedback record);
}