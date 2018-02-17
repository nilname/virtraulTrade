package com.ooteco.service.impl;

import com.github.pagehelper.PageHelper;
import com.ooteco.mapper.InfomationMapper;
import com.ooteco.model.QuickNews;
import com.ooteco.page.PageBean;
import com.ooteco.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fangqing on 2/10/18.
 */
@Service
public class InfoServiceNewsImpl extends BaseInfoService {
    @Autowired
    InfomationMapper infomationMapper;

    @Override
    public List<QuickNews> getInfoAll() {
        return infomationMapper.getallinfo();
    }

    @Override
    public PageBean<QuickNews> getInfo(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        List<QuickNews> getallinfo = infomationMapper.getallinfo();
        return new PageBean<QuickNews>(getallinfo);
    }

    @Override
    public List<QuickNews> getInfoBySource(String source) {
        return infomationMapper.getInfoBySource(source);
    }

    @Override
    public List<QuickNews> getInfoByDate(String date) {
        return infomationMapper.getInfoByDate(date);
    }

}
