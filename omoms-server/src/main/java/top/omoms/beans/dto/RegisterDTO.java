package top.omoms.beans.dto;

import lombok.Data;

/**
 * @program: omom
 * @description: 注册dto
 * @author: yuanshuai
 * @create: 2023-11-24 13:04
 **/
@Data
public class RegisterDTO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 电话
     */
    private String phone;


    /**
     * 验证码
     */
    private String verifyCode;

}
