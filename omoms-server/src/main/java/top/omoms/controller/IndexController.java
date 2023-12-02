package top.omoms.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.omoms.beans.common.ResultBean;
import top.omoms.service.BannerService;
import top.omoms.service.CourseService;

/**
 * 首页相关接口
 *
 * @author yuanshuai
 * @date 2023/11/30
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/index")
public class IndexController {

    private final CourseService courseService;

    private final BannerService bannerService;


    /**
     * 获取首页banner图
     *
     * @return {@link ResultBean}<{@link Object}>
     */
    @GetMapping("/banner/list")
    public ResultBean<Object> getBanners() {
        log.info("获取banner图开始");

        return bannerService.getBanners();
    }

    /**
     * 获取最新课程列表
     *
     * @return {@link ResultBean}<{@link Object}>
     */
    @GetMapping("/course/list/newest")
    public ResultBean<Object> getNewestCourses() {
        log.info("获取最新课程列表");

        return courseService.getNewestCourses();
    }


    /**
     * 增加课程点击量
     * @param courseId 课程id
     */
    @PostMapping("/course/count/add")
    public void addCourseClickCount(@RequestBody Integer courseId) {
        try {
            log.info("开始增加课程点击量, courseId：{}", courseId);
            courseService.addCourseClickCount(courseId);
        }catch (Exception e) {
            log.error("增加课程点击量发生异常, courseId:{}, e:{}", courseId, e.toString());
        }

    }

}
