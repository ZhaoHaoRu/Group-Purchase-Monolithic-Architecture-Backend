package com.example.groupbuy.utils.sessionUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {

    public static void setSession(JSONObject data) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 获取Request实例
        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            HttpSession session = request.getSession();
            for (Object thisKey : data.keySet()) {   //返回所有key的列表
                String key = (String) thisKey;
                Object val = data.get(key);
                session.setAttribute(key, val);
            }
        }
    }

    public static boolean removeSession() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
                return true;
            }
        }
        return false;
    }

    public static Boolean checkAuth() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            HttpSession session = request.getSession(false);
            if (session != null) {
                Integer userIdentity = (Integer) session.getAttribute("userType");
                return userIdentity != null && userIdentity >= 0;
            }
        }
        return false;
    }

    public static JSONObject getAuth() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            HttpSession session = request.getSession();
            if (session != null) {
                JSONObject authObject = new JSONObject();
                authObject.put("userId", session.getAttribute("userId"));
                authObject.put("username",  session.getAttribute("username"));
                authObject.put("userType", session.getAttribute("userType"));
                return authObject;
            }
        }
        return null;
    }

    public static Long getUserId() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            HttpSession session = request.getSession(false);
            if (session != null){
                return (Long) session.getAttribute("userId");
            }
        }
        return null;
    }
}
