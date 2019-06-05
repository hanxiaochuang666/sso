package cn.eblcu.sso.domain.service.impl;

import cn.eblcu.sso.domain.exception.DomainException;
import cn.eblcu.sso.domain.service.IRegisterCheckService;
import cn.eblcu.sso.domain.service.UserService;
import cn.eblcu.sso.infrastructure.util.DateUtils;
import cn.eblcu.sso.infrastructure.util.SupperTokenUtils;
import cn.eblcu.sso.infrastructure.util.TokenUtils;
import cn.eblcu.sso.persistence.dao.UserDao;
import cn.eblcu.sso.persistence.dao.UserInfoDao;
import cn.eblcu.sso.persistence.entity.dto.User;
import cn.eblcu.sso.persistence.entity.dto.UserInfo;
import cn.eblcu.sso.ui.model.UserInfoApiModel;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private RedisUtils redisUtils;

    @Resource(name = "registerCheckService")
    private IRegisterCheckService registerCheckService;

    @Value("${token.expires}")
    private int expires;

    @Override
    public List<User> getUserList() {
        List<User> users = userDao.selectUserList(new User());
        if (null != users && users.size() > 0) {
            users.forEach(user -> user.getUserinfo());
        }
        return users;
    }

    @Override
    public User getUser(User user) {

        User user1 = new User();
        List<User> users = userDao.selectUserList(user);
        if (users != null && users.size() > 0) {
            user1 = users.get(0);
            user1.getUserinfo();
        }
        return user1;
    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    @Transactional
    public Map addUser(UserInfoApiModel userApiModel) throws Exception {

        Integer regeisterType = userApiModel.getRegisterType();
        String loginName = "";
        User user = new User();
        if (1 == regeisterType) {
            checkPhoneAndCode(userApiModel);
            loginName = "" + user.getMobile();
        } else if (2 == regeisterType) {
            checkEmailAndCode(userApiModel);
            loginName = user.getEmail();
        }
        // 将信息保存入库
        Date currDate = DateUtils.now();
        user.setRegistertime(currDate);
        user.setCount(1);
        user.setLastlogintime(currDate);
        user.setLoginname(loginName);// 默认使用注册账号作为用户名,可以通过用户信息维护接口修改
        user.setStatus(0);
        user.setPassword(userApiModel.getPassword());
        userDao.insertSelective(user);
        int id = user.getId();
        log.info("插入成功，返回主键id======" + user.getId());
        // 生成一个token返回给前端
        String token = SupperTokenUtils.getSupperToken(user);
        log.info("注册自动生成token============" + token);
        // 将token和id存入Redis
        redisUtils.setStrByTime(token,"" + id,expires);
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("token", token);
        retMap.put("id", id);
        retMap.put("loginName", user.getLoginname());
        return retMap;
    }

    @Override
    public String login(UserInfoApiModel userInfoApiModel) throws Exception {

        String userName = userInfoApiModel.getLoginname();
        String passWord = userInfoApiModel.getPassword();
        Integer loginType = userInfoApiModel.getLoginType();
        User user1 = new User();
        String tokenStr = "";

        if (1 == loginType){// 手机号登录
            boolean result = registerCheckService.checkRegister(userName, passWord);
            if (!result) {
                throw new Exception("手机验证码不正确或已过期！");
            }
            user1.setMobile(Long.parseLong(userName));
            tokenStr = checkUserExist(user1,loginType);
        }else if (2 == loginType){// 用户名登录
            user1.setLoginname(userName);
            user1.setPassword(passWord);
            tokenStr = checkUserExist(user1,loginType);
        }
        return tokenStr;
    }

    @Override
    @Transactional
    public void editUser(User user) throws Exception {

        User user1 = userDao.selectByPrimaryKey(user.getId());
        if (null != user1) {
            // 表示用户存在，直接更新
            userDao.updateByPrimaryKeySelective(user);
            UserInfo userInfo = userInfoDao.selectUserInfoByUserId(user.getId());
            if (null != userInfo) {// 更新
                UserInfo userInfoU = user.getUserinfo();
                userInfoU.setUserid(user.getId());
                userInfoU.setId(userInfo.getId());
                userInfoDao.updateByPrimaryKeySelective(userInfoU);
            } else {// 创建
                userInfo = user.getUserinfo();
                userInfo.setUserid(user.getId());
                userInfoDao.insertSelective(userInfo);
            }
        } else {
            throw new DomainException("该用户不存在！");
        }
    }

    @Override
    @Transactional
    public void bindUser(UserInfoApiModel userInfoApiModel) throws Exception {

        User user = new User();
        user.setId(userInfoApiModel.getUserId());
        // 检查用户是否存在
        User userCheck = userDao.selectByPrimaryKey(userInfoApiModel.getUserId());
        if (null != userCheck) {
            Integer bindType = userInfoApiModel.getBindType();
            if (1 == bindType) {// 手机绑定
                checkPhoneAndCode(userInfoApiModel);
                user.setMobile(userInfoApiModel.getMobile());
            } else if (2 == bindType) {// 邮箱绑定
                checkEmailAndCode(userInfoApiModel);
                user.setEmail(userInfoApiModel.getEmail());
            }
            userDao.updateByPrimaryKeySelective(user);
        } else {
            throw new DomainException("用户不存在！");
        }
    }

    @Override
    public void editUserInfo(UserInfo userInfo) throws Exception {

        UserInfo userInfoTmp = new UserInfo();
        userInfoTmp.setUserid(userInfo.getUserid());
        List<UserInfo> userInfos = userInfoDao.selectUserInfoList(userInfoTmp);
        if (userInfos != null && userInfos.size() > 0) {
            userInfo.setId(userInfos.get(0).getId());
            userInfoDao.updateByPrimaryKeySelective(userInfo);
        } else {
            userInfoDao.insertSelective(userInfo);
        }
    }


    private void checkPhoneAndCode(UserInfoApiModel userInfoApiModel) throws Exception {

        boolean result = registerCheckService.checkRegister(Long.toString(userInfoApiModel.getMobile()), userInfoApiModel.getCode());
        if (!result) {
            throw new Exception("手机验证码不正确或已过期！");
        }
        // 校验手机号是否已经被注册过
        User userTmp = new User();
        userTmp.setMobile(userInfoApiModel.getMobile());
        List<User> users = userDao.selectUserList(userTmp);
        if (null != users && users.size() > 0) {
            throw new Exception("该手机号已被注册！");
        }
    }


    private void checkEmailAndCode(UserInfoApiModel userInfoApiModel) throws Exception {
        boolean result = registerCheckService.checkRegister(userInfoApiModel.getEmail(), userInfoApiModel.getCode());
        if (!result) {
            throw new Exception("邮箱验证码不正确或已过期！");
        }
        // 校验邮箱是否已经被注册过
        User userTmp = new User();
        userTmp.setEmail(userInfoApiModel.getEmail());
        List<User> users = userDao.selectUserList(userTmp);
        if (null != users && users.size() > 0) {
            throw new Exception("该邮箱已被注册！");
        }
    }

    private String checkUserExist(User user1,Integer loginType) throws Exception{

        String supperToken = "";
        List<User> users = userDao.selectUserList(user1);
        if(null != users && users.size() > 0){
            user1.setId(users.get(0).getId());
            supperToken = SupperTokenUtils.getSupperToken(user1);
        }else{
            if (1 == loginType){
                throw new DomainException("该手机号还未注册，请先注册！");
            }else if (2 == loginType){
                throw new DomainException("账号或密码不正确！");
            }
        }
        redisUtils.setStrByTime(supperToken,""+users.get(0).getId(), expires);
        return supperToken;
    }
}
