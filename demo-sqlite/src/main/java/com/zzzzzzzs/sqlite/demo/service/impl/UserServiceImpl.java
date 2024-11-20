package com.zzzzzzzs.sqlite.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.zzzzzzzs.sqlite.demo.entity.User;
import com.zzzzzzzs.sqlite.demo.mapper.UserMapper;
import com.zzzzzzzs.sqlite.demo.service.IUserService;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rstyro
 * @since 2022-03-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    UserMapper userMapper;

    @Override
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }
}
