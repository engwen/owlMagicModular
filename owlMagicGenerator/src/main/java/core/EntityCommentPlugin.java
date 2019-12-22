package core;

import com.owl.util.DateCountUtil;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.JDBCConnectionFactory;
import org.mybatis.generator.internal.util.StringUtility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

/**
 * Model 类注释, 僅支持 mysql
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/12/21.
 */
public class EntityCommentPlugin extends PluginAdapter {

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        addModelClassComment(topLevelClass, introspectedTable);
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        addModelClassComment(topLevelClass, introspectedTable);
        return super.modelRecordWithBLOBsClassGenerated(topLevelClass, introspectedTable);
    }

    /**
     * 添加類注釋
     * @param topLevelClass
     * @param introspectedTable
     */
    private void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        String tableComment = getTableComment(table);
        topLevelClass.addJavaDocLine("/**");
        if (StringUtility.stringHasValue(tableComment)) topLevelClass.addJavaDocLine(" * " + tableComment + "<p/>");
        topLevelClass.addJavaDocLine(" * " + table.toString() + "<p/>");
        topLevelClass.addJavaDocLine(" * time " + DateCountUtil.getDateFormSdf(new Date(), DateCountUtil.YMD) + ".");
        topLevelClass.addJavaDocLine(" *");
        topLevelClass.addJavaDocLine(" */");
    }

    /**
     * 獲取表的注解
     * @param table
     * @return
     */
    private String getTableComment(FullyQualifiedTable table) {
        String tableComment = "";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = new JDBCConnectionFactory(context.getJdbcConnectionConfiguration()).getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SHOW CREATE TABLE " + table.getIntrospectedTableName());
            if (resultSet != null && resultSet.next()) {
                String commentStr = resultSet.getString(2);
                int index = commentStr.indexOf("COMMENT='");
                if (index >= 0) {
                    tableComment = commentStr.substring(index + 9);
                    tableComment = tableComment.substring(0, tableComment.length() - 1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, statement, resultSet);
        }
        return tableComment;
    }


    private void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (null != resultSet) resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (connection != null) connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean validate(List<String> warnings) {
        return true;
    }
}