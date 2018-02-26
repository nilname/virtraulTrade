package com.ooteco.service;

import com.ooteco.model.InfoCoin;
import com.ooteco.swagger.JsonResult;

/**
 * Created by shenyu on 2018/02/13.
 */
public interface InfoCoinService {

    /**
     * 查询所有
     * 字典序排序
     * @return
     */
    JsonResult findAll();

    JsonResult find(int id);

    JsonResult add(InfoCoin infoCoin);
}
