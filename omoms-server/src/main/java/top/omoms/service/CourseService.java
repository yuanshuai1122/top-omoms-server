package top.omoms.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.omoms.beans.common.ResultBean;
import top.omoms.beans.vo.NewestCourse;
import top.omoms.enums.RetCodeEnum;
import top.omoms.mapper.CourseMapper;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseService {

    private final CourseMapper courseMapper;

    private final AsyncService asyncService;


    /**
     * 获取最新课程
     *
     * @return {@link ResultBean}<{@link Object}>
     */
    public ResultBean<Object> getNewestCourses() {
        List<NewestCourse> newestCourses = courseMapper.selectNewestCourses();
        newestCourses.forEach(p -> {
            if (null == p.getClickCount()) {
                p.setClickCount(0);
            }
        });
        log.info("获取最新列表课程成功, newestCourses:{}", newestCourses);
        return new ResultBean<>(RetCodeEnum.SUCCESS, "获取成功", newestCourses);
    }

    /**
     * 增加课程点击量
     * @param courseId 课程id
     */
    public void addCourseClickCount(Integer courseId) {
        asyncService.addCourseClickCount(courseId);
    }
}