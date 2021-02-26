package com.sola.usercenter.controller.user;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;
import com.sola.usercenter.annotation.Authentication;
import com.sola.usercenter.domain.entity.user.User;
import com.sola.usercenter.domain.vo.user.LoginUserResponseVo;
import com.sola.usercenter.domain.vo.user.LoginUserVo;
import com.sola.usercenter.sentinel.resource.block.handeler.TestBlockHandler;
import com.sola.usercenter.service.user.IUserService;
import com.sola.usercenter.util.JwtOperator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "用户控制器")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private JwtOperator jwtOperator;

    @GetMapping(value = "/{id}")
    @SentinelResource(value = "getUser")
    public User userTest(@PathVariable("id") Integer userId) {

        log.info("请求 。。..");
        User user = userService.getById(userId);

        return user;
    }

    @GetMapping(value = "/testTracsactionInsert")
    public User testTransactionInsert() {
        User user = new User();
        user.setWxNickname("test " + System.currentTimeMillis());
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        return userService.testTransactionInsert(user);
    }

    @Authentication
    @GetMapping(value = "/queryUser")
    @SentinelResource(value = ".", blockHandlerClass = TestBlockHandler.class, blockHandler = "blockHandler")
    public List<User> queryUser(User user) {

        return userService.getUser(user);
    }

    @ApiOperation(value = "/login", notes = "登录测试接口")
    @PostMapping(value = "/login")
    @ApiImplicitParam(name = "loginUserVo", value = "登录用户信息", required = true, dataType = "LoginUserVo",
        paramType = "body")
    public LoginUserResponseVo login(@RequestBody LoginUserVo loginUserVo) {
        User param = new User();
        BeanUtils.copyProperties(loginUserVo, param);
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>(param);
        User user = this.userService.getOne(lambdaQueryWrapper);

        if (user == null) {
            // 插入数据
            User build = User.builder().roles("user").bonus(0).wxId(param.getWxId()).wxNickname("测试")
                .createTime(LocalDateTime.now()).updateTime(LocalDateTime.now()).build();
            this.userService.save(build);
        }
        // 生成token

        HashMap<String, Object> claims = Maps.newHashMap();
        claims.put("wxId", param.getWxId());
        claims.put("wxNickname", param.getWxNickname());
        String token = jwtOperator.generateToken(claims);

        return LoginUserResponseVo.builder().token(token).build();
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
