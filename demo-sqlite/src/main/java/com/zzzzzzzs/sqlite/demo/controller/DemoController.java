package com.zzzzzzzs.sqlite.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zzzzzzzs.sqlite.demo.entity.User;
import com.zzzzzzzs.sqlite.demo.service.IUserService;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private IUserService userService;

    @GetMapping("/save")
    public Object save(Long id){
        userService.save(new User().setId(id).setAge(10).setName("rstyro").setCreateTime(System.currentTimeMillis()+""));
        System.out.println("save");
        return "success";
    }

    @GetMapping("/get")
    public Object get(Long id){
        User user = userService.getById(id);
        System.out.println("user="+user);
        System.out.println("get");
        return user;
    }

    @GetMapping("/getall")
    public Object getall(){
        List<User> user = userService.list();
        return user;
    }

    @GetMapping("/insert")
    public Object insert(){
        User user = new User().setAge(10).setName("rstyro").setCreateTime(System.currentTimeMillis()+"");
        userService.insertUser(user);
        return "success";
    }

}
