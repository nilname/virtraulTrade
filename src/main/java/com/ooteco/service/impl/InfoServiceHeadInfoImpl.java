package com.ooteco.service.impl;

import com.github.pagehelper.PageHelper;
import com.ooteco.mapper.InfomationMapper;
import com.ooteco.model.FirstHeadNews;
import com.ooteco.page.PageBean;
import com.ooteco.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fangqing on 2/10/18.
 */
@Service
public class InfoServiceHeadInfoImpl extends BaseInfoService {
    @Autowired
    InfomationMapper infomationMapper;


    @Override
    public PageBean<FirstHeadNews> getInfo(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageBean<FirstHeadNews>(infomationMapper.getHeadFirstInfo());
    }

    @Override
    public List<FirstHeadNews> getInfoBySource(String source) {
        return infomationMapper.getHeadFirstInfoBySource(source);
    }

    @Override
    public List<FirstHeadNews> getInfoByDate(String date) {
        return infomationMapper.getInfoHeadFirstByDate(date);
    }

    @Override
    public List<FirstHeadNews> getInfoAll() {
        return infomationMapper.getInfoHeadFirstAll();
    }
}
