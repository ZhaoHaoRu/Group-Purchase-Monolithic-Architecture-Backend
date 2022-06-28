package com.example.groupbuy.utils.messageUtils;
/**
 * @author yz
 */
public class MessageUtil {

  public static final String LOGIN_ERROR_MSG = "密码或用户名有误，请重新输入！";
  public static final String LOGIN_BAN_MSG ="您已被禁用！";
  public static final String LOGIN_SUCCESS_MSG = "欢迎回来";
  public static final String ALREADY_LOGIN_MSG = "用户已经登陆。";
  public static final String NOT_LOGIN_MSG = "出现问题请刷新";
  public static final String LOGOUT_SUCCESS_MSG = "出现问题请刷新";
  public static final String LOGOUT_ERROR_MSG = "退出登录失败！";
  public static final String REGISTER_ERROR_MSG = "此用户名已注册，请更换其他用户名！";
  public static final String REGISTER_SUCCESS_MSG = "注册成功!";
  public static final String NOT_ALLOW_MSG="当前用户非管理员，不能访问此页面。";
  public static final String SUCCESS="操作成功";
  public static final String NOTENOUGHSTOCK="库存不够";
  public static final String NOTLOGGEDIN="没有登录";


  public static final int LOGIN_ERROR_CODE = -1;
  public static final int LOGIN_SUCCESS_CODE = 1;
  public static final int NOT_LOGIN_CODE = -2;
  public static final int ALREADY_LOGIN_CODE = 0;
  public static final int LOGOUT_SUCCESS_CODE = 2;
  public static final int LOGOUT_ERROR_CODE = -3;
  public static final int REGISTER_ERROR_CODE = -4;
  public static final int REGISTER_SUCCESS_CODE = 4;
  public static final int NOT_ALLOW_CODE=5;

  public static <T> Message<T> createMessage(int statusCode, String message) {
    return new Message<T>(statusCode, message);
  }

  public static <T> Message<T> createMessage(int statusCode, String message, T data) {
    return new Message<T>(statusCode, message, data);
  }
}
