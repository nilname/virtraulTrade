package com.ooteco.service.impl;

import com.ooteco.mapper.UserIdentityMapper;
import com.ooteco.model.UserIdentity;
import com.ooteco.utils.ResultFactory;
import com.ooteco.mapper.ext.UserIdentityExtMapper;
import com.ooteco.service.UserIdentityService;
import com.ooteco.swagger.JsonResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by shenyu on 2018/02/13.
 */
@Service(value = "userIdentityService")
public class UserIdentityServiceImpl implements UserIdentityService {

    private static Logger log = Logger.getLogger(UserIdentityServiceImpl.class);

    @Autowired
    private UserIdentityMapper userIdentityMapper;

    @Autowired
    private UserIdentityExtMapper userIdentityExtMapper;

    @Override
    public JsonResult findByUserId(int userId) {
        UserIdentity userIdentity = userIdentityExtMapper.selectByUserId(userId);
        if(userIdentity==null) {
            log.error(String.format("user identity,find,identity is null,userid:%s", userId));
            return ResultFactory.fail();
        }
        log.info(String.format("user identity,find,userid:%s", userId));
        return ResultFactory.newSucc(userIdentity);
    }

    @Override
    public JsonResult add(UserIdentity userIdentity) {
        userIdentity.setInputtime(new Date());
        if(userIdentityMapper.insertSelective(userIdentity)!=1) {
            log.error(String.format("user identity,add,userid:%s", userIdentity.getUserid()));
            return ResultFactory.fail();
        }
        log.info(String.format("user identity,add,userid:%s", userIdentity.getUserid()));
        return ResultFactory.succ();
    }

    @Override
    public JsonResult editState(int userId, Byte state) {
        UserIdentity userIdentity = new UserIdentity();
        userIdentity.setUserid(userId);
        userIdentity.setState(state);
        if(userIdentityExtMapper.updateStateByUserId(userIdentity)!=1) {
            log.error(String.format("user identity,edit state,userid:%s,state:%s", userIdentity.getUserid(), userIdentity.getState()));
            return ResultFactory.fail();
        }
        log.info(String.format("user identity,add,edit state,userid:%s", userIdentity.getUserid(), userIdentity.getState()));
        return ResultFactory.succ();
    }
}
