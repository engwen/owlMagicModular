package com.owl.jdbc.service.impl;

import com.owl.jdbc.service.JDBCBaseService;
import com.owl.jdbc.service.ab.JDBCBaseDefaultAB;
import com.owl.jdbc.utils.JDBCUtil;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2022/12/30.
 */
public class PostgreServiceImpl extends JDBCBaseDefaultAB implements JDBCBaseService {
    public PostgreServiceImpl() {
        jdbcConn = JDBCUtil.getPostGreJDBCConn();
    }

    @Override
    public String buildSelectPageSql(String tableName, String orderBy, String where, Integer upLimit, Integer downLimit, Integer size) {
        return "SELECT * FROM " + tableName + " order by " + orderBy + " limit " + size + " offset " + upLimit + " ";
    }

}
