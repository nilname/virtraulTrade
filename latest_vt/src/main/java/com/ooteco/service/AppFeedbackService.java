package com.ooteco.service;

import com.ooteco.model.AppFeedback;
import com.ooteco.page.PageBean;
import com.ooteco.swagger.JsonResult;

/**
 * Created by shenyu on 2018/02/13.
 */
public interface AppFeedbackService {

    PageBean<AppFeedback> findList(int pageNum, int pageSize);

    JsonResult add(AppFeedback appFeedback);
}
