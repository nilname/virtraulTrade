package com.ooteco.controller;

import com.ooteco.model.AppFeedback;
import com.ooteco.service.IpAddressService;
import com.ooteco.service.TokenService;
import com.ooteco.utils.ResultFactory;
import com.ooteco.page.PageBean;
import com.ooteco.service.AppFeedbackService;
import com.ooteco.swagger.JsonResult;
import com.ooteco.utils.StringUtil;
import com.ooteco.validator.AppFeedbackAddValidator;
import com.ooteco.validator.UserAccountLoginValidator;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

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

    @Autowired
    private IpAddressService ipAddressService;

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
            @ApiImplicitParam(name = "token", value = "登录加密信息", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "反馈内容", required = true, dataType = "string", paramType="query", defaultValue = "有Bug呦~"),
            @ApiImplicitParam(name = "callway", value = "联系方式", required = true, dataType = "string", paramType="query", defaultValue = "callme@gmail.com")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
    public String add(
            @Valid AppFeedbackAddValidator params,
            BindingResult bindingResult,
            HttpServletRequest request) {
        JsonResult validator = validator(bindingResult);
        if (ResultFactory.isFail(validator)) {
            return renderResult(validator);
        }
        String data = new TokenService().decryptToken(params.getToken());
        if (StringUtil.isEmpty(data)) {
            log.error(String.format("user account,editavatar,token error"));
            return renderResult(ResultFactory.newFail(ResultFactory.NOAUTH, "权限校验失败"));
        }
        int userId = Integer.parseInt(data);
        AppFeedback appFeedback = new AppFeedback();
        appFeedback.setUserid(userId);
        appFeedback.setContent(params.getContent());
        appFeedback.setCallway(params.getCallway());
        appFeedback.setIp(ipAddressService.getClientIp(request));
        appFeedback.setInputtime(new Date());
        return renderResult(appFeedbackService.add(appFeedback));
    }

}
