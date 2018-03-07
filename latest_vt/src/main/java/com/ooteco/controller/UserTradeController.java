package com.ooteco.controller;

import com.ooteco.constant.MarketDepthType;
import com.ooteco.exceptions.CustomException;
import com.ooteco.exceptions.SymbolParseException;
import com.ooteco.model.UserDelegateTrade;
import com.ooteco.service.TokenService;
import com.ooteco.service.UserTradeService;
import com.ooteco.swagger.JsonResult;
import com.ooteco.utils.ResultFactory;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("app/user/trade")
public class UserTradeController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserTradeService userTradeService;

    @ApiOperation(value = "取消委托", notes = "取消委托")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录加密信息", required = true, dataType = "string", paramType="form"),
            @ApiImplicitParam(name = "id", value = "委托订单id", required = true, dataType = "integer", paramType="form")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @PostMapping("cancel")
    public JsonResult cancel(String token, Integer id) throws SymbolParseException{
        if(token == null){
            return ResultFactory.newFail("token 不能为空");
        }
        Integer userId = tokenService.decryptTokenUserId(token);
        if(userId == null){
            return ResultFactory.newFail("token解析错误");
        }
        if(id == null){
            return ResultFactory.newFail("id不能为空");
        }
        try {
            userTradeService.cancel(id);
        } catch (CustomException e) {
            return ResultFactory.newFail(e.getMessage());
        }
        return ResultFactory.succ();
    }


    @ApiOperation(value = "订单委托", notes = "订单委托")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录加密信息", required = true, dataType = "string", paramType="form"),
            @ApiImplicitParam(name = "quantity", value = "委托数量", required = true, dataType = "double", paramType="double"),
            @ApiImplicitParam(name = "operateType", value = "操作类型,1-买入 2-卖出(默认买入)", dataType = "integer", paramType="form"),
            @ApiImplicitParam(name = "price", value = "委托价格", required = true, dataType = "double", paramType="form"),
            @ApiImplicitParam(name = "tradeType", value = "交易类型id", required = true, dataType = "integer", paramType="form")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @PostMapping
    public JsonResult trade(UserDelegateTrade delegateTrade, String token) throws SymbolParseException{
        if(token == null){
            return ResultFactory.newFail("token 不能为空");
        }
        Integer userId = tokenService.decryptTokenUserId(token);
        if(userId == null){
            return ResultFactory.newFail("token解析错误");
        }
        if(delegateTrade.getQuantity() == null){
            return ResultFactory.newFail("交易数量不能为空");
        }
        if(delegateTrade.getPrice() == null){
            return ResultFactory.newFail("交易价格不能为空");
        }
        delegateTrade.setUserId(userId);
        if(delegateTrade.getOperateType() == null){
            delegateTrade.setOperateType(MarketDepthType.asks.ordinal());
        }
        delegateTrade.setAmount(delegateTrade.getPrice().multiply(delegateTrade.getQuantity()));
        try {
            userTradeService.handleDelegateTrade(delegateTrade);
        } catch (CustomException e) {
            return ResultFactory.newFail(e.getMessage());
        }
        return ResultFactory.succ();
    }
}
