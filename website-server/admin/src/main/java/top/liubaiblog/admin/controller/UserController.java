package top.liubaiblog.admin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.liubaiblog.admin.annotation.HasPermission;
import top.liubaiblog.admin.annotation.HasRole;
import top.liubaiblog.admin.annotation.Log;
import top.liubaiblog.admin.annotation.StopRepeat;
import top.liubaiblog.admin.constant.Constant;
import top.liubaiblog.admin.pojo.LoginUser;
import top.liubaiblog.admin.pojo.User;
import top.liubaiblog.admin.service.user.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘佳俊
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    public Object test() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "12");
        return map;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUser> login(@RequestBody @Validated User user, BindingResult bindingResult, HttpServletRequest req) {
        if (bindingResult.getAllErrors().size() > 0) {
            return ResponseEntity.status(400).build();
        }
        LoginUser loginUser = null;
        try {
            loginUser = userService.login(user.getUserName(), user.getPassword());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }

        return ResponseEntity.ok(loginUser);
    }


    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        try {
            // 移除token
            if (userService.logout()) {
                return ResponseEntity.ok("logout success");
            } else {
                return ResponseEntity.status(500).body("remove token fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/checkToken")
    public ResponseEntity<String> checkToken() {
        // 只要能通过拦截器，说明token就是合法的，可以直接放行
        return ResponseEntity.ok("token legal");
    }

    @GetMapping("/userList")
//    @StopRepeat(100)
    @Log(title = "请求用户数据", businessType = "用户操作")
    public Object userList(@RequestParam(required = false, defaultValue = "1") int pageNo,
                           @RequestParam(required = false, defaultValue = "2") int pageSize,
                           @RequestParam(required = false) String userId,
                           @RequestParam(required = false) String nickName) {
        return userService.selectUserForPage(pageNo, pageSize, userId, nickName);
    }

    @GetMapping("/getInfo")
    public ResponseEntity<Object> getInfo() {
        try {
            Map<String, List<String>> userInfo = userService.getUserInfo();
            return ResponseEntity.ok(userInfo);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/getUser/{id}")
//    @HasRole("admin")
    public User getUser(@PathVariable("id") Integer id) {
        return userService.selectUserById(id);
    }

    @PostMapping("/saveUser")
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        try {
            if (userService.saveUser(user)) {
                return ResponseEntity.ok("success");
            } else {
                throw new RuntimeException("添加用户失败");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }


    @PutMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        try {
            if (userService.updateUser(user)) {
                return ResponseEntity.ok("success");
            } else {
                throw new RuntimeException("修改用户失败");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Integer userId) {
        try {
            if (userService.deleteUser(userId)) {
                return ResponseEntity.ok("success");
            } else {
                throw new RuntimeException("删除用户失败");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
