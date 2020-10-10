package com.sola.usercenter.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sola.usercenter.domain.entity.user.User;
import com.sola.usercenter.service.user.IUserService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 分享 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-09-20
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/{id}")
    public User userTest(@PathVariable("id") Integer userId) {

        log.info("请求 。。..");
        User user = userService.getById(userId);

        return user;
    }

}
