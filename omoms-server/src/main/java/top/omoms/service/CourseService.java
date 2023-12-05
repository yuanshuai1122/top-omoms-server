package top.omoms.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.omoms.beans.common.ResultBean;
import top.omoms.beans.dto.CourseClickCountDTO;
import top.omoms.beans.entity.Course;
import top.omoms.beans.entity.CoursePart;
import top.omoms.beans.vo.AllCourse;
import top.omoms.beans.vo.CourseIntroVo;
import top.omoms.beans.vo.NewestCourse;
import top.omoms.enums.RetCodeEnum;
import top.omoms.mapper.CourseMapper;
import top.omoms.mapper.CoursePartMapper;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseService {

    private final CourseMapper courseMapper;

    private final AsyncService asyncService;

    private final CoursePartMapper coursePartMapper;


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

        pageNum = pageNum == 0 ? 1 : pageNum;
        pageNum = ( pageNum -1 ) * pageSize;

        List<AllCourse> allCourses = courseMapper.selectAllCourses(pageNum, pageSize);
        log.info("获取全部列表课程结果, allCourses:{}", allCourses);

        return new ResultBean<>(RetCodeEnum.SUCCESS, "获取成功", allCourses);
    }

    /**
     * 增加课程点击量
     * @param dto 课程dto
     */
    public void addCourseClickCount(CourseClickCountDTO dto) {
        Course course = courseMapper.selectById(dto.getCourseId());
        log.info("增加课程点击量查询课程, dto:{}, course:{}", dto, course);
        if (null == course) {
            return;
        }
        asyncService.addCourseClickCount(dto);
    }

    /**
     * 获取课程介绍
     * @param courseId 课程id
     * @return 课程介绍
     */
    public ResultBean<Object> getCourseIntro(Integer courseId) {

        CourseIntroVo courseIntro = courseMapper.selectCourseById(courseId);
        log.info("获取课程介绍结果, courseId:{}, courseIntro:{}", courseId, courseIntro);
        if (null == courseIntro) {
            return new ResultBean<>(RetCodeEnum.STATUS_ERROR, "课程不存在", null);
        }

        return new ResultBean<>(RetCodeEnum.SUCCESS, "获取成功", courseIntro);
    }

    /**
     * 获取课程小节列表
     * @param courseId 课程id
     * @return 小节列表
     */
    public ResultBean<Object> getCoursePartList(Integer courseId) {
        // 验证课程是否存在
        Course course = new LambdaQueryChainWrapper<Course>(courseMapper)
                .eq(Course::getId, courseId)
                .eq(Course::getIsDeleted, 0)
                .one();
        if (null == course) {
            return new ResultBean<>(RetCodeEnum.STATUS_ERROR, "课程不存在", null);
        }

        // 查询课程列表
        List<CoursePart> courseParts = new LambdaQueryChainWrapper<CoursePart>(coursePartMapper)
                .eq(CoursePart::getCourseId, course.getId())
                .orderByAsc(CoursePart::getPartSort)
                .list();
        if (courseParts.isEmpty()) {
            return new ResultBean<>(RetCodeEnum.STATUS_ERROR, "课程列表为空", null);
        }

        return new ResultBean<>(RetCodeEnum.SUCCESS, "获取成功", courseParts);

    }
}
