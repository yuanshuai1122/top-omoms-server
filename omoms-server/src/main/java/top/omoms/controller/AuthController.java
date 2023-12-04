package top.omoms.controller;

import top.omoms.beans.common.ResultBean;
import top.omoms.beans.dto.LoginDTO;
import top.omoms.beans.dto.RegisterDTO;
import top.omoms.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 登录认证相关接口
 *
 * @author yuanshuai
 * @date 2023/11/30
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 登录
     *
     * @param dto DTO
     * @return {@link ResultBean}<{@link Object}>
     */
    @PostMapping("/login")
    public ResultBean<Object> login(@RequestBody LoginDTO dto) {
        log.info("开始登录请求， dto：{}", dto);
        return authService.login(dto);
    }

    /**
     * 获取登录状态
     *
     * @return {@link ResultBean}<{@link Object}>
     */
    @GetMapping("/login/status")
    public ResultBean<Object> loginStatus() {

        return authService.loginStatus();
    }

    /**
     * 注册
     *
     * @param dto DTO
     * @return {@link ResultBean}<{@link Object}>
     */
    @PostMapping("/register")
    public ResultBean<Object> register(@RequestBody RegisterDTO dto) {
        log.info("用户开始注册, dto:{}", dto);

        return authService.register(dto);
    }


}
