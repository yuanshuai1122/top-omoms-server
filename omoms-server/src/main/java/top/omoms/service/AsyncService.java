package top.omoms.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.omoms.beans.dto.CourseClickCountDTO;
import top.omoms.beans.entity.CourseClick;
import top.omoms.mapper.CourseClickMapper;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class AsyncService {

    private final CourseClickMapper courseClickMapper;


    @Async("writeLogThreadPool")
    public void addCourseClickCount(CourseClickCountDTO courseClickCount) {
        try {
            LambdaQueryWrapper<CourseClick> lambdaQuery = Wrappers.lambdaQuery();
            lambdaQuery.eq(CourseClick::getCourseId, courseClickCount.getCourseId());
            CourseClick courseClick = courseClickMapper.selectOne(lambdaQuery);
            // 未查到 初始化
            if (null == courseClick) {
                CourseClick courseClickNew = new CourseClick();
                courseClickNew.setCourseId(courseClick.getCourseId());
                courseClickNew.setClickCount(0);
                courseClickNew.setFirstClickTime(new Date());
                courseClickNew.setLastClickTime(new Date());
                int insert = courseClickMapper.insert(courseClickNew);
                if (insert <= 0) {
                    throw new RuntimeException("新增课程点击量发生异常, courseId:" + courseClick.getClickCount());
                }
                log.info("异步新增课程点击量成功, courseId:{}", courseClick.getCourseId());
                return;
            }
            courseClick.setClickCount(courseClick.getClickCount() + 1);
            int update = courseClickMapper.update(courseClick, lambdaQuery);
            if (update <= 0) {
                throw new RuntimeException("更新课程点击量发生异常, courseId:" + courseClick.getClickCount());
            }
            log.info("异步更新课程点击量成功, courseId:{}", courseClick.getCourseId());
        }catch (Exception e) {
            log.error("异步增加课程点击量发生异常, courseId:{}, e:{}", courseClickCount.getCourseId(), e.toString());
        }
    }


}
