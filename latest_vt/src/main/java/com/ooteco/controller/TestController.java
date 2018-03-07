package com.ooteco.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ooteco.mapper.InfoNewsMapper;
import com.ooteco.model.InfoNews;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    Logger logger = Logger.getLogger(TestController.class);
    @Autowired
    private InfoNewsMapper infoNewsMapper;

    @RequestMapping("hello")
    public PageInfo<InfoNews> sayHello(){

        PageInfo<InfoNews> pageInfo = PageHelper.startPage(2, 10)
                .doSelectPageInfo(() -> infoNewsMapper.selectByExample(null));
        return pageInfo;
    }
}
