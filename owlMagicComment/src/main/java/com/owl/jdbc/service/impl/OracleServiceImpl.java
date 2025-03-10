package com.owl.jdbc.service.impl;

import com.owl.jdbc.service.JDBCBaseService;
import com.owl.jdbc.service.ab.JDBCBaseDefaultAB;
import com.owl.jdbc.utils.JDBCUtil;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2022/12/30.
 */
public class OracleServiceImpl extends JDBCBaseDefaultAB implements JDBCBaseService {
    public OracleServiceImpl() {
        jdbcConn = JDBCUtil.getOracleJDBCConn();
    }

    @Override
    public String buildSelectPageSql(String tableName, String orderBy, String where, Integer upLimit, Integer downLimit, Integer size) {
//        return "select * from (SELECT *,ROW_NUMBER() OVER(Order by " + orderBy + " ) AS RowNumber FROM " + tableName + ") as b where RowNumber between " + upLimit + " and " + downLimit;
        return null;
    }

}
