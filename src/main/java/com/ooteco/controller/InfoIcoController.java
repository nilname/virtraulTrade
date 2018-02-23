package com.ooteco.controller;

import com.ooteco.utils.ResultFactory;
import com.ooteco.model.InfoIco;
import com.ooteco.page.PageBean;
import com.ooteco.service.InfoIcoService;
import com.ooteco.swagger.JsonResult;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shenyu on 2018/02/13.
 */
@Api(value = "/info/ico", description = "资讯ico相关接口")
@RestController
@RequestMapping(value = "/info/ico")
public class InfoIcoController extends AbstractController{

    private static Logger log = Logger.getLogger(InfoIcoController.class);

    @Autowired
    private InfoIcoService infoIcoService;

    @ApiOperation(value = "查询ico资讯", notes = "查询ico资讯")
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
    public PageBean<InfoIco> findList(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize){
        return infoIcoService.findList(pageNum, pageSize);
    }


    @ApiOperation(value = "根据id查询ico资讯", notes = "根据id查询ico资讯")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "int", paramType="query", defaultValue = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/find", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public JsonResult find(@RequestParam("id") int id){
        return infoIcoService.find(id);
    }

    @ApiOperation(value = "新增ico资讯", notes = "新增ico资讯")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "标题", required = true, dataType = "string", paramType="query", defaultValue = "Make Bitcoin Great Again"),
            @ApiImplicitParam(name = "cover", value = "封面", required = true, dataType = "string", paramType="query", defaultValue = "D://image_1.png"),
            @ApiImplicitParam(name = "content", value = "内容", required = true, dataType = "string", paramType="query", defaultValue = "We are merely implementing the proposals recommended by the bitcoin community. Only when we put talk into action can we make bitcoin great again..."),
            @ApiImplicitParam(name = "coinType", value = "币种", required = true, dataType = "string", paramType="query", defaultValue = "ETH"),
            @ApiImplicitParam(name = "coinTarget", value = "目标个数", required = true, dataType = "double", paramType="query", defaultValue = "2000"),
            @ApiImplicitParam(name = "endtime", value = "到期时间", required = true, dataType = "long", paramType="query", defaultValue = "1518456310527")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
    public JsonResult add(@RequestParam("title") String title, @RequestParam("cover") String cover, @RequestParam("content") String content, @RequestParam("coinType") String coinType, @RequestParam("coinTarget") Double coinTarget, @RequestParam("endtime") Long endtime){
        InfoIco infoIco = new InfoIco();
        infoIco.setTitle(title);
        infoIco.setCover(cover);
        infoIco.setContent(content);
        infoIco.setCoinType(coinType);
        infoIco.setCoinTarget(coinTarget);
        infoIco.setEndtime(endtime);
        infoIco.setInputtime(System.currentTimeMillis());
        return infoIcoService.add(infoIco);
    }

}
