package com.ooteco.service.impl;

import com.github.pagehelper.PageHelper;
import com.ooteco.utils.ResultFactory;
import com.ooteco.mapper.AppFeedbackMapper;
import com.ooteco.mapper.ext.AppFeedbackExtMapper;
import com.ooteco.model.AppFeedback;
import com.ooteco.page.PageBean;
import com.ooteco.service.AppFeedbackService;
import com.ooteco.swagger.JsonResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "appFeedbackService")
public class AppFeedbackServiceImpl implements AppFeedbackService{

    private static Logger log = Logger.getLogger(AppFeedbackServiceImpl.class);

    @Autowired
    private AppFeedbackMapper appFeedbackMapper;

    @Autowired
    private AppFeedbackExtMapper appFeedbackExtMapper;

    @Override
    public PageBean<AppFeedback> findList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageBean<>(appFeedbackExtMapper.selectAll());
    }

    @Override
    public JsonResult add(AppFeedback appFeedback) {
        if(appFeedbackMapper.insertSelective(appFeedback)!=1) {
            log.error("app feedback,add");
            return ResultFactory.fail();
        }
        log.info("app feedback,add");
        return ResultFactory.succ();
    }
}
