package top.omoms.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.omoms.beans.common.ResultBean;
import top.omoms.service.IndexService;

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

    private final IndexService indexService;


    /**
     * 获取首页banner图
     *
     * @return {@link ResultBean}<{@link Object}>
     */
    @GetMapping("/banner/list")
    public ResultBean<Object> getBanners() {
        log.info("获取banner图开始");

        return indexService.getBanners();
    }

    /**
     * 获取最新课程列表
     *
     * @return {@link ResultBean}<{@link Object}>
     */
    @GetMapping("/course/list/newest")
    public ResultBean<Object> getNewestCourses() {
        log.info("获取最新课程列表");

        return indexService.getNewestCourses();
    }

}
