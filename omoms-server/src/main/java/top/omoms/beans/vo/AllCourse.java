package top.omoms.beans.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class AllCourse {


    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;


    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 浏览量
     */
    private Integer clickCount;


}
