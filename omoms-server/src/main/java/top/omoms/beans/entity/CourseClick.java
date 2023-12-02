package top.omoms.beans.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class CourseClick {


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
     * 点击量
     */
    private Integer clickCount;

    /**
     * 第一次点击时间
     */
    private Date firstClickTime;

    /**
     * 最后一次点击时间
     */
    private Date lastClickTime;

}
