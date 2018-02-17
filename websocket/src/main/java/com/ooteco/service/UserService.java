package com.ooteco.service;

import com.ooteco.model.User;
import com.ooteco.page.PageBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zk on 2018/2/2.
 */
public interface UserService {

    @Transactional
    int addUser(User user);

    PageBean<User> findAllUser(int pageNum, int pageSize);
}
