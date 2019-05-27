package cn.eblcu.sso.persistence.dao;

import cn.eblcu.sso.persistence.entity.dto.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @desc
 * @Author：hanchuang
 * @Version 1.0
 * @Date：add on 14:44 2019/5/27
 */
@Mapper
public interface UserInfoDao {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    List<UserInfo> selectUserInfoList(UserInfo record);

    int updateByPrimaryKeySelective(UserInfo record);

}
