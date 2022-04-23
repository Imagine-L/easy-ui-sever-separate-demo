package top.liubaiblog.admin.wapper;

import top.liubaiblog.admin.util.XssUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 刘佳俊
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final HttpServletRequest originalReq;

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.originalReq = request;
    }

    @Override
    public String getParameter(String name) {
        // 获取参数
        String parameter = super.getParameter(XssUtil.xssEncode(name));
        // 处理参数(防止xss脚本)
        return XssUtil.xssEncode(parameter);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameterMap = super.getParameterMap();
        Map<String, String[]> newParameterMap = new HashMap<>();

        parameterMap.forEach((key, values) -> {
            // 处理key
            String newKey = XssUtil.xssEncode(key);
            // 处理value
            String[] newParameterValues = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                newParameterValues[i] = XssUtil.xssEncode(values[i]);
            }
            // 插入到新的map中
            newParameterMap.put(newKey, newParameterValues);
        });

        return newParameterMap;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameterValues = super.getParameterValues(XssUtil.xssEncode(name));
        String[] newParameterValues = new String[parameterValues.length];
        for (int i = 0; i < parameterValues.length; i++) {
            newParameterValues[i] = XssUtil.xssEncode(parameterValues[i]);
        }
        return newParameterValues;
    }

    public HttpServletRequest getOriginalReq() {
        return originalReq;
    }
}
