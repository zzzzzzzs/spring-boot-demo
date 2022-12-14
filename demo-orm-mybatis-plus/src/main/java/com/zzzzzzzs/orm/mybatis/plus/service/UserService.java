package com.zzzzzzzs.orm.mybatis.plus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzzzzzzs.orm.mybatis.plus.entity.User;

/**
 * <p>
 * User Service
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-08 18:10
 */
public interface UserService extends IService<User> {
    // 使用 sql 分页查询
    public void sqlPageSelect(int pageNo, int pageSize);
}
