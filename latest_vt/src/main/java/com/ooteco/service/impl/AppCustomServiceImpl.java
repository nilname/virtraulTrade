package com.ooteco.service.impl;

import com.github.pagehelper.PageHelper;
import com.ooteco.mapper.AppCustomMapper;
import com.ooteco.model.AppCustom;
import com.ooteco.utils.ResultFactory;
import com.ooteco.mapper.ext.AppCustomExtMapper;
import com.ooteco.page.PageBean;
import com.ooteco.service.AppCustomService;
import com.ooteco.swagger.JsonResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "appCustomService")
public class AppCustomServiceImpl implements AppCustomService{

    private static Logger log = Logger.getLogger(AppCustomServiceImpl.class);

    @Autowired
    private AppCustomMapper appCustomMapper;

    @Autowired
    private AppCustomExtMapper appCustomExtMapper;

    @Override
    public PageBean<AppCustom> findList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageBean<>(appCustomExtMapper.selectAll());
    }

    @Override
    public JsonResult add(AppCustom appCustom) {
        if(appCustomMapper.insertSelective(appCustom)!=1) {
            log.error("app custom,add");
            return ResultFactory.fail();
        }
        log.info("app custom,add");
        return ResultFactory.succ();
    }
}
