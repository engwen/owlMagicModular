package core;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/12/21.
 */
public class SqlMapIsMergeAblePlugin extends ClientDaoPlugin {
    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        sqlMap.setMergeable(false);
        return super.sqlMapGenerated(sqlMap, introspectedTable);
    }
}
