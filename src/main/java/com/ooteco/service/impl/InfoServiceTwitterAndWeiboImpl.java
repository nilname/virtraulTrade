package com.ooteco.service.impl;

import com.github.pagehelper.PageHelper;
import com.ooteco.mapper.InfomationMapper;
import com.ooteco.model.NiurenNews;
import com.ooteco.page.PageBean;
import com.ooteco.service.InfoService;
import com.sun.xml.internal.rngom.parse.host.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fangqing on 2/10/18.
 */
@Service
public class InfoServiceTwitterAndWeiboImpl extends BaseInfoService {
    @Autowired
    InfomationMapper infomationMapper;

    @Override
    public PageBean<NiurenNews> getInfo(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageBean<NiurenNews>(infomationMapper.getInfoNiuren());
    }

    @Override
    public List<NiurenNews> getInfoBySource(String source) {
        return infomationMapper.getInfoNiurenBySource(source);
    }

    @Override
    public List<NiurenNews> getInfoByDate(String date) {
        return infomationMapper.getInfoNiurenByDate(date);
    }

    @Override
    public List<NiurenNews> getInfoAll() {
        return infomationMapper.getInfNiurenoAll();
    }
}
