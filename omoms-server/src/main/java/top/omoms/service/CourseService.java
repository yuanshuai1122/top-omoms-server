package top.omoms.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.omoms.beans.common.ResultBean;
import top.omoms.beans.vo.AllCourse;
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
        log.info("获取最新列表课程结果, newestCourses:{}", newestCourses);

        return new ResultBean<>(RetCodeEnum.SUCCESS, "获取成功", newestCourses);
    }


    /**
     * 分页获取全部课程列表
     * @param pageNum 页数
     * @param pageSize 页大小
     * @return
     */
    public ResultBean<Object> getAllCourses(Integer pageNum, Integer pageSize) {
        List<AllCourse> allCourses = courseMapper.selectAllCourses(pageNum, pageSize);
        log.info("获取全部列表课程结果, allCourses:{}", allCourses);

        return new ResultBean<>(RetCodeEnum.SUCCESS, "获取成功", allCourses);
    }

    /**
     * 增加课程点击量
     * @param courseId 课程id
     */
    public void addCourseClickCount(Integer courseId) {
        asyncService.addCourseClickCount(courseId);
    }
}
