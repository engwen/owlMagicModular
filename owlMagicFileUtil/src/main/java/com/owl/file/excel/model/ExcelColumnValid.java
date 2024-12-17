package com.owl.file.excel.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 列属性
 * author engwen
 * email xiachanzou@outlook.com
 * 2024/11/14.
 */
public class ExcelColumnValid {
    //列
    private Integer x;
    //是否正整数 0,字符串，1正整数，2浮点数，3金额,4 yyyy-mm-dd日期
    private Integer validType;
    //是否必填
    private boolean isMust;
    //标题
    private String title;
    //对象属性名
    private String field;
    //长度类型，列内容应当，-1：小于指定长度 0：=指定长度，1：大于指定长度
    private Integer lenType;
    //长度
    private Integer len;
    //是否需要转换值
    private boolean isMapper;
    //对象映射属性，是-》1 ，否-》0
    private Map<String, String> filedMap;
    //是否关联字段，当该字段存在时，本字段不能为空使用
    private boolean isHyper;
    //关联字段所在列
    private Integer xHyper;
    //是否归属于指定值
    private boolean isInner;
    //当归属于指定值时，此字段必填
    private List<String> innerList;
    //指定测试方式，结果
    private ExcelCheckParam excelCheckParam;

    public ExcelColumnValid(Integer x, Integer validType, boolean isMust, String title, String field) {
        this.x = x;
        this.validType = validType;
        this.isMust = isMust;
        this.title = title;
        this.field = field;
        this.isMapper = false;
        this.isInner = false;
        this.isHyper = false;
    }

    public ExcelColumnValid(Integer x, Integer validType, boolean isMust, String title, String field, Integer lenType, Integer len) {
        this.x = x;
        this.validType = validType;
        this.isMust = isMust;
        this.title = title;
        this.field = field;
        this.isMapper = false;
        this.isInner = false;
        this.isHyper = false;
        this.lenType = lenType;
        this.len = len;
    }

    public ExcelColumnValid(Integer x, Integer validType, boolean isMust, String title, String field, boolean isHyper, Integer xHyper, Integer lenType, Integer len) {
        this.x = x;
        this.validType = validType;
        this.isMust = isMust;
        this.title = title;
        this.field = field;
        this.isMapper = false;
        this.isInner = false;
        this.isHyper = isHyper;
        this.xHyper = xHyper;
        this.lenType = lenType;
        this.len = len;
    }

    public ExcelColumnValid(Integer x, Integer validType, boolean isMust, String title, String field, boolean isMapper, Map<String, String> filedMap) {
        this.x = x;
        this.validType = validType;
        this.isMust = isMust;
        this.title = title;
        this.field = field;
        this.isMapper = isMapper;
        this.isInner = false;
        this.filedMap = filedMap;
        this.isHyper = false;
    }

    public ExcelColumnValid(Integer x, Integer validType, boolean isMust, String title, String field, boolean isInner, String... inners) {
        this.x = x;
        this.validType = validType;
        this.isMust = isMust;
        this.title = title;
        this.field = field;
        this.isInner = isInner;
        this.innerList = Arrays.asList(inners);
        this.isHyper = false;
    }

    public ExcelColumnValid(Integer x, Integer validType, boolean isMust, String title, String field, boolean isMapper, Map<String, String> filedMap, boolean isHyper, Integer xHyper) {
        this.x = x;
        this.validType = validType;
        this.isMust = isMust;
        this.title = title;
        this.field = field;
        this.isInner = false;
        this.isMapper = isMapper;
        this.filedMap = filedMap;
        this.isHyper = isHyper;
        this.xHyper = xHyper;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getValidType() {
        return validType;
    }

    public void setValidType(Integer validType) {
        this.validType = validType;
    }

    public boolean isMust() {
        return isMust;
    }

    public void setMust(boolean must) {
        isMust = must;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getLenType() {
        return lenType;
    }

    public void setLenType(Integer lenType) {
        this.lenType = lenType;
    }

    public Integer getLen() {
        return len;
    }

    public void setLen(Integer len) {
        this.len = len;
    }

    public boolean isMapper() {
        return isMapper;
    }

    public void setMapper(boolean mapper) {
        isMapper = mapper;
    }

    public Map<String, String> getFiledMap() {
        return filedMap;
    }

    public void setFiledMap(Map<String, String> filedMap) {
        this.filedMap = filedMap;
    }

    public boolean isHyper() {
        return isHyper;
    }

    public void setHyper(boolean hyper) {
        isHyper = hyper;
    }

    public Integer getXHyper() {
        return xHyper;
    }

    public void setXHyper(Integer xHyper) {
        this.xHyper = xHyper;
    }

    public boolean isInner() {
        return isInner;
    }

    public void setInner(boolean inner) {
        isInner = inner;
    }

    public List<String> getInnerList() {
        return innerList;
    }

    public void setInnerList(List<String> innerList) {
        this.innerList = innerList;
    }

    public ExcelCheckParam getCheckParams() {
        return excelCheckParam;
    }

    public void setCheckParams(ExcelCheckParam excelCheckParam) {
        this.excelCheckParam = excelCheckParam;
    }
}
