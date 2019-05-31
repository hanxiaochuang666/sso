package cn.eblcu.sso.persistence.dao;

import cn.eblcu.sso.persistence.entity.dto.UserInfo;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface UserInfoDao {

    @Delete("delete from user_info where Id = #{id}")
    int deleteByPrimaryKey(Integer id);

    int insertSelective(UserInfo record);

    UserInfo selectUserInfoByUserId(Integer id);

    List<UserInfo> selectUserInfoList(UserInfo record);

    int updateByPrimaryKeySelective(UserInfo record);

}
