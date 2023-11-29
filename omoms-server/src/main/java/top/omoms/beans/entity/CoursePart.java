package top.omoms.beans.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class CoursePart {


    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 章节标题
     */
    private String partTitle;

    /**
     * 章节排序
     */
    private Integer partSort;

    /**
     * 章节描述
     */
    private String partDescription;

    /**
     * 章节地址
     */
    private String partUrl;


}
