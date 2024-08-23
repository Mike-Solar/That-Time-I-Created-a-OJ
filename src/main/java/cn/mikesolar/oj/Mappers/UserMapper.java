package cn.mikesolar.oj.Mappers;

import cn.mikesolar.oj.Entity.UserPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface UserMapper extends BaseMapper<UserPo> {
    UserPo getUserByName(String username);
    UserPo getUserByPhoneNumber(String phoneNumber);
    void cleanupUser();
}
