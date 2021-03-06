package com.sola.usercenter.service.user.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sola.usercenter.domain.entity.user.User;
import com.sola.usercenter.mapper.user.UserMapper;
import com.sola.usercenter.service.user.IUserService;

/**
 * <p>
 * 分享 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-09-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private PlatformTransactionManager transactionManager;

    public List<User> getAllUser() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        return baseMapper.selectList(lambdaQueryWrapper);
    }

    public List<User> getUser(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(user.getId() != null, User::getId, user.getId())
            .eq(StringUtils.isNotEmpty(user.getWxNickname()), User::getWxNickname, user.getWxNickname());
        return baseMapper.selectList(lambdaQueryWrapper);
    }

    /**
     * 测试代码形式事务
     * 
     * @return
     */
    public User testTransactionInsert(User user) {

        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transaction = transactionManager.getTransaction(transactionDefinition);

        try {
            this.baseMapper.insert(user);
            transactionManager.commit(transaction);

        } catch (Exception e) {
            transactionManager.rollback(transaction);
            throw e;
        }

        return user;
    }
}
