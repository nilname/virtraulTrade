package com.ooteco.controller;

import com.ooteco.utils.ResultFactory;
import com.ooteco.model.UserLogin;
import com.ooteco.page.PageBean;
import com.ooteco.service.TokenService;
import com.ooteco.service.UserAccountService;
import com.ooteco.swagger.JsonResult;
import com.ooteco.utils.IpUtil;
import com.ooteco.utils.StringUtil;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by shenyu on 2018/02/12.
 */
@Api(value = "/user/account", description = "用户账号相关接口")
@RestController
@RequestMapping(value = "/user/account")
public class UserAccountController extends AbstractController{

    private static Logger log = Logger.getLogger(UserAccountController.class);

    @Autowired
    private UserAccountService userAccountService;

    @ApiOperation(value = "发送注册验证码", notes = "发送注册验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, dataType = "string", paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG),
            @ApiResponse(code = ResultFactory.NOAUTH, message = "受限，返回剩余秒数")
    })
    @ResponseBody
    @RequestMapping(value = "/sendRegistSms", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public String sendRegistSms(@RequestParam("mobile") String mobile, HttpServletRequest request){
        JsonResult jsonResult = userAccountService.sendRegistSms(mobile);
        return renderResult(jsonResult);
    }

    @ApiOperation(value = "注册", notes = "用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, dataType = "string", paramType="form"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "string", paramType="form"),
            @ApiImplicitParam(name = "smsCode", value = "短信验证码", required = true, dataType = "string", paramType="form")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG) ,
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/regist", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
    public String regist(@RequestParam("mobile") String mobile, @RequestParam("password") String password, @RequestParam("smsCode") String smsCode, HttpServletRequest request){
        JsonResult result = userAccountService.regist(mobile, password, smsCode, IpUtil.getIpAddr(request));
        return renderResult(result);
    }

    @ApiOperation(value = "登录", notes = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, dataType = "string", paramType="form"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "string", paramType="form")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG) ,
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/login", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
    public String login(@RequestParam("mobile") String mobile, @RequestParam("password") String password, HttpServletRequest request) {
        JsonResult result = userAccountService.login(mobile, password, IpUtil.getIpAddr(request));
        return renderResult(result);
    }

    @ApiOperation(value = "发送找回密码验证码", notes = "发送找回密码验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, dataType = "string", paramType="query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG),
            @ApiResponse(code = ResultFactory.NOAUTH, message = "受限，返回剩余秒数")
    })
    @ResponseBody
    @RequestMapping(value = "/sendResetPwdSms", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public String sendResetPwdSms(@RequestParam("mobile") String mobile, HttpServletRequest request){
        JsonResult jsonResult = userAccountService.sendResetPwdSms(mobile);
        return renderResult(jsonResult);
    }

    @ApiOperation(value = "找回密码", notes = "找回密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, dataType = "string", paramType="form"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "string", paramType="form"),
            @ApiImplicitParam(name = "smsCode", value = "短信验证码", required = true, dataType = "string", paramType="form")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/resetPassword", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
    public String resetPassword(@RequestParam("mobile") String mobile, @RequestParam("password") String password, @RequestParam("smsCode") String smsCode, HttpServletRequest request){
        JsonResult jsonResult = userAccountService.resetPassword(mobile, password, smsCode, IpUtil.getIpAddr(request));
        return renderResult(jsonResult);
    }

    @ApiOperation(value = "修改密码", notes = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录加密信息", required = true, dataType = "string", paramType="form"),
            @ApiImplicitParam(name = "password", value = "原密码", required = true, dataType = "string", paramType="form"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, dataType = "string", paramType="form")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/editPassword", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
    public String editPassword(@RequestParam("token") String token, @RequestParam("password") String password, @RequestParam("newPassword") String newPassword, HttpServletRequest request){
        String data = new TokenService().decryptToken(token);
        if(StringUtil.isEmpty(data)) {
            log.error(String.format("user account,editpwd,token error"));
            return renderResult(ResultFactory.newFail(ResultFactory.NOAUTH, "权限校验失败"));
        }
        int userId = Integer.parseInt(data);
        JsonResult jsonResult = userAccountService.editPassword(userId, password, newPassword, IpUtil.getIpAddr(request));
        return renderResult(jsonResult);
    }

    @ApiOperation(value = "输入邀请码", notes = "输入邀请码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录加密信息", required = true, dataType = "string", paramType="query"),
            @ApiImplicitParam(name = "invitecode", value = "邀请码", required = true, dataType = "int", paramType="query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/editInviter", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public String editInviter(@RequestParam("token") String token, @RequestParam("invitecode") int invitecode){
        String data = new TokenService().decryptToken(token);
        if(StringUtil.isEmpty(data)) {
            log.error(String.format("user account,editinviter,token error"));
            return renderResult(ResultFactory.newFail(ResultFactory.NOAUTH, "权限校验失败"));
        }
        int userId = Integer.parseInt(data);
        JsonResult jsonResult = userAccountService.editInviter(userId, invitecode);
        return renderResult(jsonResult);
    }

    @ApiOperation(value = "最近登录", notes = "最近登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录加密信息", required = true, dataType = "string", paramType="query"),
            @ApiImplicitParam(name = "pageNum", value = "页号", required = true, dataType = "int", paramType="query"),
            @ApiImplicitParam(name = "pageSize", value = "长度", required = true, dataType = "int", paramType="query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/loginLog", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public PageBean<UserLogin> loginLog(@RequestParam("token") String token, @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize){
        String data = new TokenService().decryptToken(token);
        if(StringUtil.isEmpty(data)) {
            log.error(String.format("user account,login log,token error"));
            return null;
        }
        int userId = Integer.parseInt(data);
        return userAccountService.loginLog(userId, pageNum, pageSize);
    }

    @ApiOperation(value = "修改昵称", notes = "修改昵称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录加密信息", required = true, dataType = "string", paramType="query"),
            @ApiImplicitParam(name = "nickName", value = "昵称", required = true, dataType = "string", paramType="query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/editNickname", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public String editNickname(@RequestParam("token") String token, @RequestParam("nickName") String nickName){
        String data = new TokenService().decryptToken(token);
        if(StringUtil.isEmpty(data)) {
            log.error(String.format("user account,editnickname,token error"));
            return renderResult(ResultFactory.newFail(ResultFactory.NOAUTH, "权限校验失败"));
        }
        int userId = Integer.parseInt(data);
        JsonResult jsonResult = userAccountService.editNickname(userId, nickName);
        return renderResult(jsonResult);
    }

    @ApiOperation(value = "修改头像", notes = "修改头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录加密信息", required = true, dataType = "string", paramType="query"),
            @ApiImplicitParam(name = "avatar", value = "头像地址", required = true, dataType = "string", paramType="query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/editAvatar", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public String editAvatar(@RequestParam("token") String token, @RequestParam("avatar") String avatar){
        String data = new TokenService().decryptToken(token);
        if(StringUtil.isEmpty(data)) {
            log.error(String.format("user account,editavatar,token error"));
            return renderResult(ResultFactory.newFail(ResultFactory.NOAUTH, "权限校验失败"));
        }
        int userId = Integer.parseInt(data);
        JsonResult jsonResult = userAccountService.editAvatar(userId, avatar);
        return renderResult(jsonResult);
    }
}
