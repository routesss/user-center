package com.sola.usercenter.service.bonus.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sola.usercenter.domain.entity.bonus.BonusEventLog;
import com.sola.usercenter.mapper.bonus.BonusEventLogMapper;
import com.sola.usercenter.service.bonus.IBonusEventLogService;

/**
 * <p>
 * 积分变更记录表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-01-20
 */
@Service
public class BonusEventLogServiceImpl extends ServiceImpl<BonusEventLogMapper, BonusEventLog>
    implements IBonusEventLogService {

}
