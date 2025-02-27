package com.owl.file.excel.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2024/11/14.
 */
public class ExcelImportObj {
    private List<ExcelColumnValid> excelColumnValidList;
    //标题行
    private Integer yTitle;
    //数据起始行
    private Integer yValue;

    public ExcelImportObj() {
        this.excelColumnValidList = new ArrayList<>();
    }

    /**
     * 添加对应属性单位到导入对象中
     * @param x         excel中第几列（从0开始）
     * @param validType 0字符串，1正整数，2浮点数，3金额,4 yyyy-mm-dd日期
     * @param isMust    是否必填
     * @param title     对应excel中的属性中文名称
     * @param field     对应的对象中的属性
     */
    public void addToList(Integer x, Integer validType, boolean isMust, String title, String field) {
        ExcelColumnValid valid = new ExcelColumnValid(x, validType, isMust, title, field);
        this.addToList(valid);
    }


    /**
     * 添加对应属性单位到导入对象中
     * @param x          excel中第几列（从0开始）
     * @param validType  0字符串，1正整数，2浮点数，3金额,4 yyyy-mm-dd日期
     * @param isMust     是否必填
     * @param title      对应excel中的属性中文名称
     * @param field      对应的对象中的属性
     * @param excelCheckParam 校验函数
     */
    public void addToList(Integer x, Integer validType, boolean isMust, String title, String field, ExcelCheckParam excelCheckParam) {
        ExcelColumnValid valid = new ExcelColumnValid(x, validType, isMust, title, field);
        valid.setExcelCheckParams(excelCheckParam);
        this.addToList(valid);
    }

    /**
     * 添加对应属性单位到导入对象中
     * @param x         excel中第几列（从0开始）
     * @param validType 0字符串，1正整数，2浮点数，3金额,4 yyyy-mm-dd日期
     * @param isMust    是否必填
     * @param title     对应excel中的属性中文名称
     * @param field     对应的对象中的属性
     * @param lenType   长度类型，列内容应当，-1：小于指定长度 0：=指定长度，1：大于指定长度
     * @param len       此属性字段最大长度
     */
    public void addToList(Integer x, Integer validType, boolean isMust, String title, String field, Integer lenType, Integer len) {
        ExcelColumnValid valid = new ExcelColumnValid(x, validType, isMust, title, field, lenType, len);
        this.addToList(valid);
    }

    /**
     * 添加对应属性单位到导入对象中
     * @param x         excel中第几列（从0开始）
     * @param validType 0字符串，1正整数，2浮点数，3金额,4 yyyy-mm-dd日期
     * @param isMust    是否必填
     * @param title     对应excel中的属性中文名称
     * @param field     对应的对象中的属性
     * @param isMapper  是否有映射关系，如填入 是，对应取 1，填入否则取0
     * @param filedMap  映射关系，是：1.否：0
     */
    public void addToList(Integer x, Integer validType, boolean isMust, String title, String field, boolean isMapper, Map<String, String> filedMap) {
        ExcelColumnValid valid = new ExcelColumnValid(x, validType, isMust, title, field, isMapper, filedMap);
        this.addToList(valid);
    }

    /**
     * 添加对应属性单位到导入对象中
     * @param x         excel中第几列（从0开始）
     * @param validType 0字符串，1正整数，2浮点数，3金额,4 yyyy-mm-dd日期
     * @param isMust    是否必填
     * @param title     对应excel中的属性中文名称
     * @param field     对应的对象中的属性
     * @param isInner   是否存在包含关系，比如取值必须在T00,T01,T03之间
     * @param inners    取值范围
     */
    public void addToList(Integer x, Integer validType, boolean isMust, String title, String field, boolean isInner, String... inners) {
        ExcelColumnValid valid = new ExcelColumnValid(x, validType, isMust, title, field, isInner, inners);
        this.addToList(valid);
    }

    /**
     * 添加对应属性单位到导入对象中
     * @param x         excel中第几列（从0开始）
     * @param validType 0字符串，1正整数，2浮点数，3金额,4 yyyy-mm-dd日期
     * @param isMust    是否必填
     * @param title     对应excel中的属性中文名称
     * @param field     对应的对象中的属性
     * @param isHyper   是否存在依赖关系。如该列存在时，此列必填
     * @param xHyper    依赖属性所在列。
     * @param lenType   长度类型，列内容应当，-1：小于指定长度 0：=指定长度，1：大于指定长度
     * @param len       此属性字段最大长度
     */
    public void addToList(Integer x, Integer validType, boolean isMust, String title, String field, boolean isHyper, Integer xHyper, Integer lenType, Integer len) {
        ExcelColumnValid valid = new ExcelColumnValid(x, validType, isMust, title, field, isHyper, xHyper, lenType, len);
        this.addToList(valid);
    }

    /**
     * @param x         excel中第几列（从0开始）
     * @param validType 0字符串，1正整数，2浮点数，3金额,4 yyyy-mm-dd日期
     * @param isMust    是否必填
     * @param title     对应excel中的属性中文名称
     * @param field     对应的对象中的属性
     * @param isMapper  是否有映射关系，如填入 是，对应取 1，填入否则取0
     * @param filedMap  映射关系，是：1.否：0
     * @param isHyper   是否存在依赖关系。如该列存在时，此列必填
     * @param xHyper    依赖属性所在列。
     */
    public void addToList(Integer x, Integer validType, boolean isMust, String title, String field, boolean isMapper, Map<String, String> filedMap, boolean isHyper, Integer xHyper) {
        ExcelColumnValid valid = new ExcelColumnValid(x, validType, isMust, title, field, isMapper, filedMap, isHyper, xHyper);
        this.addToList(valid);
    }

    public void addToList(ExcelColumnValid valid) {
        this.excelColumnValidList.add(valid);
    }

    public List<ExcelColumnValid> getExcelColumnValidList() {
        return excelColumnValidList;
    }

    public void setExcelColumnValidList(List<ExcelColumnValid> excelColumnValidList) {
        this.excelColumnValidList = excelColumnValidList;
    }

    public Integer getYTitle() {
        return yTitle;
    }

    public void setYTitle(Integer yTitle) {
        this.yTitle = yTitle;
    }

    public Integer getYValue() {
        return yValue;
    }

    public void setYValue(Integer yValue) {
        this.yValue = yValue;
    }
}
