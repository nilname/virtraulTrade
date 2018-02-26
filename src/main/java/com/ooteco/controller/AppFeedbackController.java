package com.ooteco.controller;

import com.ooteco.utils.ResultFactory;
import com.ooteco.model.AppFeedback;
import com.ooteco.page.PageBean;
import com.ooteco.service.AppFeedbackService;
import com.ooteco.swagger.JsonResult;
import com.ooteco.utils.IpUtil;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by shenyu on 2018/02/13.
 */
@Api(value = "/app/feedback", description = "app问题反馈相关接口")
@RestController
@RequestMapping(value = "/app/feedback")
public class AppFeedbackController extends AbstractController{

    private static Logger log = Logger.getLogger(AppFeedbackController.class);

    @Autowired
    private AppFeedbackService appFeedbackService;

    @ApiOperation(value = "查询问题反馈", notes = "查询问题反馈")
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
    public PageBean<AppFeedback> findList(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize){
        return appFeedbackService.findList(pageNum, pageSize);
    }

    @ApiOperation(value = "新增问题反馈", notes = "新增问题反馈")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content", value = "反馈内容", required = true, dataType = "string", paramType="query", defaultValue = "有Bug呦~"),
            @ApiImplicitParam(name = "callway", value = "联系方式", required = true, dataType = "string", paramType="query", defaultValue = "callme@gmail.com")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
    public JsonResult add(@RequestParam("content") String content, @RequestParam("callway") String callway, HttpServletRequest request){
        AppFeedback appFeedback = new AppFeedback();
        appFeedback.setContent(content);
        appFeedback.setCallway(callway);
        appFeedback.setIp(IpUtil.getIpAddr(request));
        appFeedback.setInputtime(System.currentTimeMillis());
        return appFeedbackService.add(appFeedback);
    }

}
