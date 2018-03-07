package com.ooteco.mapper;

import com.ooteco.model.InfoNews;
import com.ooteco.model.InfoNewsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InfoNewsMapper {
    int countByExample(InfoNewsExample example);

    int deleteByExample(InfoNewsExample example);

    int insert(InfoNews record);

    int insertSelective(InfoNews record);

    List<InfoNews> selectByExampleWithBLOBs(InfoNewsExample example);

    List<InfoNews> selectByExample(InfoNewsExample example);

    int updateByExampleSelective(@Param("record") InfoNews record, @Param("example") InfoNewsExample example);

    int updateByExampleWithBLOBs(@Param("record") InfoNews record, @Param("example") InfoNewsExample example);

    int updateByExample(@Param("record") InfoNews record, @Param("example") InfoNewsExample example);
}