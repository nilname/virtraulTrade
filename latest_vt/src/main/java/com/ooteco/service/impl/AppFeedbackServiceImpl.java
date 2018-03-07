package com.ooteco.service.impl;

import com.github.pagehelper.PageHelper;
import com.ooteco.mapper.AppFeedbackMapper;
import com.ooteco.model.AppFeedback;
import com.ooteco.utils.ResultFactory;
import com.ooteco.mapper.ext.AppFeedbackExtMapper;
import com.ooteco.page.PageBean;
import com.ooteco.service.AppFeedbackService;
import com.ooteco.swagger.JsonResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

@Service(value = "appFeedbackService")
public class AppFeedbackServiceImpl implements AppFeedbackService{

    private static Logger log = Logger.getLogger(AppFeedbackServiceImpl.class);

    private static String ADD_COUNT = "app:feedback:addcount:";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AppFeedbackMapper appFeedbackMapper;

    @Autowired
    private AppFeedbackExtMapper appFeedbackExtMapper;

    @Override
    public PageBean<AppFeedback> findList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageBean<>(appFeedbackExtMapper.selectAll());
    }

    @Override
    public JsonResult add(AppFeedback appFeedback) {
        String exKey = ADD_COUNT + appFeedback.getIp();
        //判断是否已超过当天限制
        if(stringRedisTemplate.hasKey(exKey) && Integer.parseInt(stringRedisTemplate.opsForValue().get(exKey)) >= 10) {
            log.error("app feedback,add,out of size today");
            return ResultFactory.newFail(ResultFactory.NOAUTH, "问题反馈次数超出限制");
        }
        if(appFeedbackMapper.insertSelective(appFeedback)!=1) {
            log.error("app feedback,add");
            return ResultFactory.fail();
        }
        if(!stringRedisTemplate.hasKey(exKey)) {
            stringRedisTemplate.opsForValue().set(exKey, "1");
        }else {
            stringRedisTemplate.opsForValue().increment(exKey, 1);
        }
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        int extime = Integer.parseInt(String.valueOf((cal.getTime().getTime() - new Date().getTime()) / 1000));
        stringRedisTemplate.expire(exKey, extime , TimeUnit.SECONDS);
        log.info("app feedback,add");
        return ResultFactory.succ();
    }
}
