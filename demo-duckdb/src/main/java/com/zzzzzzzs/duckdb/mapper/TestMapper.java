package com.zzzzzzzs.duckdb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface TestMapper extends BaseMapper<String> {

    Float getAreaFromJson();
}
