package top.omoms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.omoms.beans.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户mapper
 *
 * @author yuanshuai
 * @date 2023/11/24
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
