package top.omoms.service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import top.omoms.beans.common.ResultBean;
import top.omoms.beans.dto.LoginDTO;
import top.omoms.beans.dto.RegisterDTO;
import top.omoms.beans.entity.User;
import top.omoms.beans.vo.LoginStatusVO;
import top.omoms.constants.AuthConstants;
import top.omoms.enums.RetCodeEnum;
import top.omoms.mapper.UserMapper;
import top.omoms.utils.CommonUtils;
import top.omoms.utils.PBKDF2Utils;
import top.omoms.utils.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @program: omom
 * @description: 认证服务
 * @author: yuanshuai
 * @create: 2023-11-24 13:02
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {


    private final HttpServletRequest request;

    private final UserMapper userMapper;

    private final RedisUtil redisUtil;


    /**
     * 登录
     *
     * @param dto DTO
     * @return {@link ResultBean}<{@link Object}>
     */
    public ResultBean<Object> login(LoginDTO dto) {

        User userInfo = new LambdaQueryChainWrapper<>(userMapper)
                .eq(User::getUsername, dto.getUsername())
                .or()
                .eq(User::getPhone, dto.getUsername())
                .last("LIMIT 1")
                .one();

        if (null == userInfo) {
            return new ResultBean<>(RetCodeEnum.STATUS_ERROR, "账号或密码错误", null);
        }

        boolean flag = PBKDF2Utils.verifyPassword(dto.getPassword(), userInfo.getSalt(), userInfo.getPassword());
        if (Boolean.FALSE.equals(flag)) {
            return new ResultBean<>(RetCodeEnum.STATUS_ERROR, "账号或密码错误", null);
        }

        String uuid = CommonUtils.getUUID();
        // 存缓存
        redisUtil.setEx(uuid, CommonUtils.GSON.toJson(userInfo), 30, TimeUnit.MINUTES);

        return new ResultBean<>(RetCodeEnum.SUCCESS, "登录成功", uuid);

    }

    /**
     * 登录状态
     *
     * @return {@link ResultBean}<{@link Object}>
     */
    public ResultBean<Object> loginStatus() {
        String token = request.getHeader(AuthConstants.AUTHORIZATION);
        if (StringUtils.isBlank(token)) {
            return new ResultBean<>(RetCodeEnum.SUCCESS, "获取成功", new LoginStatusVO(false));
        }

        User user = redisUtil.get(token, User.class);
        if (null == user) {
            return new ResultBean<>(RetCodeEnum.SUCCESS, "获取成功", new LoginStatusVO(false));
        }

        return new ResultBean<>(RetCodeEnum.SUCCESS, "获取成功", new LoginStatusVO(true));
    }

    /**
     * 注册
     *
     * @param dto DTO
     * @return {@link ResultBean}<{@link Object}>
     */
    public ResultBean<Object> register(RegisterDTO dto) {
        // 验证短信验证码

        // 验证手机号是否已经注册
        User userWithPhone = new LambdaQueryChainWrapper<>(userMapper)
                .eq(User::getPhone, dto.getPhone())
                .last("LIMIT 1")
                .one();
        if (null != userWithPhone) {
            return new ResultBean<>(RetCodeEnum.STATUS_ERROR, "该手机号已经注册", null);
        }

        // 验证用户是否存在
        User userInfo = new LambdaQueryChainWrapper<>(userMapper)
                .eq(User::getUsername, dto.getUsername())
                .last("LIMIT 1")
                .one();

        if (null != userInfo) {
            return new ResultBean<>(RetCodeEnum.STATUS_ERROR, "用户名重复，请重新输入", null);
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPhone(dto.getPhone());
        user.setCreateTime(new Date());
        user.setSalt(PBKDF2Utils.generateSalt());
        user.setPassword(PBKDF2Utils.hashPassword(dto.getPassword(), user.getSalt()));

        int flag = userMapper.insert(user);
        if (flag > 0) {
            return new ResultBean<>(RetCodeEnum.SUCCESS, "注册成功", null);
        }

        return new ResultBean<>(RetCodeEnum.STATUS_ERROR, "注册失败，请重试", null);

    }
}
