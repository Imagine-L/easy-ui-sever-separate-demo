package top.liubaiblog.admin.pojo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Role {
    private Long roleId;

    private String roleName;

    private String roleTag;

    private Integer roleSort;

    private String status;

    private String delFlag;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private List<Menu> menus;
}