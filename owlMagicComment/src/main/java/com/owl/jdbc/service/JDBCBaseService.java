package com.owl.jdbc.service;


import com.owl.jdbc.model.JDBCConn;

import java.util.List;
import java.util.Map;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2022/12/30.
 */
public interface JDBCBaseService {
    /**
     * 查询总条数
     * @param tableName 表面
     * @return 结果
     */
    Integer countRecord(String tableName);

    String buildSelectPageSql(String tableName, String orderBy, String where, Integer upLimit, Integer downLimit, Integer size);

    /**
     * 查询列表
     * @param tableName 表名
     * @param where     条件
     * @return 结果
     */
    List<Map<String, String>> selectList(String tableName, String where);

    /**
     * 分页查询
     * @param tableName 表面
     * @param orderBy   排序
     * @param where     条件
     * @param upLimit   起始点
     * @param downLimit 结束点
     * @param size      查询条数
     * @return 结果
     */
    List<Map<String, String>> selectByPage(String tableName, String orderBy, String where, Integer upLimit, Integer downLimit, Integer size);

    /**
     * 插入
     * @param tableName 表名
     * @param params    params
     */
    void insertList(String tableName, List<Map<String, String>> params);

    /**
     * 复制表
     * @param sourceTableName 源表名
     * @param toTableName     到表名
     * @param orderBy         排序
     * @param source          源数据库连接
     * @param aim             目标数据库连接
     * @param mapping         映射关系
     */
    void copyTableValue(String sourceTableName, String toTableName, String orderBy, JDBCConn source, JDBCConn aim, Map<String, String> mapping);
}
