package com.sky.service.impl;

import java.util.Map;
import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;

@Service
public class UserServiceImpl implements UserService {

    // 微信登录接口
    public static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;

        // 新增用户方法  
    @Override  
    public void addUser(User user) {  
        // 校验用户信息  
        if (user == null || user.getName() == null || user.getName().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()) {  
            throw new IllegalArgumentException("用户信息不能为空");  
        }  
        // 调用Mapper层方法添加用户  
        userMapper.insertUser(user);  
    }  
  
    // 更新用户方法  
    @Override  
    public void updateUser(User user) {  
        // 校验用户信息  
        if (user == null || user.getId() == null || user.getName() == null || user.getName().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()) {  
            throw new IllegalArgumentException("用户信息不能为空");  
        }  
        // 调用Mapper层方法更新用户  
        userMapper.updateUser(user);  
    }  
  
    // 删除用户方法  
    @Override  
    public void deleteUser(Long id) {  
        // 校验用户ID  
        if (id == null) {  
            throw new IllegalArgumentException("用户ID不能为空");  
        }  
        // 调用Mapper层方法删除用户  
        userMapper.deleteUser(id);  
    }  
  
    // 根据ID查询用户方法  
    @Override  
    public User getUserById(Long id) {  
        // 校验用户ID  
        if (id == null) {  
            throw new IllegalArgumentException("用户ID不能为空");  
        }  
        // 调用Mapper层方法根据ID查询用户  
        return userMapper.getUserById(id);  
    }  
  
    // 根据用户名查询用户方法  
    @Override  
    public User getUserByName(String name) {  
        // 校验用户名  
        if (name == null || name.isEmpty()) {  
            throw new IllegalArgumentException("用户名不能为空");  
        }  
        // 调用Mapper层方法根据用户名查询用户  
        return userMapper.getUserByName(name);  
    }  
  
    // 查询所有用户方法  
    @Override  
    public List<User> getAllUsers() {  
        // 调用Mapper层方法查询所有用户  
        return userMapper.getAllUsers();  
    }  

  
    // 根据用户ID和姓名查询用户  
    public User getUserByIdAndName(Long id, String name) {  
        // 校验参数  
        if (id == null || name == null || name.isEmpty()) {  
            throw new IllegalArgumentException("用户ID或用户名不能为空");  
        }  
        // 调用Mapper层方法根据ID和姓名查询用户  
        // 假设Mapper层有对应的方法 getUserByIdAndName  
        return userMapper.getUserByIdAndName(id, name);  
    }  
  
    // 根据用户状态查询用户列表  
    public List<User> getUsersByStatus(Integer status) {  
        // 校验参数  
        if (status == null) {  
            throw new IllegalArgumentException("用户状态不能为空");  
        }  
        // 调用Mapper层方法根据状态查询用户列表  
        // 假设Mapper层有对应的方法 getUsersByStatus  
        return userMapper.getUsersByStatus(status);  
    }  
  
    // 统计用户数量  
    public int countUsers() {  
        // 调用Mapper层方法统计用户数量  
        // 假设Mapper层有对应的方法 countUsers  
        return userMapper.countUsers();  
    }  

    /**
     * 微信登录
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        // 调用微信用户接口，获取openid
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("appid", weChatProperties.getAppid());
        reqParams.put("secret", weChatProperties.getSecret());
        reqParams.put("js_code", userLoginDTO.getCode());
        reqParams.put("grant_type", "authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN_URL, reqParams);

        JSONObject parseJson = JSON.parseObject(json);
        String openid = parseJson.getString("openid");

        // 判断 openid 是否为空，如果为空，抛出异常
        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        // 是否在用户表中存在，如果不存在为新用户，插入用户表
        User user = userMapper.getByOpenid(openid);
        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();

            userMapper.insert(user);
        }

        // 返回用户信息
        return user;
    }

}
