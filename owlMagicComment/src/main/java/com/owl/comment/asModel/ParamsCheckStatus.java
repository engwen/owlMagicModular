package com.owl.comment.asModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求参数校验状态
 * author engwen
 * email xiachanzou@outlook.com
 * 2021/3/23.
 */
public class ParamsCheckStatus {
    //requestParams
    private String[] paramsNotNull;
    private String[] paramsNotAllNull;
    private boolean paramsHasNull = false;
    private boolean paramsAllOrNull = true;

    //requestBody
    private String[] bodyNotNull;
    private String[] bodyNotAllNull;
    private boolean bodyHasNull = false;
    private boolean bodyAllOrNull = true;

    private List<String> requestParamsIsNull = new ArrayList<>();
    private List<String> requestBodyIsNull = new ArrayList<>();

    public String[] getParamsNotNull() {
        return paramsNotNull;
    }

    public void setParamsNotNull(String[] paramsNotNull) {
        this.paramsNotNull = paramsNotNull;
    }

    public String[] getParamsNotAllNull() {
        return paramsNotAllNull;
    }

    public void setParamsNotAllNull(String[] paramsNotAllNull) {
        this.paramsNotAllNull = paramsNotAllNull;
    }

    public boolean isParamsHasNull() {
        return paramsHasNull;
    }

    public void setParamsHasNull(boolean paramsHasNull) {
        this.paramsHasNull = paramsHasNull;
    }

    public boolean isParamsAllOrNull() {
        return paramsAllOrNull;
    }

    public void setParamsAllOrNull(boolean paramsAllOrNull) {
        this.paramsAllOrNull = paramsAllOrNull;
    }

    public String[] getBodyNotNull() {
        return bodyNotNull;
    }

    public void setBodyNotNull(String[] bodyNotNull) {
        this.bodyNotNull = bodyNotNull;
    }

    public String[] getBodyNotAllNull() {
        return bodyNotAllNull;
    }

    public void setBodyNotAllNull(String[] bodyNotAllNull) {
        this.bodyNotAllNull = bodyNotAllNull;
    }

    public boolean isBodyHasNull() {
        return bodyHasNull;
    }

    public void setBodyHasNull(boolean bodyHasNull) {
        this.bodyHasNull = bodyHasNull;
    }

    public boolean isBodyAllOrNull() {
        return bodyAllOrNull;
    }

    public void setBodyAllOrNull(boolean bodyAllOrNull) {
        this.bodyAllOrNull = bodyAllOrNull;
    }

    public List<String> getRequestParamsIsNull() {
        return requestParamsIsNull;
    }

    public void setRequestParamsIsNull(List<String> requestParamsIsNull) {
        this.requestParamsIsNull = requestParamsIsNull;
    }

    public boolean addNullParams(String paramsName) {
        return requestParamsIsNull.add(paramsName);
    }

    public List<String> getRequestBodyIsNull() {
        return requestBodyIsNull;
    }

    public void setRequestBodyIsNull(List<String> requestBodyIsNull) {
        this.requestBodyIsNull = requestBodyIsNull;
    }

    public boolean addNullBody(String paramsName) {
        return requestBodyIsNull.add(paramsName);
    }

}
