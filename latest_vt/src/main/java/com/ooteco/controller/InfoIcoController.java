package com.ooteco.controller;

import com.ooteco.model.InfoIco;
import com.ooteco.service.UploaderService;
import com.ooteco.utils.Base64Image;
import com.ooteco.utils.ResultFactory;
import com.ooteco.page.PageBean;
import com.ooteco.service.InfoIcoService;
import com.ooteco.swagger.JsonResult;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Result;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public String find(@RequestParam("id") int id){
        return renderResult(infoIcoService.find(id));
    }

    @ApiOperation(value = "新增ico资讯", notes = "新增ico资讯")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "标题", required = true, dataType = "string", paramType="query", defaultValue = "Make Bitcoin Great Again"),
            @ApiImplicitParam(name = "cover", value = "封面", required = true, dataType = "string", paramType="query"),
            @ApiImplicitParam(name = "content", value = "内容", required = true, dataType = "string", paramType="query", defaultValue = "We are merely implementing the proposals recommended by the bitcoin community. Only when we put talk into action can we make bitcoin great again..."),
            @ApiImplicitParam(name = "coinType", value = "币种", required = true, dataType = "string", paramType="query", defaultValue = "ETH"),
            @ApiImplicitParam(name = "coinGet", value = "已筹集个数", required = true, dataType = "double", paramType="query", defaultValue = "1000"),
            @ApiImplicitParam(name = "coinTarget", value = "目标个数", required = true, dataType = "double", paramType="query", defaultValue = "2000"),
            @ApiImplicitParam(name = "endtime", value = "到期时间", required = true, dataType = "string", paramType="query", defaultValue = "2018-03-01 17:50:00")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG),
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
    public String add(@RequestParam("title") String title, @RequestParam("cover") String cover, @RequestParam("content") String content, @RequestParam("coinType") String coinType, @RequestParam("coinGet") Double coinGet, @RequestParam("coinTarget") Double coinTarget, @RequestParam("endtime") String endtime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        InfoIco infoIco = new InfoIco();
        infoIco.setTitle(title);
        infoIco.setCover(cover);
        infoIco.setContent(content);
        infoIco.setCoinType(coinType);
        infoIco.setCoinGet(coinGet);
        infoIco.setCoinTarget(coinTarget);
        try {
            infoIco.setEndtime(sdf.parse(endtime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        infoIco.setInputtime(new Date());
        return renderResult(infoIcoService.add(infoIco));
    }

}
