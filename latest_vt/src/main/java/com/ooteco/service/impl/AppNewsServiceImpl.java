package com.ooteco.service.impl;

import com.github.pagehelper.PageHelper;
import com.ooteco.mapper.AppNewsMapper;
import com.ooteco.model.AppNews;
import com.ooteco.utils.ResultFactory;
import com.ooteco.mapper.ext.AppNewsExtMapper;
import com.ooteco.page.PageBean;
import com.ooteco.service.AppNewsService;
import com.ooteco.swagger.JsonResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "appNewsService")
public class AppNewsServiceImpl implements AppNewsService{

    private static Logger log = Logger.getLogger(AppNewsServiceImpl.class);

    @Autowired
    private AppNewsMapper appNewsMapper;

    @Autowired
    private AppNewsExtMapper appNewsExtMapper;

    @Override
    public PageBean<AppNews> findList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageBean<>(appNewsExtMapper.selectAll());
    }

    @Override
    public JsonResult find(int id) {
        AppNews appNews = appNewsMapper.selectByPrimaryKey(id);
        if(appNews==null) {
            return ResultFactory.fail();
        }
        return ResultFactory.newSucc(appNews);
    }

    @Override
    public JsonResult add(AppNews appNews) {
        if(appNewsMapper.insertSelective(appNews)!=1) {
            log.error(String.format("app news,add,title:%s,type:%s", appNews.getTitle(), appNews.getType()));
            return ResultFactory.fail();
        }
        log.info(String.format("app news,add,title:%s,type:%s", appNews.getTitle(), appNews.getType()));
        return ResultFactory.succ();
    }
}
