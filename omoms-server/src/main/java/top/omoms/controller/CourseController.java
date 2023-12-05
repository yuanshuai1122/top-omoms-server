package top.omoms.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.omoms.beans.common.ResultBean;
import top.omoms.enums.RetCodeEnum;
import top.omoms.service.CourseService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {


    private final CourseService courseService;


    /**
     * 获取课程介绍
     * @param courseId 课程id
     * @return 课程介绍
     */
    @GetMapping("/intro")
    public ResultBean<Object> getCourseIntro(@RequestParam("courseId") Integer courseId) {
        log.info("开始获取课程介绍, courseId:{}", courseId);
        if (null == courseId || courseId <= 0) {
            return new ResultBean<>(RetCodeEnum.PARAM_ERROR, "参数错误", null);
        }

        return courseService.getCourseIntro(courseId);
    }

}
