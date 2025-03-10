package com.owl.jdbc.controller;

import com.owl.comment.annotations.OwlCheckParams;
import com.owl.comment.annotations.OwlLogInfo;
import com.owl.jdbc.model.CopyTableInfo;
import com.owl.jdbc.model.InsertListInfo;
import com.owl.jdbc.model.JDBCConn;
import com.owl.jdbc.model.SelectTableInfo;
import com.owl.jdbc.service.JDBCBaseService;
import com.owl.jdbc.utils.JDBCUtil;
import com.owl.jdbc.utils.PageUtil;
import com.owl.mvc.vo.MsgResultVO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2022/12/27.
 */
@CrossOrigin
@RestController
@OwlLogInfo
@RequestMapping(value = "buildTable", method = RequestMethod.GET, consumes = "application/json")
public class BuildTableController {
    private static final Logger logger = Logger.getLogger(BuildTableController.class.getName());

    @RequestMapping("selectPage")
    @OwlCheckParams(notNull = {"dbType", "tableName", "orderBy", "upLimit", "size"})
    public MsgResultVO<List<Map<String, String>>> selectPage(SelectTableInfo info) {
        MsgResultVO<List<Map<String, String>>> result = new MsgResultVO<>();
        JDBCBaseService jdbcService = JDBCUtil.getJDBCService(info.getDbType());
        Integer sum = jdbcService.countRecord(info.getTableName());
        Integer downLimit = PageUtil.countPageDownLimit(sum, info.getUpLimit(), info.getSize());
        List<Map<String, String>> maps = jdbcService.selectByPage(info.getTableName(), info.getOrderBy(), info.getWhere(), info.getUpLimit(), downLimit, info.getSize());
        return result.successResult(maps);
    }

    @RequestMapping("insertList")
    @OwlCheckParams(notNull = {"dbType", "tableName", "params"})
    public MsgResultVO<?> insertList(InsertListInfo info) {
        MsgResultVO<?> result = new MsgResultVO<>();
        JDBCBaseService jdbcService = JDBCUtil.getJDBCService(info.getDbType());
        jdbcService.insertList(info.getTableName(), info.getParams());
        return result.successResult();
    }

    @RequestMapping("copyTable")
    @OwlCheckParams(notNull = {"sourceTableName", "toTableName", "sourceType", "toType", "orderBy", "keyMapping"})
    public MsgResultVO<?> copyTable(CopyTableInfo info) {
        MsgResultVO<?> result = new MsgResultVO<>();
        JDBCConn sourceJdbcConn = JDBCUtil.getJDBCConn(info.getSourceType());
        JDBCConn toJdbcConn = JDBCUtil.getJDBCConn(info.getToType());
        JDBCBaseService jdbcService = JDBCUtil.getJDBCService(info.getSourceType());
        jdbcService.copyTableValue(info.getSourceTableName(), info.getToTableName(), info.getOrderBy(), sourceJdbcConn, toJdbcConn, info.getKeyMapping());
        return result.successResult();
    }

}
