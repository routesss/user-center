package com.sola.usercenter.controller.user;


import com.alibaba.fastjson.JSONObject;
import com.sola.usercenter.domain.entity.user.User;
import com.sola.usercenter.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 分享 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-09-20
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/test")
    public String userTest(){

        List<User> allUser = userService.getAllUser();
        System.out.println(JSONObject.toJSONString(allUser));

        return "OK";
    }

}
