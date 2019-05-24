package cn.eblcu.sso.persistence.dao;
import cn.eblcu.sso.persistence.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserDao {
    public List<User> getUser();

    public void deleteUser(int id);

    public void addUser(User user);
}
