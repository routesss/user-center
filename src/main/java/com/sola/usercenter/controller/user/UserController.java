package com.sola.usercenter.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sola.usercenter.controller.util.IResult;
import com.sola.usercenter.controller.util.Result;
import com.sola.usercenter.domain.entity.user.User;
import com.sola.usercenter.service.user.IUserService;

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

    @GetMapping("/{id}")
    public IResult userTest(@PathVariable("id") Integer userId) {

        User user = userService.getById(userId);

        return Result.ok(user);
    }

}
