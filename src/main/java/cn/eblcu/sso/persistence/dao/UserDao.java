package cn.eblcu.sso.persistence.dao;

import cn.eblcu.sso.persistence.entity.dto.User;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface UserDao {

    @Delete("delete from user where id = #{id}")
    int deleteByPrimaryKey(Integer id);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectUserList(User user);

    int updateByPrimaryKeySelective(User record);

}
