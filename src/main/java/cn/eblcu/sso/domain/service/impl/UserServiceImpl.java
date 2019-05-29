package cn.eblcu.sso.domain.service.impl;

import cn.eblcu.sso.domain.service.IRegisterCheckService;
import cn.eblcu.sso.domain.service.UserService;
import cn.eblcu.sso.infrastructure.util.DateUtils;
import cn.eblcu.sso.infrastructure.util.TokenUtils;
import cn.eblcu.sso.persistence.dao.UserDao;
import cn.eblcu.sso.persistence.dao.UserInfoDao;
import cn.eblcu.sso.persistence.entity.dto.User;
import cn.eblcu.sso.persistence.entity.dto.UserInfo;
import cn.eblcu.sso.ui.model.UserInfoApiModel;
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

    @Resource(name="registerCheckService")
    private IRegisterCheckService registerCheckService;

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
    public Map addUser(UserInfoApiModel userApiModel) throws Exception{

        String code = userApiModel.getCode();

        Map<String,Object> retMap = new HashMap<>();
        String regeisterType = userApiModel.getRegisterType();
        String loginName = "";
        List<User> users = null;
        User user = new User();
        if ("1".equals(regeisterType)) {
            // 手机号注册
            // 校验手机号的验证码是否正确
            boolean result = registerCheckService.checkRegister(Long.toString(userApiModel.getMobile()),code);
            if(!result){
                throw new Exception("手机验证码不正确或已过期！");
            }
            user.setMobile(userApiModel.getMobile());
            users = userDao.selectUserList(user);
            if(null != users && users.size() > 0){
                throw new Exception("该手机号已被注册！");
            }else {
                loginName = "" + user.getMobile();
            }
        }else if ("2".equals(regeisterType)){
            // 邮箱注册
            // 校验邮箱验证码吗是否正确
            boolean result = registerCheckService.checkRegister(userApiModel.getEmail(),code);
            if(!result){
                throw new Exception("邮箱验证码不正确或已过期！");
            }
            user.setEmail(userApiModel.getEmail());
            users = userDao.selectUserList(user);
            if(null != users && users.size() > 0){
                throw new Exception("该邮箱已被注册！");
            }else{
                loginName = user.getEmail();
            }
        }
        // 将信息保存入库
        Date currDate = DateUtils.now();
        user.setRegistertime(currDate);
        user.setCount(1);
        user.setLastlogintime(currDate);
        user.setLoginname(loginName);// 默认使用注册账号作为用户名,可以通过用户信息维护接口修改
        user.setStatus(0);
        user.setPassword(userApiModel.getPassword());
        int id = userDao.insertSelective(user);
        // todo 没有返回主键id，如果不行就再去库里查一下主键
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

    @Override
    public void editUser(User user) throws Exception {

        User usertmp = new User();
        usertmp.setId(user.getId());
        List<User> users = userDao.selectUserList(usertmp);
        if(null != users && users.size() > 0){
            // 表示用户存在，直接更新
            userDao.updateByPrimaryKeySelective(user);

            UserInfo userInfo = new UserInfo();
            userInfo.setUserid(user.getId());
            List<UserInfo> userInfos = userInfoDao.selectUserInfoList(userInfo);
            if(null != userInfos && userInfos.size() > 0){ // 表示库里有基本信息了
                // 更新
                userInfo = user.getUserinfo();
                userInfo.setId(userInfos.get(0).getId());
                userInfoDao.updateByPrimaryKeySelective(userInfo);
            }else{
                // 创建
                userInfo = user.getUserinfo();
                userInfoDao.insertSelective(userInfo);
            }
        }else{
            throw new Exception("该用户不存在！");
        }

    }

    @Override
    public void bindUser(UserInfoApiModel userInfoApiModel) throws Exception {

        // 检查用户是否存在
        User user = new User();
        user.setId(userInfoApiModel.getUserId());
        user.setLoginname(userInfoApiModel.getLoginname());
        List<User> users = userDao.selectUserList(user);

        String code = userInfoApiModel.getCode();
        if(null != users && users.size() > 0){
            String bindType = userInfoApiModel.getBindType();
            if("1".equals(bindType)){
                // 手机绑定
                // 校验手机号的验证码是否正确
                boolean result = registerCheckService.checkRegister(Long.toString(userInfoApiModel.getMobile()),code);
                if(!result){
                    throw new Exception("手机验证码不正确或已过期！");
                }
                // 校验手机号是否已经被注册过
                User usertmp = new User();
                usertmp.setMobile(userInfoApiModel.getMobile());
                List<User> users1 = userDao.selectUserList(user);
                if(null != users1 && users1.size() > 0){
                    throw new Exception("该手机号已被注册，请更换手机号！");
                }

                user.setMobile(userInfoApiModel.getMobile());
            }else if("2".equals(bindType)){
                // 邮箱绑定
                // 校验邮箱验证码吗是否正确
                boolean result = registerCheckService.checkRegister(userInfoApiModel.getEmail(),code);
                if(!result){
                    throw new Exception("邮箱验证码不正确或已过期！");
                }
                // 校验邮箱是否已经被注册过
                User usertmp = new User();
                usertmp.setEmail(userInfoApiModel.getEmail());
                List<User> users1 = userDao.selectUserList(usertmp);
                if(null != users1 && users1.size() > 0){
                    throw new Exception("该邮箱已被注册，请更换邮箱！");
                }
                user.setEmail(userInfoApiModel.getEmail());
            }
            userDao.updateByPrimaryKeySelective(user);
        }else{
            throw new Exception("用户不存在！");
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
        }else{
            userInfoDao.insertSelective(userInfo);
        }
    }
}
