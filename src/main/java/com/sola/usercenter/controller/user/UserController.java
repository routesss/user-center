package com.sola.usercenter.controller.user;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.sola.usercenter.sentinel.resource.block.handeler.TestBlockHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sola.usercenter.domain.entity.user.User;
import com.sola.usercenter.service.user.IUserService;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping(value = "/{id}")
    @SentinelResource(value = "getUser")
    public User userTest(@PathVariable("id") Integer userId) {

        log.info("请求 。。..");
        User user = userService.getById(userId);

        return user;
    }

    @GetMapping(value = "/testTracsactionInsert")
    public User testTransactionInsert(){
        User user = new User();
        user.setWxNickname("test " + System.currentTimeMillis());
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        return userService.testTransactionInsert(user);
    }

    @GetMapping(value = "/queryUser")
    @SentinelResource(value = "queryUser", blockHandlerClass = TestBlockHandler.class, blockHandler = "blockHandler")
    public List<User> queryUser(User user) {

        return userService.getUser(user);
    }

    /*@GetMapping(value = "/queryUser")
    public List<User> queryUser(User user) {

        Entry entry = null ;
        List<User> userList = new ArrayList<>();

        String resourceName = "queryUser";

        //资源名 来源名称
        ContextUtil.enter(resourceName, "userCenterBJ");

        try {
            //指定sentinel保护的资源 名称是queryUser
            entry = SphU.entry(resourceName);
            userList = userService.getUser(user);
        } catch (BlockException e) {
            e.printStackTrace();
        }catch (IllegalArgumentException e){
            //统计其他异常
            Tracer.trace(e);
        }finally {
            if(entry != null){
                entry.exit();
            }
            ContextUtil.exit();
        }
        return userList;
    }*/

}
