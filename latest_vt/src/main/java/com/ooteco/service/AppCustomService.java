package com.ooteco.service;

import com.ooteco.model.AppCustom;
import com.ooteco.page.PageBean;
import com.ooteco.swagger.JsonResult;

/**
 * Created by shenyu on 2018/02/13.
 */
public interface AppCustomService {

    PageBean<AppCustom> findList(int pageNum, int pageSize);

    JsonResult add(AppCustom appCustom);
}
