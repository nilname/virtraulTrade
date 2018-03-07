package com.ooteco.service.impl;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.github.pagehelper.PageHelper;
import com.ooteco.mapper.UserAccountMapper;
import com.ooteco.mapper.UserLoginMapper;
import com.ooteco.model.UserAccount;
import com.ooteco.model.UserLogin;
import com.ooteco.service.IpAddressService;
import com.ooteco.utils.NonceStrUtil;
import com.ooteco.utils.ResultFactory;
import com.ooteco.mapper.ext.UserAccountExtMapper;
import com.ooteco.mapper.ext.UserLoginExtMapper;
import com.ooteco.page.PageBean;
import com.ooteco.service.SmsService;
import com.ooteco.service.TokenService;
import com.ooteco.service.UserAccountService;
import com.ooteco.swagger.JsonResult;
import com.ooteco.utils.MD5Util;
import com.ooteco.utils.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by shenyu on 2018/02/12.
 */
@Service(value = "userAccountService")
public class UserAccountServiceImpl implements UserAccountService{

    private static Logger log = Logger.getLogger(UserAccountServiceImpl.class);
    private static String SMS_REGIST_EXTIME = "sms:regist:extime:";
    private static String SMS_REGIST_CODE = "sms:regist:code:";

    private static String SMS_RESETPWD_EXTIME = "sms:resetpwd:extime:";
    private static String SMS_RESETPWD_CODE = "sms:resetpwd:code:";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Autowired
    private UserLoginMapper userLoginMapper;

    @Autowired
    private UserAccountExtMapper userAccountExtMapper;

    @Autowired
    private UserLoginExtMapper userLoginExtMapper;

    @Autowired
    private SmsService smsService;

    @Autowired
    private IpAddressService ipAddressService;

    @Override
    public JsonResult sendRegistSms(String mobile) {
        String exKey = SMS_REGIST_EXTIME + mobile;
        String codeKey = SMS_REGIST_CODE + mobile;
        //判断是否已过限制时间
        if(stringRedisTemplate.hasKey(exKey)) {
            Long expire = stringRedisTemplate.getExpire(exKey, TimeUnit.SECONDS);
            log.error(String.format("user account,regist sms,undue,second:%s", expire));
            return ResultFactory.newFail(ResultFactory.NOAUTH, String.valueOf(expire));
        }
        //用户是否存在
        if(userAccountExtMapper.isExistByMobile(mobile)!=null) {
            log.error(String.format("user account,regist sms,mobile existed,mobile:%s", mobile));
            return ResultFactory.newFail("用户已存在");
        }
        //发送短信验证码
        String smsCode = String.valueOf((int)((Math.random()*9+1)*100000));
        try {
            SendSmsResponse sendSmsResponse = smsService.sendSms(mobile, smsCode);
            if(!sendSmsResponse.getCode().equals("OK")) {
                log.error(String.format("user account,regist sms,code:%s,msg:%s", sendSmsResponse.getCode(), sendSmsResponse.getMessage()));
                return ResultFactory.newFail(sendSmsResponse.getMessage());
            }

            //缓存短信验证码(有效期5分钟)
            stringRedisTemplate.opsForValue().set(exKey, String.valueOf(System.currentTimeMillis()).trim());
            stringRedisTemplate.expire(exKey, 60 , TimeUnit.SECONDS);
            stringRedisTemplate.opsForValue().set(codeKey, smsCode.trim());
            stringRedisTemplate.expire(codeKey, 60 * 5 , TimeUnit.SECONDS);
            log.info(String.format("user account,regist sms,mobile:%s,smsCode:%s", mobile, smsCode));
            return ResultFactory.succ();
        } catch (ServerException e) {
            //e.printStackTrace();
            log.error(String.format("user account,regist sms,exception:%s", e));
            return ResultFactory.newException("ServerException");
        } catch (ClientException e) {
            //e.printStackTrace();
            log.error(String.format("user account,regist sms,exception:%s", e));
            return ResultFactory.newException("ClientException");
        }
    }

    @Override
    public JsonResult sendResetPwdSms(String mobile) {
        String exKey = SMS_RESETPWD_EXTIME + mobile;
        String codeKey = SMS_RESETPWD_CODE + mobile;
        //判断是否已过限制时间
        if(stringRedisTemplate.hasKey(exKey)) {
            Long expire = stringRedisTemplate.getExpire(exKey, TimeUnit.SECONDS);
            log.error(String.format("user account,resetpwd sms,undue,second:%s", expire));
            return ResultFactory.newFail(ResultFactory.NOAUTH, String.valueOf(expire));
        }
        //用户是否存在
        if(userAccountExtMapper.isExistByMobile(mobile)==null) {
            log.error(String.format("user account,resetpwd sms,mobile not exist,mobile:%s", mobile));
            return ResultFactory.newFail("用户不存在");
        }
        //发送短信验证码
        String smsCode = String.valueOf((int)((Math.random()*9+1)*100000));
        try {
            SendSmsResponse sendSmsResponse = smsService.sendSms(mobile, smsCode);
            if(!sendSmsResponse.getCode().equals("OK")) {
                log.error(String.format("user account,resetpwd sms,code:%s,msg:%s", sendSmsResponse.getCode(), sendSmsResponse.getMessage()));
                return ResultFactory.newFail(sendSmsResponse.getMessage());
            }
            //缓存短信验证码(有效期5分钟)
            stringRedisTemplate.opsForValue().set(exKey, String.valueOf(System.currentTimeMillis()).trim());
            stringRedisTemplate.expire(exKey, 60 , TimeUnit.SECONDS);
            stringRedisTemplate.opsForValue().set(codeKey, smsCode.trim());
            stringRedisTemplate.expire(codeKey, 60 * 5 , TimeUnit.SECONDS);
            log.info(String.format("user account,resetpwd sms,mobile:%s,smsCode:%s", mobile, smsCode));
            return ResultFactory.succ();
        } catch (ServerException e) {
            //e.printStackTrace();
            log.error(String.format("user account,resetpwd sms,exception:%s", e));
            return ResultFactory.newException("ServerException");
        } catch (ClientException e) {
            //e.printStackTrace();
            log.error(String.format("user account,resetpwd sms,exception:%s", e));
            return ResultFactory.newException("ClientException");
        }
    }

    @Override
    public JsonResult regist(String mobile, String password, String smsCode, String ip) {
        //校验短信验证码
        String exKey = SMS_REGIST_EXTIME + mobile;
        String codeKey = SMS_REGIST_CODE + mobile;
        String cacheCode = stringRedisTemplate.opsForValue().get(codeKey);
        if(StringUtil.isEmpty(cacheCode) || !smsCode.equals(cacheCode)) {
            log.error(String.format("user account,regist,smsCode error,mobile:%s,smsCode:%s", smsCode, smsCode));
            return ResultFactory.newFail("验证码输入错误");
        }
        //用户是否存在
        if(userAccountExtMapper.isExistByMobile(mobile)!=null) {
            log.error(String.format("user account,regist sms,account existed,mobile:%s", mobile));
            return ResultFactory.newFail("手机号已存在");
        }
        //删除缓存
        stringRedisTemplate.delete(exKey);
        stringRedisTemplate.delete(codeKey);
        //生成邀请码 (随机7位)
        int invitecode = 0;
        do{
            invitecode = (int)((Math.random()*9+1)*1000000);
        }while (userAccountExtMapper.selectIdByInvitecode(invitecode)!=null);

        //新增账号
        UserAccount account = new UserAccount();
        account.setMobile(mobile);
        account.setPassword(MD5Util.encode(MD5Util.encode(password)));
        account.setNickname(NonceStrUtil.createUserName());  //昵称
        account.setInvitecode(invitecode);
        account.setRegisttime(new Date());
        if(userAccountMapper.insertSelective(account)!=1) {
            log.error(String.format("user account,regist,insert,mobile:%s,ip:%s", mobile, ip));
            return ResultFactory.exception();
        }
        log.info(String.format("user account,regist,mobile:%s,ip:%s", mobile, ip));
        return ResultFactory.succ();
    }

    @Override
    public JsonResult login(String mobile, String password, String ip) {
        //用户是否存在
        if(userAccountExtMapper.isExistByMobile(mobile)==null) {
            log.error(String.format("user account,login,mobile not existed,mobile:%s", mobile));
            return ResultFactory.newFail("手机号输入错误");
        }
        //密码是否正确
        UserAccount account = userAccountExtMapper.selectByMobileAndPassword(mobile, MD5Util.encode(MD5Util.encode(password)));
        if(account==null) {
            log.error(String.format("user account,login,err pwd,mobile:{},ip:{}", mobile, ip));
            return ResultFactory.newFail("密码输入错误");
        }
        //登录日志
        UserLogin login = new UserLogin();
        login.setUserid(account.getId());
        login.setLoginip(ip);
        login.setLogintime(new Date());
        Map<String, String> address = ipAddressService.getAddress(ip);
        if(address!=null) {
            login.setCountry(address.get("country"));
            login.setProvince(address.get("subdivision"));
            login.setCity(address.get("city"));
        }
        if(userLoginMapper.insertSelective(login)!=1) {
            log.error(String.format("user account,login,log,mobile:%s,ip:%s", mobile, ip));
            return ResultFactory.exception();
        }
        //生成登录态
        String token = "";
        try {
            token = new TokenService().encrypt(account.getId(), System.currentTimeMillis());
        } catch (Exception e) {
            //e.printStackTrace();
            log.error(String.format("user account,login,create token,mobile:%s,clientid:%s,ip:%s", mobile, ip));
            return ResultFactory.exception();
        }
        log.info(String.format("user account,login,token:%s,mobile:%s,ip:%s", token, mobile, ip));

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("token", token);
        dataMap.put("user", account);
        return ResultFactory.newSucc(dataMap);
    }

    @Override
    public JsonResult resetPassword(String mobile, String password, String smsCode, String ip) {
        //校验短信验证码
        String exKey = SMS_RESETPWD_EXTIME + mobile;
        String codeKey = SMS_RESETPWD_CODE + mobile;
        String cacheCode = stringRedisTemplate.opsForValue().get(codeKey);
        if(StringUtil.isEmpty(cacheCode) || !smsCode.equals(cacheCode)) {
            log.error(String.format("user account,resetpwd,smsCode error,mobile:%s,smsCode:%s", smsCode, smsCode));
            return ResultFactory.newFail("短信验证码错误");
        }
        //删除缓存
        stringRedisTemplate.delete(exKey);
        stringRedisTemplate.delete(codeKey);
        //重置密码
        if(userAccountExtMapper.updatePasswordByMobile(mobile, MD5Util.encode(MD5Util.encode(password)))!=1) {
            log.error(String.format("user account,resetpwd,mobile:%s,ip:%s", mobile, ip));
            return ResultFactory.exception();
        }
        log.info(String.format("user account,resetpwd,mobile:%s,ip:%s", mobile, ip));
        return ResultFactory.succ();
    }

    @Override
    public JsonResult editPassword(int userId, String password, String newPassword, String smsCode, String ip) {
        UserAccount account = userAccountExtMapper.selectByIdAndPassword(userId, MD5Util.encode(MD5Util.encode(password)));
        if(account==null) {
            log.error(String.format("user account,editpass,account is null,userid:%s,ip:%s", userId, ip));
            return ResultFactory.newFail("旧密码输入错误");
        }
        //校验短信验证码
        String mobile = account.getMobile();
        String exKey = SMS_RESETPWD_EXTIME + mobile;
        String codeKey = SMS_RESETPWD_CODE + mobile;
        String cacheCode = stringRedisTemplate.opsForValue().get(codeKey);
        if(StringUtil.isEmpty(cacheCode) || !smsCode.equals(cacheCode)) {
            log.error(String.format("user account,resetpwd,smsCode error,mobile:%s,smsCode:%s", smsCode, smsCode));
            return ResultFactory.newFail("验证码输入错误");
        }
        //校验新密码是否与旧密码相同
        if(password.equals(newPassword)) {
            log.error(String.format("user account,editpass,pwd equals,userid:%s,ip:%s", userId, ip));
            return ResultFactory.newFail("新密码不能与旧密码相同");
        }
        account.setId(userId);
        account.setPassword(MD5Util.encode(MD5Util.encode(newPassword)));
        if(userAccountMapper.updateByPrimaryKeySelective(account)!=1) {
            log.error(String.format("user account,editpass,userid:%s,ip:%s", userId, ip));
            return ResultFactory.exception();
        }
        log.info(String.format("user account,editpass,userid:%s,ip:%s", userId, ip));
        return ResultFactory.succ();
    }

    @Override
    public JsonResult editInviter(int userId, int invitecode) {
        //根据邀请码获取邀请人id
        Integer inviter = userAccountExtMapper.selectIdByInvitecode(invitecode);
        //判断邀请码是否为自己
        if(invitecode==userId) {
            log.error(String.format("user account,editinviter,don't invite self,userid:%s,invitecode:%s", userId, invitecode));
            return ResultFactory.newFail("不能输入自己的邀请码");
        }
        //判断邀请码是否存在
        if(inviter==null) {
            log.error(String.format("user account,editinviter,invitecode not exist,userid:%s,invitecode:%s", userId, invitecode));
            return ResultFactory.newFail("邀请码不存在");
        }
        UserAccount account = new UserAccount();
        account.setId(userId);
        account.setInviter(inviter);
        if(userAccountMapper.updateByPrimaryKeySelective(account)!=1) {
            log.error(String.format("user account,editinviter,userid:%s,invitecode:%s", userId, invitecode));
            return ResultFactory.exception();
        }
        log.info(String.format("user account,editinviter,userid:%s,invitecode:%s", userId, invitecode));
        return ResultFactory.succ();
    }

    @Override
    public PageBean<UserLogin> loginLog(int userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageBean<>(userLoginExtMapper.selectAllByUserId(userId));
    }

    @Override
    public JsonResult editNickname(int userId, String nickName) {
        UserAccount account = new UserAccount();
        account.setId(userId);
        account.setNickname(nickName);
        if(userAccountMapper.updateByPrimaryKeySelective(account)!=1) {
            log.error(String.format("user account,edit,userid:%s,nickname:%s", userId, nickName));
            return ResultFactory.exception();
        }
        log.info(String.format("user account,edit,userid:%s,nickname:%s", userId, nickName));
        return ResultFactory.succ();
    }

    @Override
    public JsonResult editAvatar(int userId, String avatar) {
        UserAccount account = new UserAccount();
        account.setId(userId);
        account.setAvatar(avatar);
        if(userAccountMapper.updateByPrimaryKeySelective(account)!=1) {
            log.error(String.format("user account,edit,userid:%s,avatar:%s", userId, avatar));
            return ResultFactory.exception();
        }
        log.info(String.format("user account,edit,userid:%s,avatar:%s", userId, avatar));
        return ResultFactory.succ();
    }
}
