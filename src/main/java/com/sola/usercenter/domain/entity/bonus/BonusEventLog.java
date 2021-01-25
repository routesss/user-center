package com.sola.usercenter.domain.entity.bonus;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 积分变更记录表
 * </p>
 *
 * @author jobob
 * @since 2021-01-20
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class BonusEventLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * user.id
     */
    private Integer userId;

    /**
     * 积分操作值
     */
    private Integer value;

    /**
     * 发生的事件
     */
    private String event;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 描述
     */
    private String description;

}
