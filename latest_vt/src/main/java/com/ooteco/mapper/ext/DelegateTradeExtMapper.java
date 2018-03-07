package com.ooteco.mapper.ext;

import com.ooteco.model.DelegateTrade;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xule on 2018/3/5.
 */
public interface DelegateTradeExtMapper {

    List<DelegateTrade> delegateTradeCurrentOrder(@Param("userId") Integer userId, @Param("isCanceled") Boolean isCanceled, @Param("status")String status, @Param("tradeType")Integer tradeType);

    List<DelegateTrade> delegateTradeHistoryOrder(@Param("userId") Integer userId, @Param("isCanceled") Boolean isCanceled, @Param("status")String status, @Param("tradeType")Integer tradeType,@Param("operateType")Integer operateType);

    List<DelegateTrade> selectById(@Param("id")Integer id);
}
