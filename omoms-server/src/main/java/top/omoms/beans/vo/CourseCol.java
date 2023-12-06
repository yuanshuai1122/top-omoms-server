package top.omoms.beans.vo;

import lombok.Data;

import java.util.List;

@Data
public class CourseCol {

    /**
     * 课程集合标题
     */
    private String courseCollectionTitle;

    /**
     * 课程列表
     */
    private List<CourseColInfo> CourseCollectionList;

}
