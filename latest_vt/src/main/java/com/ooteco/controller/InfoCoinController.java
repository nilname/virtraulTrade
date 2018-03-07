package com.ooteco.controller;

import com.ooteco.model.InfoCoin;
import com.ooteco.model.InfoCoinLink;
import com.ooteco.model.InfoCoinParam;
import com.ooteco.utils.ResultFactory;
import com.ooteco.service.InfoCoinService;
import com.ooteco.swagger.JsonResult;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shenyu on 2018/02/13.
 */
@Api(value = "/info/coin", description = "资讯-币资料相关接口")
@RestController
@RequestMapping(value = "/info/coin")
public class InfoCoinController extends AbstractController{

    private static Logger log = Logger.getLogger(InfoCoinController.class);

    @Autowired
    private InfoCoinService infoCoinService;

    @ApiOperation(value = "查询币资料", notes = "查询币资料")
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/findAll", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public String findList(){
        JsonResult jsonResult = infoCoinService.findAll();
        return renderResult(jsonResult);
    }

    @ApiOperation(value = "根据id查询币资料", notes = "根据id查询币资料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "int", paramType="query", defaultValue = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/find", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public String find(@RequestParam("id") int id){
        return renderResult(infoCoinService.find(id));
    }

    @ApiOperation(value = "新增币资料", notes = "新增币资料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nameEn", value = "英文名称", required = true, dataType = "string", paramType="query", defaultValue = "ETC"),
            @ApiImplicitParam(name = "nameCn", value = "中文名称", required = true, dataType = "string", paramType="query", defaultValue = "以太经典"),
            @ApiImplicitParam(name = "desc", value = "描述", required = true, dataType = "string", paramType="query", defaultValue = "以太币（ETH）是以太坊（Ethereum）的一种数字代币，被视为“比特币2.0版”，采用与比特币不同的区块链技术“以太坊”（Ethereum），开发者们需要支付以太币（ETH）来支撑应用的运行。和其他数字货币一样，以太币可以在交易平台上进行买卖。"),
            @ApiImplicitParam(name = "param1", value = "参数1", required = true, dataType = "string", paramType="query", defaultValue = "ETC"),
            @ApiImplicitParam(name = "param2", value = "参数n...", required = true, dataType = "string", paramType="query", defaultValue = "param"),
            @ApiImplicitParam(name = "link1", value = "链接1", required = true, dataType = "string", paramType="query", defaultValue = "https://www.ethereum.org/"),
            @ApiImplicitParam(name = "link2", value = "链接n...", required = true, dataType = "string", paramType="query", defaultValue = "link")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
    public String add(@RequestParam("nameEn") String nameEn, @RequestParam("nameCn") String nameCn, @RequestParam("desc") String desc, @RequestParam("param1") String param1, @RequestParam("link1") String link1, @RequestParam("param2") String param2, @RequestParam("link2") String link2){
        InfoCoin infoCoin = new InfoCoin();
        infoCoin.setNameEn(nameEn);
        infoCoin.setNameCn(nameCn);
        infoCoin.setDesc(desc);
        infoCoin.setInputtime(new Date());

        List<InfoCoinParam> paramList = new ArrayList<InfoCoinParam>();
        InfoCoinParam infoCoinParam1 = new InfoCoinParam();
        infoCoinParam1.setParam(param1);
        paramList.add(infoCoinParam1);
        InfoCoinParam infoCoinParam2 = new InfoCoinParam();
        infoCoinParam2.setParam(param2);
        paramList.add(infoCoinParam2);

        List<InfoCoinLink> linkList = new ArrayList<InfoCoinLink>();
        InfoCoinLink infoCoinLink1 = new InfoCoinLink();
        infoCoinLink1.setLink(link1);
        linkList.add(infoCoinLink1);
        InfoCoinLink infoCoinLink2 = new InfoCoinLink();
        infoCoinLink2.setLink(link2);
        linkList.add(infoCoinLink2);

        infoCoin.setParamList(paramList);
        infoCoin.setLinkList(linkList);
        return renderResult(infoCoinService.add(infoCoin));
    }

}
