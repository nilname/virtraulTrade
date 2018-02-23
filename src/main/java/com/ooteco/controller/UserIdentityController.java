package com.ooteco.controller;

import com.ooteco.utils.ResultFactory;
import com.ooteco.model.UserIdentity;
import com.ooteco.service.TokenService;
import com.ooteco.service.UserIdentityService;
import com.ooteco.swagger.JsonResult;
import com.ooteco.utils.StringUtil;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shenyu on 2018/02/13.
 */
@Api(value = "/user/identity", description = "用户身份相关接口")
@RestController
@RequestMapping(value = "/user/identity")
public class UserIdentityController extends AbstractController{

    private static Logger log = Logger.getLogger(UserIdentityController.class);

    @Autowired
    private UserIdentityService userIdentityService;

    @ApiOperation(value = "查询身份信息", notes = "查询身份信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录加密信息", required = true, dataType = "string", paramType = "query", defaultValue="2zkZeTpuspyn4polQgHxqQ==")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/find", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public String find(@RequestParam("token") String token){
        String data = new TokenService().decryptToken(token);
        if(StringUtil.isEmpty(data)) {
            log.error(String.format("user identity,find,token error"));
            return renderResult(ResultFactory.newFail(ResultFactory.NOAUTH, "权限校验失败"));
        }
        int userId = Integer.parseInt(data);
        JsonResult jsonResult = userIdentityService.findByUserId(userId);
        return renderResult(jsonResult);
    }

    @ApiOperation(value = "新增身份信息", notes = "新增身份信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录加密信息", required = true, dataType = "string", paramType = "query", defaultValue = "2zkZeTpuspyn4polQgHxqQ=="),
            @ApiImplicitParam(name = "realname", value = "真实姓名", required = true, dataType = "string", paramType = "query", defaultValue = "张三"),
            @ApiImplicitParam(name = "idcard", value = "身份证号", required = true, dataType = "string", paramType = "query", defaultValue = "610322189910224361"),
            @ApiImplicitParam(name = "img_id1", value = "身份证-正面", required = true, dataType = "string", paramType = "query", defaultValue = "D://image_1.png"),
            @ApiImplicitParam(name = "img_id2", value = "身份证-反面", required = true, dataType = "string", paramType = "query", defaultValue = "D://image_2.png"),
            @ApiImplicitParam(name = "img_id3", value = "身份证-手持", required = true, dataType = "string", paramType = "query", defaultValue = "D://image_3.png")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public String add(@RequestParam("token") String token, @RequestParam("realname") String realname, @RequestParam("idcard") String idcard, @RequestParam("img_id1") String img_id1, @RequestParam("img_id2") String img_id2, @RequestParam("img_id3") String img_id3){
        String data = new TokenService().decryptToken(token);
        if(StringUtil.isEmpty(data)) {
            log.error(String.format("user identity,add,token error"));
            return renderResult(ResultFactory.newFail(ResultFactory.NOAUTH, "权限校验失败"));
        }
        int userId = Integer.parseInt(data);
        UserIdentity userIdentity = new UserIdentity();
        userIdentity.setUserid(userId);
        userIdentity.setRealname(realname);
        userIdentity.setIdcard(idcard);
        userIdentity.setImgId1(img_id1);
        userIdentity.setImgId2(img_id2);
        userIdentity.setImgId3(img_id3);
        JsonResult jsonResult = userIdentityService.add(userIdentity);
        return renderResult(jsonResult);
    }

    @ApiOperation(value = "修改身份状态", notes = "修改身份状态（管理员操作）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户唯一标识", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "state", value = "状态码(0正在审核1审核通过2审核未通过)", required = true, dataType = "byte", paramType = "query", defaultValue = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/editState", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public String editState(@RequestParam("userId") int userId, @RequestParam("state") Byte state){
        JsonResult jsonResult = userIdentityService.editState(userId, state);
        return renderResult(jsonResult);
    }
}
