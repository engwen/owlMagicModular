package com.owl.mvc.model;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2018/11/23.
 */
public class MsgConstant {
    private String code;
    private String msg;

    public static MsgConstant REQUEST_SUCCESS = new MsgConstant("0000", "request success");
    public static MsgConstant REQUEST_DEFAULT = new MsgConstant("0001", "default return data");
    public static MsgConstant REQUEST_PARAMETER_ERROR = new MsgConstant("0002", "request parameter error");
    public static MsgConstant REQUEST_NO_KNOW_ERROR = new MsgConstant("0003", "unknown error");
    public static MsgConstant REQUEST_NOT_EXITS = new MsgConstant("0008", "No required data");
    public static MsgConstant REQUEST_IS_EXITS = new MsgConstant("0009", "This data already exists");
    public static MsgConstant REQUEST_METHOD_NOT_EXITS = new MsgConstant("0011", "This method (interface) does not exist. Please check the code.");
    public static MsgConstant REQUEST_BACK_ARE_LIST = new MsgConstant("0013", "Qualified data are not unique");

    public static MsgConstant STR_NOT_ARE_JSON = new MsgConstant("0030","The string can`t match JSON type");
    public static MsgConstant NOT_FIND_PROPERTIES = new MsgConstant("0031","The properties not find");



    public static MsgConstant REQUEST_NO_SIGNUP = new MsgConstant("1002", "not find this account, if you first use, please signup");
    public static MsgConstant REQUEST_NO_SIGNIN = new MsgConstant("1003", "your are not login,please signin");
    public static MsgConstant REQUEST_ACCOUNT_OR_PASSWORD_ERROR = new MsgConstant("1004", "account or password error");
    public static MsgConstant CONTROLLER_THROWABLE_ERROR = new MsgConstant("1005", "operation failed");
    public static MsgConstant TRY_CATCH_THROWABLE_ERROR = new MsgConstant("1006", "There seems to be something wrong with the website.");
    public static MsgConstant REQUEST_PERMISSION_DEFINED = new MsgConstant("1007", "permission defined");
    public static MsgConstant PARAM_EMAIL_ERROR = new MsgConstant("1008", "email presentation error");
    public static MsgConstant PARAM_MOBILE_ERROR = new MsgConstant("1009", "mobile presentation error");
    public static MsgConstant PARAM_ACCOUNT_ERROR = new MsgConstant("1010", "account presentation error");
    public static MsgConstant REQUEST_CANT_UPDATE_ADMIN = new MsgConstant("1011","can`t update admin");
    public static MsgConstant REQUEST_CANT_UPDATE_STATUS = new MsgConstant("1012","can`t update status");


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
