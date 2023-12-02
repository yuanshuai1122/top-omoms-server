package top.omoms.service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.omoms.beans.common.ResultBean;
import top.omoms.beans.entity.Banner;
import top.omoms.beans.vo.BannerVO;
import top.omoms.enums.RetCodeEnum;
import top.omoms.mapper.BannerMapper;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BannerService {


    private final BannerMapper bannerMapper;


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

}
