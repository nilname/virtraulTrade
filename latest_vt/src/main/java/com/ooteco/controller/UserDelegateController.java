package com.ooteco.controller;

import com.ooteco.model.DelegateTrade;
import com.ooteco.model.ext.UserKnockdownTradeExt;
import com.ooteco.page.PageBean;
import com.ooteco.service.TokenService;
import com.ooteco.service.UserDelegateTradeService;
import com.ooteco.service.impl.KnockdownTradeServiceImpl;
import com.ooteco.utils.ResultFactory;
import com.ooteco.utils.StringUtil;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by xule on 2018/3/6.
 */
@Api(value = "/app/delegate", description = "用户委托交易相关接口")
@RestController
@RequestMapping(value = "/app/delegate")
public class UserDelegateController {

    private static Logger log = Logger.getLogger(UserAccountController.class);

    @Autowired
    private UserDelegateTradeService userDelegateTradeService;

    @Autowired
    private KnockdownTradeServiceImpl knockdownTradeService;



    @ApiOperation(value = "查询当前委托", notes = "查询当前委托")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号", required = true, dataType = "int", paramType="query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "长度", required = true, dataType = "int", paramType="query", defaultValue = "10"),
            @ApiImplicitParam(name = "token", value = "登录加密信息", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isCanceled", value = "交易是否取消", required = true, dataType = "boolean", paramType="query"),
            @ApiImplicitParam(name = "statuIds", value = "委托状态", required = true, dataType = "string", paramType="query"),
            @ApiImplicitParam(name = "tradeType", value = "交易类型", dataType = "int", paramType="query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/findCurrentData", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public PageBean<DelegateTrade> findCurrentData(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize,DelegateTrade delegateTrade){
        return userDelegateTradeService.findDelegateTradeData(pageNum,pageSize,delegateTrade);
    }

    @ApiOperation(value = "查询历史委托", notes = "查询历史委托")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号", required = true, dataType = "int", paramType="query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "长度", required = true, dataType = "int", paramType="query", defaultValue = "10"),
            @ApiImplicitParam(name = "token", value = "登录加密信息", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isCanceled", value = "交易是否取消", required = true, dataType = "boolean", paramType="query"),
            @ApiImplicitParam(name = "statuIds", value = "委托状态", dataType = "string", paramType="query"),
            @ApiImplicitParam(name = "tradeType", value = "交易类型", dataType = "int", paramType="query"),
            @ApiImplicitParam(name = "operateType", value = "操作类型", dataType = "int", paramType="query"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/findHistoryTradeData", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public PageBean<DelegateTrade> findHistoryTradeData(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize,DelegateTrade delegateTrade){
        return userDelegateTradeService.findHistoryDelegateTrade(pageNum,pageSize,delegateTrade);
    }


    @ApiOperation(value = "查询订单详情", notes = "查询订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号", required = true, dataType = "int", paramType="query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "长度", required = true, dataType = "int", paramType="query", defaultValue = "10"),
            @ApiImplicitParam(name = "token", value = "登录加密信息", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "订单id", required = true, dataType = "int", paramType="query"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/selectTradeDetail", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public PageBean<UserKnockdownTradeExt> findHistoryTradeData(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, String token, Integer id){
        String data = new TokenService().decryptToken(token);
        if(StringUtil.isEmpty(data)) {
            log.error(String.format("user account,login log,token error"));
            return null;
        }
        int userId = Integer.parseInt(data);
        return knockdownTradeService.selectTradeDetail(pageNum, pageSize, userId, id);
    }
}
