package com.owl.mvc.model;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2018/11/23.
 */
public class MsgConstant {
    private String code;
    private String msg;

    public static MsgConstant REQUEST_SUCCESS = new MsgConstant("0000", "请求成功");
    public static MsgConstant REQUEST_DEFAULT = new MsgConstant("0001", "默认返回数据");
    public static MsgConstant REQUEST_PARAMETER_ERROR = new MsgConstant("0002", "请求参数错误");
    public static MsgConstant REQUEST_NO_KNOW_ERROR = new MsgConstant("0003", "未定义错误");
    public static MsgConstant REQUEST_NOT_EXITS = new MsgConstant("0008", "请求数据不存在");
    public static MsgConstant REQUEST_IS_EXITS = new MsgConstant("0009", "数据已经存在");
    public static MsgConstant REQUEST_METHOD_NOT_EXITS = new MsgConstant("0011", "接口不存在. 请检查代码.");
    public static MsgConstant REQUEST_BACK_ARE_LIST = new MsgConstant("0013", "存在符合结果的数据集合");

    public static MsgConstant STR_NOT_ARE_JSON = new MsgConstant("0030", "此字符串不是JSON格式");
    public static MsgConstant NOT_FIND_PROPERTIES = new MsgConstant("0031", "The properties not find");


    public static MsgConstant REQUEST_NO_SIGNUP = new MsgConstant("1002", "账号不存在, 请先注册");
    public static MsgConstant REQUEST_NO_SIGNIN = new MsgConstant("1003", "请先登录");
    public static MsgConstant REQUEST_ACCOUNT_OR_PASSWORD_ERROR = new MsgConstant("1004", "账号或密码错误");
    public static MsgConstant CONTROLLER_THROWABLE_ERROR = new MsgConstant("1005", "操作失败");
    public static MsgConstant TRY_CATCH_THROWABLE_ERROR = new MsgConstant("1006", " 这个网站好像有问题");
    public static MsgConstant REQUEST_PERMISSION_DEFINED = new MsgConstant("1007", "权限拒绝");
    public static MsgConstant PARAM_EMAIL_ERROR = new MsgConstant("1008", "email 格式错误");
    public static MsgConstant PARAM_MOBILE_ERROR = new MsgConstant("1009", "手机格式错误");
    public static MsgConstant PARAM_ACCOUNT_ERROR = new MsgConstant("1010", "账号格式错误");
    public static MsgConstant REQUEST_CANT_UPDATE_ADMIN = new MsgConstant("1011", "admin账号不可更新");
    public static MsgConstant REQUEST_CANT_UPDATE_STATUS = new MsgConstant("1012", "状态不可更新");


    public MsgConstant(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
