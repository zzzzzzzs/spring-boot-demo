package com.zzzzzzzs.sqlite.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzzzzzzs.sqlite.demo.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {

    void insertUser(@Param("user") User user);
}
