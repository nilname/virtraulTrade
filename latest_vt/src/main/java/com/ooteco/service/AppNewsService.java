package com.ooteco.service;

import com.ooteco.model.AppNews;
import com.ooteco.page.PageBean;
import com.ooteco.swagger.JsonResult;

/**
 * Created by shenyu on 2018/02/13.
 */
public interface AppNewsService {

    PageBean<AppNews> findList(int pageNum, int pageSize);

    JsonResult find(int id);

    JsonResult add(AppNews appNews);

}
