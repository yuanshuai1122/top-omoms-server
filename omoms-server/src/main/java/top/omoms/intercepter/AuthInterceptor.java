package top.omoms.intercepter;

import top.omoms.beans.common.ResultBean;
import top.omoms.beans.entity.User;
import top.omoms.constants.AuthConstants;
import top.omoms.enums.RetCodeEnum;
import top.omoms.utils.CommonUtils;
import top.omoms.utils.RedisUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @program: omom
 * @description: 登录认证拦截器
 * @author: yuanshuai
 * @create: 2023-11-24 12:53
 **/
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final RedisUtil redisUtil;


    /***
     * 在请求处理之前进行调用(Controller方法调用之前)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取token
        String token = request.getHeader(AuthConstants.AUTHORIZATION);
        if (StringUtils.isBlank(token)) {
            returnNoLogin(response);
            return false;
        }
        // 从redis中拿token对应user
        User user = redisUtil.get(token, User.class);
        if (null == user) {
            returnNoLogin(response);
            return false;
        }
        // token续期
        return redisUtil.expire(token, 30, TimeUnit.MINUTES);
    }

    /***
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /***
     * 整个请求结束之后被调用，也就是在DispatchServlet渲染了对应的视图之后执行（主要用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 返回未登录的错误信息
     * @param response ServletResponse
     */
    private void returnNoLogin(HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        // 设置返回401 和响应编码
        response.setStatus(401);
        response.setContentType("Application/json;charset=utf-8");
        // 构造返回响应体
        ResultBean<Object> resultBean = new ResultBean<>(RetCodeEnum.TOKEN_ERROR, "登录已失效，请重新登录", null);
        String resultString = CommonUtils.GSON.toJson(resultBean);
        outputStream.write(resultString.getBytes(StandardCharsets.UTF_8));
    }

}
