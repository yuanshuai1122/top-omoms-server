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


}
