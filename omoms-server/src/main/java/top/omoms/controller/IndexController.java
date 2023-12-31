package top.omoms.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.omoms.beans.common.ResultBean;
import top.omoms.beans.dto.CourseClickCountDTO;
import top.omoms.enums.RetCodeEnum;
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
     * 分页获取全部课程列表
     * @param pageNum 页数
     * @param pageSize 页大小
     * @return
     */
    @GetMapping("/course/list/all")
    public ResultBean<Object> getAllCourses(@RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("pageSize") Integer pageSize) {
        log.info("开始获取全部课程列表, pageNum:{}, pageSize:{}", pageNum, pageSize);

        return courseService.getAllCourses(pageNum, pageSize);
    }


    /**
     * 增加课程点击量
     * @param dto 课程点击亮dto
     */
    @PostMapping("/course/count/add")
    public ResultBean<Object> addCourseClickCount(@RequestBody CourseClickCountDTO dto) {
        try {
            log.info("开始增加课程点击量, dto：{}", dto);
            return courseService.addCourseClickCount(dto);
        }catch (Exception e) {
            log.error("增加课程点击量发生异常, dto:{}, e:{}", dto, e.toString());
            return new ResultBean<>(RetCodeEnum.STATUS_ERROR, "增加点击量失败", null);
        }

    }

}
