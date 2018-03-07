package com.ooteco.controller;

import com.ooteco.model.FirstHeadNews;
import com.ooteco.model.FirstHeadNewsDetails;
import com.ooteco.model.NiurenNews;
import com.ooteco.model.QuickNews;
import com.ooteco.page.PageBean;
import com.ooteco.service.impl.InfoServiceHeadInfoDetail;
import com.ooteco.service.impl.InfoServiceHeadInfoImpl;
import com.ooteco.service.impl.InfoServiceNewsImpl;
import com.ooteco.service.impl.InfoServiceTwitterAndWeiboImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by fangqing on 2/10/18.
 */
@RestController
public class NewsController {
    @Autowired
    InfoServiceHeadInfoDetail infoServiceHeadInfoDetail;

    @Autowired
    InfoServiceNewsImpl infoServiceNews;

    @Autowired
    InfoServiceHeadInfoImpl infoServiceHeadInfo;
    @Autowired
    InfoServiceTwitterAndWeiboImpl infoServiceTwitterAndWeibo;

    @ApiOperation(value = "获取快讯", notes = "分页获取快讯")
    @RequestMapping(value = "/getInfo/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public PageBean<QuickNews> getInfos(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        PageBean<QuickNews> result = infoServiceNews.getInfo(pageNum, pageSize);
        return result;
    }

    @ApiOperation(value = "获取全部快讯", notes = "获取全部快讯不分页方式返回")
    @RequestMapping(value = "/getInfoAll", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public List<QuickNews> getInfos() {
        return infoServiceNews.getInfoAll();
    }

    @ApiOperation(value = "按照信息源获取快讯", notes = "信息源有 币世界、金色财经")
    @RequestMapping(value = "/getInfoBySource/{Source}", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public List<QuickNews> getInfosBysource(@PathVariable("Source") String source) {
        return infoServiceNews.getInfoBySource(source);
    }

    @ApiOperation(value = "按日期获取快讯", notes = "获取指定日期的快讯信息，日期格式为如：2018-02-11")
    @RequestMapping(value = "/getInfoByDate/{date}", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public List<QuickNews> getInfosByDate(@PathVariable("date") String date) {
        return infoServiceNews.getInfoByDate(date);
    }


    ////////////---牛人说接口----------
    @ApiOperation(value = "按日期获取牛人资讯", notes = "获取指定日期的牛人资讯，日期格式为如：2018-02-11")
    @RequestMapping(value = "/getInfoNiuRenByDate/{date}", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public List<NiurenNews> getInfosNiurenByDate(@PathVariable("date") String date) {
        return infoServiceTwitterAndWeibo.getInfoByDate(date);
    }

    @ApiOperation(value = "按照信息源获取牛人资讯", notes = "牛人资讯信息源有 weibo、twitter")
    @RequestMapping(value = "/getInfoNiuRenBySource/{Source}", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public List<NiurenNews> getInfosNiurenBysource(@PathVariable("Source") String source) {
        return infoServiceTwitterAndWeibo.getInfoBySource(source);
    }

    @ApiOperation(value = "分页获取牛人资讯", notes = "分页方式返回")
    @RequestMapping(value = "/getInfoNiuRen/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public PageBean<NiurenNews> getInfosNiuRen(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        return infoServiceTwitterAndWeibo.getInfo(pageNum, pageSize);

    }

    @ApiOperation(value = "获取全部牛人说资讯", notes = "获取全部牛人说资讯不分页方式返回")
    @RequestMapping(value = "/getInfoNiuRenAll", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public List<NiurenNews> getInfosNiurenAll() {
        return infoServiceTwitterAndWeibo.getInfoAll();
    }

    /////////////--------头条接口-----------


    @ApiOperation(value = "分页获取头条资讯", notes = "分页方式返回")
    @RequestMapping(value = "/getInfoHeadFirst/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public PageBean<FirstHeadNews> getInfosHeadFirst(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {

        return infoServiceHeadInfo.getInfo(pageNum, pageSize);
    }

    @ApiOperation(value = "获取全部头条资讯", notes = "获取全部头条资讯不分页方式返回")
    @RequestMapping(value = "/getInfoHeadFirstAll", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public List<FirstHeadNews> getInfosHeadFirstAll() {
        return infoServiceHeadInfo.getInfoAll();
    }

    @ApiOperation(value = "按日期获取头条资讯", notes = "获取指定日期的头条资讯，日期格式为如：2018-02-11")
    @RequestMapping(value = "/getInfoHeadFirstByDate/{date}", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public List<FirstHeadNews> getInfosHeadFirstByDate(@PathVariable("date") String date) {
        return infoServiceHeadInfo.getInfoByDate(date);
    }

    @ApiOperation(value = "按标题获取头条资讯详情")
    @RequestMapping(value = "/getInfoHeadFirstDetail/{title}", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public List<FirstHeadNewsDetails> getInfosHeadFirstDetailByTitle(@PathVariable("title") String title) {
        return infoServiceHeadInfoDetail.getHeadFirstByTitle(title);
    }
}


