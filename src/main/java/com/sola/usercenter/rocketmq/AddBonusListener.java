package com.sola.usercenter.rocketmq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sola.usercenter.domain.entity.bonus.BonusEventLog;
import com.sola.usercenter.domain.entity.user.User;
import com.sola.usercenter.domain.message.UserAddBonusMsgDTO;
import com.sola.usercenter.service.bonus.IBonusEventLogService;
import com.sola.usercenter.service.user.IUserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = "consumer-group", topic = "add-bount")
public class AddBonusListener implements RocketMQListener<UserAddBonusMsgDTO> {

    @Autowired
    private IUserService userService;
    @Autowired
    private IBonusEventLogService bonusEventLogService;

    @Override
    public void onMessage(UserAddBonusMsgDTO userAddBonusMsgDTO) {
        // 获取用户数据
        User user = userService.getById(userAddBonusMsgDTO.getUserId());
        if (user == null) {
            log.error("审批文章增加积分异常 : {}", JSONObject.toJSONString(userAddBonusMsgDTO));
            return;
        }
        // 增加积分
        user.setBonus(user.getBonus() + userAddBonusMsgDTO.getBonus());
        userService.updateById(user);

        // 添加bonus日志
        this.bonusEventLogService.save(BonusEventLog.builder().userId(user.getId()).value(userAddBonusMsgDTO.getBonus())
            .event("CONTRIBUTE").description("增加积分").build());

        log.info("添加积分接口调用");
    }
}
