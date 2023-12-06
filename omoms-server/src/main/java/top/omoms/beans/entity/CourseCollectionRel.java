package top.omoms.beans.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class CourseCollectionRel {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 课程集合id
     */
    private Integer courseCollectionId;

    /**
     * 课程id
     */
    private Integer courseId;

}
