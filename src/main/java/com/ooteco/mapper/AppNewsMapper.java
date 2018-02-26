package com.ooteco.mapper;

import com.ooteco.model.AppNews;

public interface AppNewsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppNews record);

    int insertSelective(AppNews record);

    AppNews selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppNews record);

    int updateByPrimaryKeyWithBLOBs(AppNews record);

    int updateByPrimaryKey(AppNews record);
}