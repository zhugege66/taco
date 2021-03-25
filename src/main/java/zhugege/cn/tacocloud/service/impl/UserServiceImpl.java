package zhugege.cn.tacocloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhugege.cn.tacocloud.entity.User;
import zhugege.cn.tacocloud.mapper.UserMapper;
import zhugege.cn.tacocloud.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByName(String name) {

        User user = userMapper.getUserByName(name);
        return user;
    }

    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }

    @Override
    public int save(Map<String, String> userInfo){

        User user = parseToUser(userInfo);
        if(user == null){
            return 0;
        }else{
            if(getUserByName(user.getName()) != null){
                return 0;
            }
            return userMapper.save(user);
        }
    }


    @Override
    public int update(Map<String, String> newUserInfo) {

        User user = parseToUser(newUserInfo);
        if(user == null){
            return 0;
        }else{
            if(getUserByName(user.getName()) == null){
                return 0;
            }else{
                return userMapper.update(user);
            }
        }
    }

    @Override
    public int delete(String name) {

        User user = userMapper.getUserByName(name);
        if(user == null){
            return 0;
        }else{
            return userMapper.delete(name);
        }

    }

    private User parseToUser(Map<String, String> userInfo){
        if(userInfo.containsKey("name") &&
           userInfo.containsKey("pwd")){
            User user = new User();
            user.setName(userInfo.get("name"));
            user.setPwd(userInfo.get("pwd"));
            user.setCreateTime(new Date());
            return user;
        }else{
            return null;
        }
    }
}
