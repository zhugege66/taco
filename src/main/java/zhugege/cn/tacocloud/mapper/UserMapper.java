package zhugege.cn.tacocloud.mapper;

import org.apache.ibatis.annotations.Param;
import zhugege.cn.tacocloud.entity.User;

import java.util.List;


public interface UserMapper {

    List<User> getAllUser();
    User getUserByName(@Param("name") String name);
    int save(User user);
    int update(User user);
    int delete(@Param("name")String name);
}
