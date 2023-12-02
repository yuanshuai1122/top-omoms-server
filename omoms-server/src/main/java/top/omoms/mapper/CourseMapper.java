package top.omoms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.omoms.beans.entity.Course;
import top.omoms.beans.vo.NewestCourse;

import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {


    /**
     * 查询最新课程列表
     * @return
     */
    @Select({
            "SELECT c.id, c.cover, c.title, c.description, cc.category_name, t.nickname, ck.click_count",
            "FROM course c",
            "LEFT JOIN course_category cc ON c.category_id = cc.id",
            "LEFT JOIN tutor t ON c.tutor_id = t.id",
            "LEFT JOIN course_click ck ON ck.course_id = c.id",
            "WHERE c.is_deleted = 0",
            "AND cc.is_deleted = 0",
            "AND t.is_deleted = 0",
            "ORDER BY c.create_time DESC",
            "LIMIT 12"
    })
    List<NewestCourse> selectNewestCourses();

}
