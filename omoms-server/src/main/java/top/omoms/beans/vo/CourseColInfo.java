package top.omoms.beans.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseColInfo {

    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 课程标题
     */
    private String courseTitle;

    /**
     * 封面
     */
    private String cover;

}
