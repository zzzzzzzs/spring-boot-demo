package com.zzzzzzzs.sqlite.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzzzzzzs.sqlite.demo.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rstyro
 * @since 2022-03-25
 */
public interface IUserService extends IService<User> {
    void insertUser(User user);
}
