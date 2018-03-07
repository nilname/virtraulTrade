package com.ooteco.controller;


import com.ooteco.feign.MarketMainService;
import com.ooteco.model.ext.MarketInfoMain;
import com.ooteco.utils.ResultFactory;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(value = "/app/market", description = "app市场行情相关接口")
@RestController
@RequestMapping(value = "/app/market")

public class MarketController extends AbstractController{
    private static Logger logger = Logger.getLogger(MarketController.class);

    @Autowired
    private MarketMainService marketMainService;

    @ApiOperation(value = "市场行情", notes = "app市场行情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录加密信息", required = false, dataType = "string", paramType="form"),
            @ApiImplicitParam(name = "platform", value = "平台", required = true, dataType = "string", paramType="form")

    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/market", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public List<MarketInfoMain> market(@RequestParam("token") String token, @RequestParam("platform") String platform){


        return marketMainService.getMarketData(token,platform);

    }
}
