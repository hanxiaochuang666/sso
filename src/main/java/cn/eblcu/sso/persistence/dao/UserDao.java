package cn.eblcu.sso.persistence.dao;

import cn.eblcu.sso.persistence.entity.dto.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectUserList(User user);

    int updateByPrimaryKeySelective(User record);

}
