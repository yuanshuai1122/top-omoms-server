package top.omoms.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.omoms.beans.common.ResultBean;
import top.omoms.beans.entity.User;
import top.omoms.constants.AuthConstants;
import top.omoms.enums.RetCodeEnum;
import top.omoms.utils.RedisUtil;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final RedisUtil redisUtil;

    private final HttpServletRequest request;


    /**
     * 获取登录信息
     * @return 登陆信息
     */
    public ResultBean<User> getLoginUserInfo() {
        // 获取token
        String token = request.getHeader(AuthConstants.AUTHORIZATION);
        if (StringUtils.isBlank(token)) {
            return new ResultBean<>(RetCodeEnum.TOKEN_ERROR, "登录已失效，请重新登录", null);
        }
        // 从redis中拿token对应user
        User user = redisUtil.get(token, User.class);
        if (null == user) {
            return new ResultBean<>(RetCodeEnum.TOKEN_ERROR, "登录已失效，请重新登录", null);
        }

        return new ResultBean<>(RetCodeEnum.SUCCESS, "获取成功", user);
    }

}
