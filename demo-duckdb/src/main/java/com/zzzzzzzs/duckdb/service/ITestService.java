package com.zzzzzzzs.duckdb.service;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rstyro
 * @since 2022-03-25
 */
public interface ITestService extends IService<String> {
    Float getAreaFromJson();
}
