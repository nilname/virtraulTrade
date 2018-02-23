package com.ooteco.service.impl;

import com.github.pagehelper.PageHelper;
import com.ooteco.utils.ResultFactory;
import com.ooteco.mapper.InfoIcoMapper;
import com.ooteco.mapper.ext.InfoIcoExtMapper;
import com.ooteco.model.InfoIco;
import com.ooteco.page.PageBean;
import com.ooteco.service.InfoIcoService;
import com.ooteco.swagger.JsonResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "infoIcoService")
public class InfoIcoServiceImpl implements InfoIcoService{

    private static Logger log = Logger.getLogger(InfoIcoServiceImpl.class);

    @Autowired
    private InfoIcoMapper infoIcoMapper;

    @Autowired
    private InfoIcoExtMapper infoIcoExtMapper;

    @Override
    public PageBean<InfoIco> findList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageBean<>(infoIcoExtMapper.selectAll());
    }

    @Override
    public JsonResult find(int id) {
        InfoIco infoIco = infoIcoMapper.selectByPrimaryKey(id);
        if(infoIco==null) {
            return ResultFactory.fail();
        }
        return ResultFactory.newSucc(infoIco);
    }

    @Override
    public JsonResult add(InfoIco infoIco) {
        if(infoIcoMapper.insertSelective(infoIco)!=1) {
            log.error("info ico,add");
            return ResultFactory.fail();
        }
        log.info("info ico,add");
        return ResultFactory.succ();
    }
}
