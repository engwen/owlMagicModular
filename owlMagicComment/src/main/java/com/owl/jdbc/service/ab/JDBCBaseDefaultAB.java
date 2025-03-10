package com.owl.jdbc.service.ab;


import com.owl.jdbc.model.JDBCConn;
import com.owl.jdbc.utils.PageUtil;
import com.owl.util.LogUtil;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 公共实现
 * author engwen
 * email xiachanzou@outlook.com
 * 2022/12/30.
 */
public abstract class JDBCBaseDefaultAB {
    private static final Logger logger = Logger.getLogger(JDBCBaseDefaultAB.class.getName());
    protected JDBCConn jdbcConn;

    /**
     * 查询总条数
     * @param tableName 表面
     * @return 结果
     */
    public Integer countRecord(String tableName) {
        int recordSize = 0;
        try {//try catch判断是否有异常
            Statement statement = jdbcConn.getConn().createStatement();//创建sql语句
            String sql = " select count(*) as pg_all FROM " + tableName + " ";
            ResultSet rs = statement.executeQuery(sql);//执行sql语句
            String pgAll = rs.getString("pg_all");
            recordSize = Integer.parseInt(pgAll);
            rs.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("获取总数：" + recordSize);
        return recordSize;
    }

    /**
     * 查询列表
     * @param tableName 表名
     * @param where     条件
     * @return 结果
     */
    public List<Map<String, String>> selectList(String tableName, String where) {
        List<Map<String, String>> result = new ArrayList<>();
        try {//try catch判断是否有异常
            Statement statement = jdbcConn.getConn().createStatement();//创建sql语句
            ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName + " " + where);//执行sql语句
            //下面根据该table输出属性组和所有元组
            ResultSetMetaData rsmd = rs.getMetaData();//获取属性名
            String[] keys = new String[0];
            if (rsmd != null) {
                int count = rsmd.getColumnCount();//统计属性个数
                keys = new String[count];
                for (int i = 1; i <= count; i++) {
                    keys[i - 1] = rsmd.getColumnName(i);//把属性名输入arr
                }
            }
            while (rs.next()) {
                //判断是否存在下一行，如果存在，就使用foreach循环输出元组
                Map<String, String> temp = new HashMap<>();
                for (String key : keys) {
                    temp.put(key.toLowerCase(), rs.getString(key));
                }
                result.add(temp);
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

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
    public List<Map<String, String>> selectByPage(String tableName, String orderBy, String where, Integer upLimit, Integer downLimit, Integer size) {
        List<Map<String, String>> result = new ArrayList<>();
        try {//try catch判断是否有异常
            Statement statement = jdbcConn.getConn().createStatement();//创建sql语句
            ResultSet rs = statement.executeQuery(this.buildSelectPageSql(tableName, orderBy, where, upLimit, downLimit, size));//执行sql语句
            //下面根据该table输出属性组和所有元组
            ResultSetMetaData rsmd = rs.getMetaData();//获取属性名
            String[] keys = new String[0];
            if (rsmd != null) {
                int count = rsmd.getColumnCount();//统计属性个数
                keys = new String[count];
                for (int i = 1; i <= count; i++) {
                    keys[i - 1] = rsmd.getColumnName(i);//把属性名输入arr
                }
            }
            while (rs.next()) {
                //判断是否存在下一行，如果存在，就使用foreach循环输出元组
                Map<String, String> temp = new HashMap<>();
                for (String key : keys) {
                    temp.put(key.toLowerCase(), rs.getString(key));
                }
                result.add(temp);
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 插入
     * @param tableName 表名
     * @param params    params
     */
    public void insertList(String tableName, List<Map<String, String>> params) {
        ResultSetMetaData rsmd = null;
        StringBuilder keySb = new StringBuilder();
        StringBuilder valueSb = new StringBuilder();
        try {
            Statement sqlStatement = jdbcConn.getConn().createStatement();
            String sql = "select * from " + tableName;
            ResultSet rs = sqlStatement.executeQuery(sql);
            rsmd = rs.getMetaData();
            List<String> keys = new ArrayList<>();
            if (rsmd != null) {
                int count = rsmd.getColumnCount();
                for (int i = 1; i <= count; i++) {
                    keys.add(rsmd.getColumnName(i));
                }
            }
            keys.forEach(key -> keySb.append(key).append(","));
            params.forEach(temp -> {
                StringBuilder sb2 = new StringBuilder("('");
                for (int i = 0; i < keys.size(); i++) {
                    String value = temp.get(keys.get(i));
                    if (null == value && Character.isUpperCase(keys.get(i).charAt(0))) {
                        value = temp.get(keys.get(i).toLowerCase());
                    }
                    if (null == value && Character.isLowerCase(keys.get(i).charAt(0))) {
                        value = temp.get(keys.get(i).toUpperCase());
                    }
                    sb2.append(value);
                    if (i == keys.size() - 1) {
                        sb2.append("'),");
                    } else {
                        sb2.append("','");
                    }
                }
                valueSb.append(sb2);
            });
//            INSERT [INTO] table_or_view [(column_list)] VALUES data_values
            String insertSql = "insert into " + tableName + "(" + keySb.substring(0, keySb.length() - 1) + ") values " + valueSb.substring(0, valueSb.length() - 1) + "";//SQL语句
            LogUtil.info(insertSql);
            sqlStatement.executeUpdate(insertSql);
            rs.close();
            sqlStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error("Execute failed !");
        }
    }

    /**
     * 复制表
     * @param sourceTableName 源表名
     * @param toTableName     到表名
     * @param orderBy         排序
     * @param source          源数据库连接
     * @param aim             目标数据库连接
     * @param mapping         映射关系
     */
    public void copyTableValue(String sourceTableName, String toTableName, String orderBy, JDBCConn source, JDBCConn aim, Map<String, String> mapping) {
        Integer sum = this.countRecord(sourceTableName);
        if (sum > 1000) {
            logger.info("复制表，源表：" + sourceTableName + " 目标表：" + toTableName);
            Integer upLimit = 0;
            Integer size = 1000;
            Integer downLimit = 0;
            while (upLimit < sum) {
                downLimit = PageUtil.countPageDownLimit(sum, upLimit, size);
                logger.info("当前复制位置起始：" + upLimit + " 截止位置：" + downLimit);
                List<Map<String, String>> maps = this.selectByPage(sourceTableName, orderBy, "", upLimit, downLimit, size);
                if (maps.size() > 0) {
                    this.insertList(toTableName, maps);
                } else {
                    break;
                }
                upLimit = downLimit;
            }
        }
        source.closeAll(null);
        aim.closeAll(null);
    }

    public abstract String buildSelectPageSql(String tableName, String orderBy, String where, Integer upLimit, Integer downLimit, Integer size);

}
