package com.ooteco.controller;

import com.ooteco.model.AppCustom;
import com.ooteco.utils.ResultFactory;
import com.ooteco.page.PageBean;
import com.ooteco.service.AppCustomService;
import com.ooteco.swagger.JsonResult;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by shenyu on 2018/02/13.
 */
@Api(value = "/app/custom", description = "app客服中心相关接口")
@RestController
@RequestMapping(value = "/app/custom")
public class AppCustomController extends AbstractController{

    private static Logger log = Logger.getLogger(AppCustomController.class);

    @Autowired
    private AppCustomService appCustomService;

    @ApiOperation(value = "查询常见问题", notes = "查询常见问题")
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
    public PageBean<AppCustom> findList(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize){
        return appCustomService.findList(pageNum, pageSize);
    }

    @ApiOperation(value = "新增常见问题", notes = "新增常见问题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "question", value = "问题", required = true, dataType = "string", paramType="query", defaultValue = "怎么实名认证？"),
            @ApiImplicitParam(name = "answer", value = "答案", required = true, dataType = "string", paramType="query", defaultValue = "我的页面-实名认证，上传指定图片后即可实名认证！")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
    public String add(@RequestParam("question") String question, @RequestParam("answer") String answer){
        AppCustom appCustom = new AppCustom();
        appCustom.setQuestion(question);
        appCustom.setAnswer(answer);
        appCustom.setInputtime(new Date());
        return renderResult(appCustomService.add(appCustom));
    }

}
