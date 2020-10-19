package com.sola.usercenter.sentinel.resource.block.handeler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.sola.usercenter.domain.entity.user.User;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TestBlockHandler {
    static public List<User> blockHandler(User user, BlockException e){
        log.info("方法被限流或降级");
        return new ArrayList<>();
    }
}
