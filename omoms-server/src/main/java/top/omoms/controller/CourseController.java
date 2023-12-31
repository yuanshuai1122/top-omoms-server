package top.omoms.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.omoms.beans.common.ResultBean;
import top.omoms.beans.dto.CourseSubscribeDTO;
import top.omoms.beans.vo.UserSubsStatus;
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

    /**
     * 获取课程小节列表
     * @param courseId 课程id
     * @return 小节列表
     */
    @GetMapping("/part/list")
    public ResultBean<Object> getCoursePartList(@RequestParam("courseId") Integer courseId) {
        log.info("开始获取课程小节列表, courseId:{}", courseId);
        if (null == courseId || courseId <= 0) {
            return new ResultBean<>(RetCodeEnum.PARAM_ERROR, "参数错误", null);
        }

        return courseService.getCoursePartList(courseId);
    }

    /**
     * 获取课程小节详情
     * @param partId 课程小节id
     * @return 小节详情
     */
    @GetMapping("/part/info")
    public ResultBean<Object> getCoursePartDetail(@RequestParam("partId") Integer partId) {
        if (null == partId || partId <= 0) {
            return new ResultBean<>(RetCodeEnum.PARAM_ERROR, "参数错误", null);
        }

        return courseService.getCoursePartDetail(partId);
    }


    /**
     * 订阅课程
     * @param dto dto
     * @return 订阅结果
     */
    @PostMapping("/subscribe")
    public ResultBean<Object> courseSubscribe(@RequestBody CourseSubscribeDTO dto) {
        if (dto.getCourseId() == null || dto.getCourseId() <= 0) {
            return new ResultBean<>(RetCodeEnum.PARAM_ERROR, "参数错误", null);
        }

        return courseService.courseSubscribe(dto);
    }


    /**
     * 检查是否已经订阅该课程
     * @param courseId 课程id
     * @return 订阅状态
     */
    @GetMapping("/subscribe/check")
    public ResultBean<UserSubsStatus> courseSubscribeCheck(@RequestParam("courseId") Integer courseId) {
        if (null == courseId || courseId <= 0) {
            return new ResultBean<>(RetCodeEnum.PARAM_ERROR, "参数错误", null);
        }

        return courseService.courseSubscribeStatus(courseId);
    }



}
