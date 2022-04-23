package top.liubaiblog.admin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.liubaiblog.admin.config.redis.RedisTemplate;
import top.liubaiblog.admin.mapper.UserMapper;
import top.liubaiblog.admin.pojo.User;
import top.liubaiblog.admin.service.user.UserService;
//import top.liubaiblog.admin.config.redis.RedisTemplate

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class AdminApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        redisTemplate.setObject("people", Arrays.asList("jack", "mary", "tom"), -1L);
        Optional<List<String>> optionalList = redisTemplate.getObject("people", new TypeReference<List<String>>() {});
        optionalList.get().forEach(System.out::println);
    }

    @Test
    public void test() {
//        PageInfo<User> userPageInfo = userService.selectUserForPage(2, 2);
//        List<User> list = userPageInfo.getList();
//        System.out.println("pageInfo: ");
//        System.out.println(userPageInfo);
//        System.out.println("listUser");
//        list.forEach(System.out::println);
        List<User> users = userMapper.selectUserByConditional("1", "管理");
        System.out.println(users.size());
        System.out.println(users);
    }

    @Test
    public void test2() {
        User userInfoById = userMapper.getUserInfoById(1L);
        System.out.println(userInfoById);
    }

}
