package top.liubaiblog.admin.pojo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class OperLog {
    private Integer operId;

    private String title;

    private String businessType;

    private String method;

    private String requestMethod;

    private String operName;

    private String operUrl;

    private String operIp;

    private Integer status;

    private String errormsg;

    private Date opertime;
}