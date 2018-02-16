package com.ooteco.controller;

import com.ooteco.model.User;
import com.ooteco.page.PageBean;
import com.ooteco.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zk on 2018/2/2.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private static Logger log = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ApiOperation(value = "添加用户", notes = "添加用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
    public int addUser(User user) {
        log.info("添加用户!");
        return userService.addUser(user);
    }

    @ApiOperation(value = "获取用户列表", notes = "分页查询用户信息列表")
    @ResponseBody
    @RequestMapping(value = "/all/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public PageBean<User> findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        return userService.findAllUser(pageNum, pageSize);
    }
}
