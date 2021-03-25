package zhugege.cn.tacocloud.service;

import zhugege.cn.tacocloud.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User getUserByName(String name);

    List<User> getAllUser();

    int save(Map<String,String> userInfo);

    int update(Map<String,String> newUserInfo);

    int delete(String name);
}
