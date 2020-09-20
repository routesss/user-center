package com.sola.usercenter.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sola.usercenter.domain.entity.user.User;

import java.util.List;

/**
 * <p>
 * 分享 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-09-20
 */
public interface IUserService extends IService<User> {
    List<User> getAllUser();
}
