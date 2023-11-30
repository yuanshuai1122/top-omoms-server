package top.omoms.service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.omoms.beans.common.ResultBean;
import top.omoms.beans.entity.Banner;
import top.omoms.beans.entity.User;
import top.omoms.beans.vo.BannerVO;
import top.omoms.beans.vo.NewestCourse;
import top.omoms.enums.RetCodeEnum;
import top.omoms.mapper.BannerMapper;
import top.omoms.mapper.CourseMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class IndexService {


    private final BannerMapper bannerMapper;

    private final CourseMapper courseMapper;


    /**
     * 获取首页banner图
     *
     * @return {@link ResultBean}<{@link Object}>
     */
    public ResultBean<Object> getBanners() {

        List<Banner> banners = new LambdaQueryChainWrapper<>(bannerMapper)
                .eq(Banner::getIsDeleted, 0)
                .orderByAsc(Banner::getBannerSort)
                .list();
        log.info("获取封面图成功, banners:{}", banners);
        if (banners.isEmpty()) {
            return new ResultBean<>(RetCodeEnum.SUCCESS, "获取成功", Collections.EMPTY_LIST);
        }

        List<BannerVO> collect = banners.stream()
                .map(banner -> new BannerVO(banner.getId(), banner.getBannerSort(), banner.getBannerUrl()))
                .toList();

        return new ResultBean<>(RetCodeEnum.SUCCESS, "获取成功", collect);

    }

    /**
     * 获取最新课程
     *
     * @return {@link ResultBean}<{@link Object}>
     */
    public ResultBean<Object> getNewestCourses() {
        List<NewestCourse> newestCourses = courseMapper.selectNewestCourses();
        log.info("获取最新列表课程成功, newestCourses:{}", newestCourses);
        return new ResultBean<>(RetCodeEnum.SUCCESS, "获取成功", newestCourses);
    }
}
