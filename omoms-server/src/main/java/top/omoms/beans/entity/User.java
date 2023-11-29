package top.omoms.beans.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @program: omom
 * @description: 用户实体
 * @author: yuanshuai
 * @create: 2023-11-23 17:25
 **/
@Data
public class User {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 电话
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * salt
     */
    private String salt;

    /**
     * 创建时间
     */
    private Date createTime;

}
