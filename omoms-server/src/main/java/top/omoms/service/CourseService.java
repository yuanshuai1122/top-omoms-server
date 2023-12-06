package top.omoms.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.omoms.beans.common.ResultBean;
import top.omoms.beans.dto.CourseClickCountDTO;
import top.omoms.beans.entity.*;
import top.omoms.beans.vo.*;
import top.omoms.enums.RetCodeEnum;
import top.omoms.mapper.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseService {

    private final CourseMapper courseMapper;

    private final AsyncService asyncService;

    private final CoursePartMapper coursePartMapper;

    private final UserService userService;

    private final UserSubsService userSubsService;

    private final UserSubsMapper userSubsMapper;

    private final CourseCollectionRelMapper courseCollectionRelMapper;

    private final CourseCollectionMapper courseCollectionMapper;


    /**
     * 获取最新课程
     *
     * @return {@link ResultBean}<{@link Object}>
     */
    public ResultBean<Object> getNewestCourses() {
        List<NewestCourse> newestCourses = courseMapper.selectNewestCourses();
        log.info("获取最新列表课程结果, newestCourses:{}", newestCourses);

        return new ResultBean<>(RetCodeEnum.SUCCESS, "获取成功", newestCourses);
    }


    /**
     * 分页获取全部课程列表
     * @param pageNum 页数
     * @param pageSize 页大小
     * @return
     */
    public ResultBean<Object> getAllCourses(Integer pageNum, Integer pageSize) {

        pageNum = pageNum == 0 ? 1 : pageNum;
        pageNum = ( pageNum -1 ) * pageSize;

        List<AllCourse> allCourses = courseMapper.selectAllCourses(pageNum, pageSize);
        log.info("获取全部列表课程结果, allCourses:{}", allCourses);

        return new ResultBean<>(RetCodeEnum.SUCCESS, "获取成功", allCourses);
    }

    /**
     * 增加课程点击量
     * @param dto 课程dto
     */
    public ResultBean<Object> addCourseClickCount(CourseClickCountDTO dto) {
        Course course = new LambdaQueryChainWrapper<Course>(courseMapper)
                .eq(Course::getId, dto.getCourseId())
                .eq(Course::getIsDeleted, 0)
                .one();
        log.info("增加课程点击量查询课程, dto:{}, course:{}", dto, course);
        if (null == course) {
            return new ResultBean<>(RetCodeEnum.STATUS_ERROR, "课程不存在", null);
        }
        asyncService.addCourseClickCount(dto);

        return new ResultBean<>(RetCodeEnum.SUCCESS, "成功", null);
    }

    /**
     * 获取课程介绍
     * @param courseId 课程id
     * @return 课程介绍
     */
    public ResultBean<Object> getCourseIntro(Integer courseId) {

        CourseIntroVo courseIntro = courseMapper.selectCourseById(courseId);
        if (null == courseIntro) {
            return new ResultBean<>(RetCodeEnum.STATUS_ERROR, "课程不存在", null);
        }
        // 查询课程订阅量
        Long count = new LambdaQueryChainWrapper<>(userSubsMapper).eq(UserSubs::getCourseId, courseId).count();
        courseIntro.setSubsCount(Math.toIntExact(count));

        // 查询系列课程
        CourseCollectionRel courseCollectionRel = new LambdaQueryChainWrapper<CourseCollectionRel>(courseCollectionRelMapper)
                .eq(CourseCollectionRel::getCourseId, courseId)
                .one();
        if (null == courseCollectionRel) {
            // 不属于任何系列课
            log.info("获取课程介绍结果, courseId:{}, courseIntro:{}", courseId, courseIntro);
            return new ResultBean<>(RetCodeEnum.SUCCESS, "获取成功", courseIntro);
        }

        // 获取系列信息
        CourseCollection courseCollection = new LambdaQueryChainWrapper<CourseCollection>(courseCollectionMapper)
                .eq(CourseCollection::getId, courseCollectionRel.getCourseCollectionId())
                .eq(CourseCollection::getIsDeleted, 0)
                .one();
        if (null == courseCollection) {
            // 课程系列已经不存在
            log.info("获取课程介绍结果, courseId:{}, courseIntro:{}", courseId, courseIntro);
            return new ResultBean<>(RetCodeEnum.SUCCESS, "获取成功", courseIntro);
        }

        CourseCol courseCol = new CourseCol();
        courseCol.setCourseCollectionTitle(courseCollection.getTitle());

        // 获取系列列表
        List<CourseCollectionRel> collectionRels = new LambdaQueryChainWrapper<CourseCollectionRel>(courseCollectionRelMapper)
                .eq(CourseCollectionRel::getCourseCollectionId, courseCollectionRel.getCourseCollectionId())
                .list();

        List<Integer> courseIds = collectionRels.stream().map(CourseCollectionRel::getCourseId).toList();

        // 获取课程列表
        List<Course> courseList = new LambdaQueryChainWrapper<Course>(courseMapper)
                .eq(Course::getIsDeleted, 0)
                .in(Course::getId, courseIds)
                .list();

        List<CourseColInfo> courseColInfos = new ArrayList<>();
        courseList.forEach(p -> {
            courseColInfos.add(
                    new CourseColInfo(p.getId(), p.getTitle(), p.getCover())
            );
        });
        courseCol.setCourseCollectionList(courseColInfos);
        courseIntro.setCourseCol(courseCol);

        log.info("获取课程介绍结果, courseId:{}, courseIntro:{}", courseId, courseIntro);
        return new ResultBean<>(RetCodeEnum.SUCCESS, "获取成功", courseIntro);
    }

    /**
     * 获取课程小节列表
     * @param courseId 课程id
     * @return 小节列表
     */
    public ResultBean<Object> getCoursePartList(Integer courseId) {
        // 验证课程是否存在
        ResultBean<Course> courseValidation = courseValidation(courseId);
        if (!courseValidation.Success()) {
            return new ResultBean<>(RetCodeEnum.STATUS_ERROR, courseValidation.getMessage(), null);
        }

        Course course = courseValidation.getData();

        // 查询课程列表
        List<CoursePart> courseParts = new LambdaQueryChainWrapper<CoursePart>(coursePartMapper)
                .eq(CoursePart::getCourseId, course.getId())
                .orderByAsc(CoursePart::getPartSort)
                .list();
        if (courseParts.isEmpty()) {
            return new ResultBean<>(RetCodeEnum.STATUS_ERROR, "课程列表为空", null);
        }

        return new ResultBean<>(RetCodeEnum.SUCCESS, "获取成功", courseParts);

    }

    /**
     * 获取课程小节详情
     * @param partId 课程小节id
     * @return 小节详情
     */
    public ResultBean<Object> getCoursePartDetail(Integer partId) {
        // 查询课程详情
        CoursePart coursePart = new LambdaQueryChainWrapper<CoursePart>(coursePartMapper)
                .eq(CoursePart::getId, partId)
                .one();
        if (null == coursePart) {
            return new ResultBean<>(RetCodeEnum.STATUS_ERROR, "课程资源不存在", null);
        }
        // 验证课程是否存在
        ResultBean<Course> courseValidation = courseValidation(coursePart.getCourseId());
        if (!courseValidation.Success()) {
            return new ResultBean<>(RetCodeEnum.STATUS_ERROR, courseValidation.getMessage(), null);
        }

        Course course = courseValidation.getData();

        // 可试看课程
        if (coursePart.getIsTry() != 0) {
            return new ResultBean<>(RetCodeEnum.SUCCESS, "获取成功", coursePart);
        }

        // 验证用户登录和订阅
        ResultBean<User> loginUserInfo = userService.getLoginUserInfo();
        if (!loginUserInfo.Success()) {
            return new ResultBean<>(loginUserInfo.getRetCode(), loginUserInfo.getMessage(), loginUserInfo.getData());
        }
        User userInfo = loginUserInfo.getData();
        ResultBean<Object> subsValidation = userSubsService.userSubsValidation(userInfo.getId(), course.getId());
        if (!subsValidation.Success()) {
            return new ResultBean<>(subsValidation.getRetCode(), subsValidation.getMessage(), subsValidation.getData());
        }

        return new ResultBean<>(RetCodeEnum.SUCCESS, "获取成功", coursePart);


    }


    /**
     * 验证课程是否存在
     * @param courseId 课程id
     * @return 是否存在
     */
    private ResultBean<Course> courseValidation(Integer courseId) {
        // 验证课程是否存在
        Course course = new LambdaQueryChainWrapper<Course>(courseMapper)
                .eq(Course::getId, courseId)
                .eq(Course::getIsDeleted, 0)
                .one();
        if (null == course) {
            return new ResultBean<>(RetCodeEnum.STATUS_ERROR, "课程不存在", null);
        }

        return new ResultBean<>(RetCodeEnum.SUCCESS, "验证成功", course);
    }

}
