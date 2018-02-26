package com.ooteco.mapper;

import com.ooteco.model.InfoIco;

public interface InfoIcoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InfoIco record);

    int insertSelective(InfoIco record);

    InfoIco selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InfoIco record);

    int updateByPrimaryKeyWithBLOBs(InfoIco record);

    int updateByPrimaryKey(InfoIco record);
}