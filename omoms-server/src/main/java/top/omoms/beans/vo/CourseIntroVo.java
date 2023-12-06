package top.omoms.beans.vo;

import lombok.Data;

@Data
public class CourseIntroVo {

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 讲师名称
     */
    private String tutorName;

    /**
     * 讲师头像
     */
    private String tutorAvatar;

    /**
     * 讲师头衔
     */
    private String tutorTitle;

    /**
     * 订阅人数
     */
    private Integer subsCount;

    /**
     * 课程集合
     */
    private CourseCol courseCol;



}
