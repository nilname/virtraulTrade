package com.ooteco.controller;

import com.ooteco.utils.ResultFactory;
import com.ooteco.model.AppNews;
import com.ooteco.page.PageBean;
import com.ooteco.service.AppNewsService;
import com.ooteco.swagger.JsonResult;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shenyu on 2018/02/13.
 */
@Api(value = "/app/news", description = "app活动公告相关接口")
@RestController
@RequestMapping(value = "/app/news")
public class AppNewsController extends AbstractController{

    private static Logger log = Logger.getLogger(AppNewsController.class);

    @Autowired
    private AppNewsService appNewsService;

    @ApiOperation(value = "查询活动公告", notes = "查询活动公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号", required = true, dataType = "int", paramType="query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "长度", required = true, dataType = "int", paramType="query", defaultValue = "10")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/findList", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public PageBean<AppNews> findList(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize){
        return appNewsService.findList(pageNum, pageSize);
    }

    @ApiOperation(value = "根据id查询活动公告", notes = "根据id查询活动公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "int", paramType="query", defaultValue = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/find", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public JsonResult find(@RequestParam("id") int id){
        return appNewsService.find(id);
    }


    @ApiOperation(value = "新增活动公告", notes = "新增活动公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型(1活动2公告)", required = true, dataType = "byte", paramType="form", defaultValue = "1"),
            @ApiImplicitParam(name = "title", value = "标题", required = true, dataType = "string", paramType="form", defaultValue = "社会主义核心价值观"),
            @ApiImplicitParam(name = "content", value = "内容", required = true, dataType = "text", paramType="form", defaultValue = "富强、民主、文明、和谐")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
    public JsonResult add(@RequestParam("type") Byte type, @RequestParam("title") String title, @RequestParam("content") String content){
        AppNews appNews = new AppNews();
        appNews.setType(type);
        appNews.setTitle(title);
        appNews.setContent(content);
        appNews.setInputtime(System.currentTimeMillis());
        return appNewsService.add(appNews);
    }

}
