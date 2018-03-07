package com.ooteco.controller;

import com.ooteco.exceptions.CustomException;
import com.ooteco.exceptions.SymbolParseException;
import com.ooteco.model.response.UserAssetResponse;
import com.ooteco.service.TokenService;
import com.ooteco.service.UserService;
import com.ooteco.swagger.JsonResult;
import com.ooteco.utils.ResultFactory;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("app/user")
public class UserController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "资产列表", notes = "资产列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录加密信息", required = true, dataType = "string", paramType="form")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @GetMapping("asset")
    public JsonResult getUserAsset(String token) throws SymbolParseException{
        if(token == null){
            return ResultFactory.newFail("token 不能为空");
        }
        Integer userId = tokenService.decryptTokenUserId(token);
        if(userId == null){
            return ResultFactory.newFail("token解析错误");
        }
        UserAssetResponse response = null;
        try {
            response = userService.getUserAsset(userId);
        } catch (CustomException e) {
            return ResultFactory.newFail(e.getMessage());
        }
        return ResultFactory.newSucc(response);
    }
}
