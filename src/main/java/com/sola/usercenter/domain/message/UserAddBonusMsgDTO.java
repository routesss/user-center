package com.sola.usercenter.domain.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAddBonusMsgDTO {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 积分
     */
    private Integer bonus;
}
