package com.rttmall.shopbackend.sys.service.impl;

import com.rttmall.shopbackend.exception.ServiceException;
import com.rttmall.shopbackend.httpModel.SessionInfo;
import com.rttmall.shopbackend.sys.mapper.UserCustomMapper;
import com.rttmall.shopbackend.sys.mapper.UserMapper;
import com.rttmall.shopbackend.sys.pojo.Role;
import com.rttmall.shopbackend.sys.pojo.RoleCustom;
import com.rttmall.shopbackend.sys.pojo.User;
import com.rttmall.shopbackend.sys.pojo.UserCustom;
import com.rttmall.shopbackend.sys.service.UserService;
import com.rttmall.shopbackend.utils.Constants;
import com.rttmall.shopbackend.utils.MD5;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by wangweibin on 2017/2/22.
 */
public class UserServiceImpl implements UserService {
    @Value("#{configProperties['user.default.pwd']}")
    private String defaultPw;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserCustomMapper userCustomMapper;

    public void loginCheck(UserCustom userCustom, HttpSession session) throws Exception {
        MD5 md5 = new MD5();
        SessionInfo sessionInfo = new SessionInfo();
        if (null != userCustom) {
            String password = md5.getMD5Str(userCustom.getUserName() + userCustom.getUserPw());
            userCustom.setUserPw(password);
            List<UserCustom> userCustomList = userCustomMapper.loginCheck(userCustom);
            if (null != userCustomList && userCustomList.size() == 1) {
                //更新用户登录信息
                UserCustom userCustom1 = userCustomList.get(0);
                String defaultPassword = md5.getMD5Str(userCustom.getUserName() + defaultPw);
                if (password.equals(defaultPassword)) {
                    sessionInfo.setDefaultPwd(true);
                }
                sessionInfo.setUser(userCustom1);
                User user = new User();
                user.setUserId(userCustom1.getUserId());
                user.setLastLoginTime(userCustom1.getLoginTime());
                user.setLoginTime(new Date());
                user.setLoginCount(userCustom1.getLoginCount() + 1);
                userMapper.updateByPrimaryKeySelective(user);
                session.setAttribute(Constants.SESSION_BEAN, sessionInfo);
            } else {
                throw new ServiceException(Constants.USER_NOT_ERROR);
            }
        } else {
            throw new ServiceException(Constants.REQUEST_DATA_ERROR);
        }
    }

    public int updateByPrimaryKeySelective(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    public void changePassword(UserCustom userCustom) throws Exception {
        if (null != userCustom ) {
            MD5 md5 = new MD5();
            //判断密码是否正确
            User user = userMapper.selectByPrimaryKey(userCustom.getUserId());
            String userName = user.getUserName();
            if (!user.getUserPw().equals(md5.getMD5Str(userName + userCustom.getUserPw()))) {
                throw new ServiceException(Constants.PASSWORD_ERROR);
            }
            userCustom.setUserPw(md5.getMD5Str(userName + userCustom.getUserNewPw()));
            int updateNum = userMapper.updateByPrimaryKeySelective(userCustom);

            if (updateNum != 1) {
                throw new ServiceException(Constants.UPDATEPW_ERROR);
            }
        }else {
            throw new ServiceException(Constants.REQUEST_DATA_ERROR);
        }
    }

    @Override
    public Map queryUser(UserCustom userCustom) {
        Map map = new HashMap();
        List<UserCustom> userCustomList = userCustomMapper.queryUser(userCustom);
        int sum = userCustomMapper.queryUserCount(userCustom);
        userCustomList.forEach(s->{
            String roleGroup = "";
            List<Role> roleList = userCustomMapper.selectUserRoleByUserId(s.getUserId());
            if (null != roleList) {
                for (int i = 0; i < roleList.size(); i++) {
                    Role role = roleList.get(i);
                    if (null != role) {
                        if (i == roleList.size() - 1) {
                            roleGroup += role.getRoleName();
                        } else {
                            roleGroup += role.getRoleName() + ",";
                        }
                    }
                }
            }
            s.setRoleName(roleGroup);
        });
        map.put("data", userCustomList);
        map.put("total", sum);
        return map;
    }

    @Override
    public void insertUser(UserCustom userCustom) throws Exception {
        MD5 md5 = new MD5();
        RoleCustom roleCustom;
        if (null != userCustom && null != (roleCustom = userCustom.getRoleCustom())) {
            //先添加用户表(先判断账号唯一)
            int count = userCustomMapper.isExistUser(userCustom.getUserName());
            if (count != 0) {
                throw new ServiceException(Constants.USERNAME_EXIST);
            }
            userCustom.setUserPw(md5.getMD5Str(userCustom.getUserName() + defaultPw));
            userMapper.insertSelective((User) userCustom);
            //再添加用户角色表(先删除该用户所有角色)
            insertUserRole(roleCustom, userCustom);
        } else {
            throw new ServiceException(Constants.REQUEST_DATA_ERROR);
        }
    }


    public UserCustom editQueryUser(int id) {
        String roleGroup = "";
        //查询用户
        User user = userMapper.selectByPrimaryKey(id);
        //查询用户角色
        List<Role> roleList = userCustomMapper.selectUserRoleByUserId(id);
        if (null != roleList) {
            for (int i = 0; i < roleList.size(); i++) {
                Role role = roleList.get(i);
                if (null != role) {
                    if (i == roleList.size() - 1) {
                        roleGroup += role.getRoleId();
                    } else {
                        roleGroup += role.getRoleId() + ",";
                    }
                }
            }
        }
        //由于开发中，我们可能对查询出来的商品进行业务处理，所以我们返回它的扩展类
        UserCustom userCustom = new UserCustom();
        BeanUtils.copyProperties(user, userCustom);
        RoleCustom roleCustom = new RoleCustom();
        roleCustom.setRoleGroup(roleGroup);
        userCustom.setRoleCustom(roleCustom);
        return userCustom;
    }

    @Override
    public void updateUser(UserCustom userCustom) throws Exception {
        RoleCustom roleCustom;
        if (null != userCustom && null != (roleCustom = userCustom.getRoleCustom())) {
            //先添加用户表(先判断账号唯一)
            int count = userCustomMapper.isExistUpdateUser(userCustom);
            if (count != 0) {
                throw new ServiceException(Constants.USERNAME_EXIST);
            }
            //更新用户
            userMapper.updateByPrimaryKeySelective((User) userCustom);
            //删除用户角色表
            userCustomMapper.deleteUserRole(userCustom.getUserId());
            insertUserRole(roleCustom, userCustom);

        } else {
            throw new ServiceException(Constants.REQUEST_DATA_ERROR);
        }
    }

    public void batchDeleteUser(List userIdList){
        //删除用户角色表
        userCustomMapper.batchDeleteUserRole(userIdList);
        //更新用户状态
        userCustomMapper.batchUpdateUser(userIdList);
    }

    public void insertUserRole(RoleCustom roleCustom, UserCustom userCustom) {
        String[] roleGroup = roleCustom.getRoleGroup().split(",");
        List<RoleCustom> roleCustomList = new ArrayList<>();
        Arrays.asList(roleGroup).parallelStream().forEach(s -> {
            RoleCustom roleCustom1 = new RoleCustom();
            roleCustom1.setRoleId(Integer.parseInt(s));
            User user = new User();
            user.setUserId(userCustom.getUserId());
            roleCustom1.setUser(user);
            roleCustomList.add(roleCustom1);
        });
        userCustomMapper.insertUserRole(roleCustomList);
    }
}