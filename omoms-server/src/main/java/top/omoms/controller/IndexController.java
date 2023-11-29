package top.omoms.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.omoms.beans.common.ResultBean;
import top.omoms.service.IndexService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/index")
public class IndexController {

    private final IndexService indexService;


    @GetMapping("/banner/list")
    public ResultBean<Object> getBanners() {
        log.info("获取banner图开始");

        return indexService.getBanners();
    }

    @GetMapping("/course/list/newest")
    public ResultBean<Object> getNewestCourses() {
        log.info("获取最新课程列表");

        return indexService.getNewestCourses();
    }

}
