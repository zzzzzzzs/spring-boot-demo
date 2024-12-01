package com.zzzzzzzs.duckdb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzzzzzzs.duckdb.service.ITestService;
import org.springframework.stereotype.Service;
import com.zzzzzzzs.duckdb.mapper.TestMapper;

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
public class TestServiceImpl extends ServiceImpl<TestMapper, String> implements ITestService {
    @Resource
    TestMapper testMapper;

    @Override
    public Float getAreaFromJson() {
        return testMapper.getAreaFromJson();
    }
}
