package com.ooteco.service.impl;

import com.alibaba.fastjson.JSON;
import com.ooteco.mapper.InfoCoinLinkMapper;
import com.ooteco.mapper.InfoCoinMapper;
import com.ooteco.mapper.InfoCoinParamMapper;
import com.ooteco.mapper.ext.InfoCoinLinkExtMapper;
import com.ooteco.mapper.ext.InfoCoinParamExtMapper;
import com.ooteco.model.InfoCoin;
import com.ooteco.model.InfoCoinLink;
import com.ooteco.model.InfoCoinParam;
import com.ooteco.utils.ResultFactory;
import com.ooteco.mapper.ext.InfoCoinExtMapper;
import com.ooteco.model.ext.InfoCoinExt;
import com.ooteco.service.InfoCoinService;
import com.ooteco.swagger.JsonResult;
import com.ooteco.utils.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service(value = "infoCoinService")
public class InfoCoinServiceImpl implements InfoCoinService{

    private static Logger log = Logger.getLogger(InfoCoinServiceImpl.class);

    @Autowired
    private InfoCoinMapper infoCoinMapper;

    @Autowired
    private InfoCoinExtMapper infoCoinExtMapper;

    @Autowired
    private InfoCoinParamMapper infoCoinParamMapper;

    @Autowired
    private InfoCoinParamExtMapper infoCoinParamExtMapper;

    @Autowired
    private InfoCoinLinkMapper infoCoinLinkMapper;

    @Autowired
    private InfoCoinLinkExtMapper infoCoinLinkExtMapper;

    @Override
    public JsonResult findAll() {
        List<InfoCoin> infoCoins = infoCoinExtMapper.selectAll();
        Map<String, InfoCoinExt> dataMap = new HashMap<String, InfoCoinExt>();
        for (InfoCoin data: infoCoins) {
            InfoCoinExt infoCoinExt = new InfoCoinExt();
            infoCoinExt.setId(data.getId());
            infoCoinExt.setNameEn(data.getNameEn());
            infoCoinExt.setNameCn(data.getNameCn());
            infoCoinExt.setLetter(StringUtil.isNotEmpty(data.getNameEn())?data.getNameEn().substring(0, 1).toUpperCase():"");
            dataMap.put(data.getNameEn(), infoCoinExt);
        }
        Collection<String> keyset = dataMap.keySet();
        List<String> list = new ArrayList<String>(keyset);
        Collections.sort(list);
        List<InfoCoinExt> dataList = new ArrayList<InfoCoinExt>();
        for (String key: list) {
            dataList.add(dataMap.get(key));
        }
        log.debug(String.format("info coin,all,size:%s", dataList.size()));
        return ResultFactory.newSucc(dataList);
    }

    @Override
    public JsonResult find(int id) {
        InfoCoin infoCoin = infoCoinMapper.selectByPrimaryKey(id);
        if(infoCoin==null) {
            return ResultFactory.fail();
        }
        infoCoin.setParamList(infoCoinParamExtMapper.selectByCoinid(id));
        infoCoin.setLinkList(infoCoinLinkExtMapper.selectByCoinid(id));
        return ResultFactory.newSucc(infoCoin);
    }

    @Override
    public JsonResult add(InfoCoin infoCoin) {
        if(infoCoinExtMapper.insertSelective(infoCoin)!=1) {
            log.error("info coin,add");
            return ResultFactory.fail();
        }
        for(InfoCoinParam param : infoCoin.getParamList()) {
            param.setCoinid(infoCoin.getId());
            if(infoCoinParamMapper.insertSelective(param)!=1) {
                log.error("info coin,add");
                return ResultFactory.fail();
            }
        }
        for(InfoCoinLink link : infoCoin.getLinkList()) {
            link.setCoinid(infoCoin.getId());
            if(infoCoinLinkMapper.insertSelective(link)!=1) {
                log.error("info coin,add");
                return ResultFactory.fail();
            }
        }
        log.info("info coin,add");
        return ResultFactory.succ();
    }
}