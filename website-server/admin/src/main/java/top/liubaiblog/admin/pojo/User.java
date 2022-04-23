package top.liubaiblog.admin.pojo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class User {
    private Long userId;

    @NotNull
    @Size(min = 5, max = 15, message = "用户名长度需要在5-15之间")
    private String userName;

    private String nickName;

    @Email
    private String email;

    private String phonenumber;

    private String sex;

    private String avatar;

    @NotNull
    @Size(min = 5, max = 32, message = "密码不符合要求")
    private String password;

    private String status;

    private String delFlag;

    private String loginIp;

    private Date loginDate;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    // 用户拥有的角色
    private List<Role> roles;
}