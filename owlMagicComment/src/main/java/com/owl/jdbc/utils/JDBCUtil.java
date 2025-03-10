package com.owl.jdbc.utils;


import com.owl.jdbc.model.JDBCConn;
import com.owl.jdbc.service.JDBCBaseService;
import com.owl.jdbc.service.impl.*;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2022/12/27.
 */
public class JDBCUtil {
    public static String user;
    public static String password;
    public static String url;
    public static String databaseName;


    public static JDBCConn getSqlServerJDBCConn() {
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String user = JDBCUtil.user;
        String password = JDBCUtil.password;
        String url = "jdbc:sqlserver://" + JDBCUtil.url + ";DatabaseName=" + JDBCUtil.databaseName;
        return new JDBCConn(driver, user, password, url);
    }

    public static JDBCConn getMySqlJDBCConn() {
        String driver = "com.mysql.jdbc.Driver";
        String user = JDBCUtil.user;
        String password = JDBCUtil.password;
        String url = "jdbc:mysql://" + JDBCUtil.url + "/" + JDBCUtil.databaseName + "?useUnicode=true&characterEncoding=UTF-8";
        return new JDBCConn(driver, user, password, url);
    }

    public static JDBCConn getPostGreJDBCConn() {
        String driver = "org.postgresql.Driver";
        String user = JDBCUtil.user;
        String password = JDBCUtil.password;
        String url = "jdbc:postgresql://" + JDBCUtil.url + "/" + JDBCUtil.databaseName;
        return new JDBCConn(driver, user, password, url);
    }


    public static JDBCConn getOracleJDBCConn() {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String user = JDBCUtil.user;
        String password = JDBCUtil.password;
        String url = "jdbc:oracle:thin:@" + JDBCUtil.url + "/" + JDBCUtil.databaseName;
        return new JDBCConn(driver, user, password, url);
    }

    public static JDBCConn getRDJCJDBCConn() {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String user = JDBCUtil.user;
        String password = JDBCUtil.password;
        String url = "jdbc:oracle:thin:@10.238.232.232:1521/t11g";
        return new JDBCConn(driver, user, password, url);
    }

    public static JDBCConn getJDBCConn(String dbType) {
        JDBCConn result = null;
        switch (dbType) {
            case "oracle":
                result = JDBCUtil.getOracleJDBCConn();
                break;
            case "postgre":
                result = JDBCUtil.getPostGreJDBCConn();
                break;
            case "mysql":
                result = JDBCUtil.getMySqlJDBCConn();
                break;
            case "jc":
                result = JDBCUtil.getRDJCJDBCConn();
                break;
            case "sqlserver":
                result = JDBCUtil.getSqlServerJDBCConn();
                break;
        }
        return result;
    }

    public static JDBCBaseService getJDBCService(String dbType) {
        JDBCBaseService result = null;
        switch (dbType) {
            case "oracle":
                result = new OracleServiceImpl();
                break;
            case "postgre":
                result = new PostgreServiceImpl();
                break;
            case "mysql":
                result = new MySqlServiceImpl();
                break;
            case "jc":
                result = new JcServiceImpl();
                break;
            case "sqlserver":
                result = new SqlServerServiceImpl();
                break;
        }
        return result;
    }

//
//    /**
//     * 同一个表名复制
//     * @param tableName 表名
//     * @param source    源表
//     * @param aim       目标表
//     * @param mapping   字段映射关系
//     */
//    public static void copyTableValue(String tableName, JDBCConn source, JDBCConn aim, Map<String, String> mapping) {
//        JDBCUtil.copyTableValue(tableName, tableName, source, aim, mapping);
//    }
//
//    /**
//     * 不同表名复制
//     * @param sourceTableName
//     * @param toTableName
//     * @param source
//     * @param aim
//     * @param mapping
//     */
//    public static void copyTableValue(String sourceTableName, String toTableName, JDBCConn source, JDBCConn aim, Map<String, String> mapping) {
//        List<Map<String, String>> params = source.selectAll(sourceTableName, "");
//        params.forEach(it -> mapping.keySet().forEach(key -> {
//            it.put(mapping.get(key), it.get(key));
//            it.remove(key);
//        }));
//        int pgo = 0, pgs = params.size();
//        for (int i = 0; i < pgs; ) {
//            pgo = i;
//            i = (i + 1) * 1000;
//            if (i > pgs) {
//                i = pgs;
//            }
//            List<Map<String, String>> maps = params.subList(pgo, i);
//            if (maps.size() > 0) {
//                aim.insertList(toTableName, maps);
//            } else {
//                break;
//            }
//        }
//        source.closeAll(null);
//        aim.closeAll(null);
//    }


}
