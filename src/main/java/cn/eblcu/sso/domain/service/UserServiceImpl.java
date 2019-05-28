package cn.eblcu.sso.domain.service;

import cn.eblcu.sso.infrastructure.util.TokenUtils;
import cn.eblcu.sso.persistence.dao.UserDao;
import cn.eblcu.sso.persistence.entity.dto.User;
import cn.eblcu.sso.persistence.entity.dto.UserInfo;
import cn.eblcu.sso.ui.model.UserApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisUtils redisUtils;

    @Value("${token.expires}")
    private int expires;

    @Override
    public List<User> getUserList() {
        List<User> users = userDao.selectUserList(new User());
        if(null != users && users.size() > 0){
            users.forEach(user -> user.setUserinfo(user.getUserinfo()));
        }
        return users;
    }

    @Override
    public User getUser(User user) {

        User user1 = new User();
        List<User> users = userDao.selectUserList(user);
        if (users != null && users.size() > 0) {
            user1 = users.get(0);
            user1.setUserinfo(user1.getUserinfo());
        }
        return user1;
    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    @Transactional
    public Map addUser(UserApiModel userApiModel) throws Exception{

        Map<String,Object> retMap = new HashMap<>();
        User user = userApiModel.getUser();
        String regeisterType = userApiModel.getRegisterType();
        String loginName = "";
        List<User> users = userDao.selectUserList(user);
        if ("1".equals(regeisterType)) {
            // 手机号注册
            if(null != users && users.size() > 0){
                throw new Exception("该手机号已被注册！");
            }else {
                loginName = "" + user.getMobile();
            }
        }else if ("2".equals(regeisterType)){
            // 邮箱注册
            if(null != users && users.size() > 0){
                throw new Exception("该邮箱已被注册！");
            }else{
                loginName = user.getEmail();
            }
        }
        // 将信息保存入库
        Date currDate = new Date();
        user.setRegistertime(currDate);
        user.setCount(1);
        user.setLastlogintime(currDate);
        user.setLoginname(loginName);// 默认使用注册账号作为用户名,可以通过用户信息维护接口修改
        int id = userDao.insertSelective(user);
        log.info("插入成功，返回主键id======"+id);

        // 生成一个token返回给前端
        String token = TokenUtils.createToken(user);
        log.info("注册自动生成token============"+token);

        // 将token和id存入Redis
        redisUtils.setStrByTime(token,""+id,expires);
        retMap.put("token",token);
        retMap.put("id",id);
        retMap.put("loginName",user.getLoginname());
        return retMap;
    }
}
