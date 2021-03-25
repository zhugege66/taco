package zhugege.cn.tacocloud.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import zhugege.cn.tacocloud.entity.User;
import zhugege.cn.tacocloud.service.UserService;
import zhugege.cn.tacocloud.util.JsonData;
import zhugege.cn.tacocloud.util.Log;
import zhugege.cn.tacocloud.util.RedisUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("user/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    KafkaTemplate<String,Object> kafkaTemplate;

    @GetMapping("getUserByName")
    public JsonData getUserByName(@RequestParam(value = "name",required = true)String name){

        User user = userService.getUserByName(name);
        if(user != null){
            return JsonData.buildSuccess(1,user);
        }else{
            return JsonData.buildError(0,"没有该用户！");
        }
    }

    @GetMapping("getAllUser")
    public JsonData getAllUser(){

        List<User> userList = userService.getAllUser();
        return JsonData.buildSuccess(1,userList);
    }

    @PostMapping("save")
    public JsonData save(@RequestBody Map<String,String> userInfo){

        int rows = userService.save(userInfo);
        if(rows == 0){
            return JsonData.buildError(0,"信息有误！");
        }else{
            return JsonData.buildSuccess(1,null);
        }
    }

    @PostMapping("update")
    public JsonData update(@RequestBody Map<String,String> newUserInfo){

        int rows = userService.update(newUserInfo);
        if(rows == 0){
            return  JsonData.buildError(0,"信息有误！");
        }else{
            return JsonData.buildSuccess(1,null);
        }
    }

    /***
     * 注销账户
     * @param name
     * @return JsonData
     */
    @GetMapping("closeAccount")
    public JsonData closeAccount(@RequestParam(value = "name",required = true) String name){

        return userService.delete(name) == 0 ? JsonData.buildError(0,"没有该用户！") :
                JsonData.buildSuccess(1,null);
    }


    /***
     * 测试Redis
     * @param name
     * @return
     */
    @GetMapping("redisTest")
    public JsonData redisTest(@RequestParam(value = "name",required = true) String name){
        boolean hasKey = redisUtil.hasKey(name);
        if(hasKey){
            Object object = redisUtil.get(name);
            Log.log.info("从缓存获取数据:" + object);
            return JsonData.buildSuccess(1,null);
        }else{
            Log.log.info("从数据库获取数据:");
            User user = userService.getUserByName(name);
            if(user != null){
                String str = user.getPwd();
                redisUtil.set(name,str,10);
                Log.log.info("数据写入缓存");
                return JsonData.buildSuccess(1,null);
            }else{
                return JsonData.buildError(0,"no people!");
            }
        }
    }

    /***
     * 测试Kafka
     */
    @GetMapping("kafkaTest")
    public JsonData sendMessage(@RequestParam(value = "name",required = true) String name){
        User user = userService.getUserByName(name);
        if(user != null){
            kafkaTemplate.send("demo",user.toString());
            return JsonData.buildSuccess(1,user);
        }else{
            return JsonData.buildError(0,"no people!");
        }
    }

}
