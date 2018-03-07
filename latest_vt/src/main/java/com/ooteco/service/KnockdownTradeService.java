package com.ooteco.service;

import com.ooteco.model.ext.UserKnockdownTradeExt;
import com.ooteco.page.PageBean;

/**
 * Created by xule on 2018/3/6.
 */
public interface KnockdownTradeService {

    PageBean<UserKnockdownTradeExt> selectTradeDetail(int pageNum, int pageSize, Integer userId, Integer id);
}
