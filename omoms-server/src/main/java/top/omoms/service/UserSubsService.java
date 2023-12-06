package top.omoms.service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.omoms.beans.common.ResultBean;
import top.omoms.beans.entity.UserSubs;
import top.omoms.enums.RetCodeEnum;
import top.omoms.mapper.UserSubsMapper;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserSubsService {

    private final UserSubsMapper userSubsMapper;

    /**
     * 验证用户是否订阅对应课程
     * @param userId 用户id
     * @param courseId 课程id
     * @return 是否订阅
     */
    public ResultBean<Object> userSubsValidation(Integer userId, Integer courseId) {
        if (null == userId || userId <= 0 || null == courseId || courseId <= 0) {
            return new ResultBean<>(RetCodeEnum.PARAM_ERROR, "参数错误", null);
        }
        // 获取用户订阅关系
        UserSubs userSubs = new LambdaQueryChainWrapper<UserSubs>(userSubsMapper)
                .eq(UserSubs::getUserId, userId)
                .eq(UserSubs::getCourseId, courseId)
                .one();
        if (null == userSubs) {
            return new ResultBean<>(RetCodeEnum.STATUS_ERROR, "用户未订阅该课程，请订阅", null);
        }

        return new ResultBean<>(RetCodeEnum.SUCCESS, "用户已订阅该课程", null);
    }

}
