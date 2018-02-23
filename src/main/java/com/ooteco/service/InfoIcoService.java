package com.ooteco.service;

import com.ooteco.model.InfoIco;
import com.ooteco.page.PageBean;
import com.ooteco.swagger.JsonResult;

/**
 * Created by shenyu on 2018/02/13.
 */
public interface InfoIcoService {

    PageBean<InfoIco> findList(int pageNum, int pageSize);

    JsonResult find(int id);

    JsonResult add(InfoIco infoIco);
}
