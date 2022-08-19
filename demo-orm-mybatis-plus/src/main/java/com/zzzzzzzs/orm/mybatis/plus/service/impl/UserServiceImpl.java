package com.zzzzzzzs.orm.mybatis.plus.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzzzzzzs.orm.mybatis.plus.entity.User;
import com.zzzzzzzs.orm.mybatis.plus.mapper.UserMapper;
import com.zzzzzzzs.orm.mybatis.plus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * User Service
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-08 18:10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    // 参数需要前端传入
    @Override
    public void sqlPageSelect(int pageNo, int pageSize) {
        Page<User> page = new Page<>(pageNo, pageSize);
        userMapper.sqlPageSelect(page);
    }
}
