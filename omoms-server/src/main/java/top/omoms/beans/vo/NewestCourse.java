package top.omoms.beans.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class NewestCourse {


    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 封面图
     */
    private String cover;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 浏览量
     */
    private Integer clickCount;


}
