package com.owl.core.model;

import com.owl.util.DateCountUtil;
import com.owl.util.RegexUtil;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.DefaultCommentGenerator;

import java.util.Date;
import java.util.Properties;

/**
 * 根據 comment 生成實體類注釋
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/12/21.
 */
public class ModelCommentGenerator extends DefaultCommentGenerator {
    /*
     * 类属性注释
     * @param field
     * @param introspectedTable
     * @param introspectedColumn
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();
        if (!RegexUtil.isEmpty(remarks)) {
            field.addJavaDocLine("//" + introspectedColumn.getRemarks());
        }
    }
}
