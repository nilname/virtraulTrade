package com.ooteco.mapper;

import com.ooteco.model.AppCustom;

public interface AppCustomMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppCustom record);

    int insertSelective(AppCustom record);

    AppCustom selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppCustom record);

    int updateByPrimaryKeyWithBLOBs(AppCustom record);

    int updateByPrimaryKey(AppCustom record);
}