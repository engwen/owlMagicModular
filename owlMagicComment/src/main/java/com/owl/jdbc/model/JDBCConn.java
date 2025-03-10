package com.owl.jdbc.model;

import com.owl.util.LogUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2022/12/27.
 */
public class JDBCConn {
    private Connection conn = null;
    private String driver;
    private String user;
    private String password;
    private String url;

    public JDBCConn(String driver, String user, String password, String url) {
        this.driver = driver;
        this.user = user;
        this.password = password;
        this.url = url;
        this.getConnection();
    }

    private void getConnection() {
        if (null == conn) {
            try {
                //加载驱动
                Class.forName(driver);
                //获取本地数据库
                conn = DriverManager.getConnection(url, user, password);
                LogUtil.info("数据库连接成功");
            } catch (Exception e) {
                LogUtil.error("获取数据库连接对象时产生的异常" + e);
            }
        }
    }

    //关闭数据库连接
    public void closeAll(PreparedStatement pst) {
        if (pst != null) {
            try {
                pst.close();
                LogUtil.info("关闭数据库连接");
            } catch (SQLException e) {
                e.printStackTrace();
                LogUtil.error("PreparedStatement对象关闭数据库的异常" + e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
                LogUtil.info("关闭数据库连接");
            } catch (Exception e) {
                LogUtil.error("数据库连接对象的异常" + e);
            }
        }
    }

    public Connection getConn() {
        return conn;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

//
//    public List<Map<String, String>> selectAll(String tableName, String where) {
//        List<Map<String, String>> result = new ArrayList<>();
//        try {//try catch判断是否有异常
//            Statement statement = conn.createStatement();//创建sql语句
//            String sql = "select * from " + tableName + " " + where;
//            ResultSet rs = statement.executeQuery(sql);//执行sql语句
//            //下面根据该table输出属性组和所有元组
//            ResultSetMetaData rsmd = rs.getMetaData();//获取属性名
//            String[] keys = new String[0];
//            if (rsmd != null) {
//                int count = rsmd.getColumnCount();//统计属性个数
//                keys = new String[count];
//                for (int i = 1; i <= count; i++) {
//                    keys[i - 1] = rsmd.getColumnName(i);//把属性名输入arr
//                }
//            }
//            while (rs.next()) {
//                //判断是否存在下一行，如果存在，就使用foreach循环输出元组
//                Map<String, String> temp = new HashMap<>();
//                for (String key : keys) {
//                    temp.put(key.toLowerCase(), rs.getString(key));
//                }
//                result.add(temp);
//            }
//            rs.close();
//            statement.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    public void insertList(String tableName, List<Map<String, String>> params) {
//        ResultSetMetaData rsmd = null;
//        StringBuilder keySb = new StringBuilder();
//        StringBuilder valueSb = new StringBuilder();
//        try {
//            Statement sqlStatement = conn.createStatement();
//            String sql = "select * from " + tableName;
//            ResultSet rs = sqlStatement.executeQuery(sql);
//            rsmd = rs.getMetaData();
//            List<String> keys = new ArrayList<>();
//            if (rsmd != null) {
//                int count = rsmd.getColumnCount();
//                for (int i = 1; i <= count; i++) {
//                    keys.add(rsmd.getColumnName(i));
//                }
//            }
//            keys.forEach(key -> keySb.append(key).append(","));
//            params.forEach(temp -> {
//                StringBuilder sb2 = new StringBuilder("('");
//                for (int i = 0; i < keys.size(); i++) {
//                    String value = temp.get(keys.get(i));
//                    if (null == value && Character.isUpperCase(keys.get(i).charAt(0))) {
//                        value = temp.get(keys.get(i).toLowerCase());
//                    }
//                    if (null == value && Character.isLowerCase(keys.get(i).charAt(0))) {
//                        value = temp.get(keys.get(i).toUpperCase());
//                    }
//                    sb2.append(value);
//                    if (i == keys.size() - 1) {
//                        sb2.append("'),");
//                    } else {
//                        sb2.append("','");
//                    }
//                }
//                valueSb.append(sb2);
//            });
////            INSERT [INTO] table_or_view [(column_list)] VALUES data_values
//            String insertSql = "insert into " + tableName + "(" + keySb.substring(0, keySb.length() - 1) + ") values " + valueSb.substring(0, valueSb.length() - 1) + "";//SQL语句
//            LogUtil.info(insertSql);
//            sqlStatement.executeUpdate(insertSql);
//            rs.close();
//            sqlStatement.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            LogUtil.error("Execute failed !");
//        }
//    }
}
