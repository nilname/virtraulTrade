package com.ooteco.service;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSONObject;
import com.ooteco.swagger.JsonResult;
import com.ooteco.utils.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 极光推送
 * Created by shenyu on 2018/02/13.
 */
@Service
public class JPushService {
    private static Logger log = LoggerFactory.getLogger(JPushService.class);

    @Value("${jpush.appkey}")
    private String appKey;
    @Value("${jpush.mastersecret}")
    private String masterSecret;


    /**
     * 推送内容 目标：所有平台，所有用户
     * @return
     */
    public JsonResult pushAll(JSONObject jsonObject) {
        final JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());
        final PushPayload payload = buildPushAll(jsonObject);
        if (payload == null) {
            log.error("jpush,payload is empty");
            return ResultFactory.newFail("推送消息创建失败，请检查内容对象！");
        }
        try {
            PushResult result = jpushClient.sendPush(payload);
            if (result.statusCode != 0) {
                log.error("jpush,errmsg:{}", result);
                return ResultFactory.newFail("推送失败！errmsg:" + result);
            }
            log.info("jpush,msgid:{}", result.msg_id);
            return ResultFactory.newSucc(result.msg_id);
        } catch (APIConnectionException e) {
            log.error("push APIConnectionException:{}", e);
            return ResultFactory.exception();
        } catch (APIRequestException e) {
            log.error("push APIRequestException,status:{},errcode:{},errmsg:{}", e.getStatus(), e.getErrorCode(), e.getErrorMessage());
            return ResultFactory.exception();
        }
    }

    private PushPayload buildPushAll(JSONObject jsonObject) {
        Map<String, String> extras = new HashMap<String, String>();
        Set<String> keySet = jsonObject.keySet();
        for(String key : keySet) {
            extras.put(key, jsonObject.get(key).toString());
        }
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.all()).setNotification(Notification.newBuilder().setAlert(jsonObject.getString("content"))
                // Android
                .addPlatformNotification(AndroidNotification.newBuilder().setTitle(jsonObject.getString("title")).addExtras(extras).build())
                // IOS
                .addPlatformNotification(IosNotification.newBuilder().incrBadge(1).addExtras(extras).build()).build()).build();
        return payload;
    }

}
