package com.zzzzzzzs.duckdb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zzzzzzzs.duckdb.service.ITestService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private ITestService testService;

    @GetMapping("/get")
    public Object get() {
        return "面积：" + testService.getAreaFromJson();
    }
}
