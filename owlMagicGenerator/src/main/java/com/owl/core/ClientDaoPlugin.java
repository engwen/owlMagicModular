package com.owl.core;

import com.owl.core.plugin.ModelCommentPlugin;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.JavaTypeResolver;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/12/21.
 */
public class ClientDaoPlugin extends ModelCommentPlugin {

    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {

        JavaTypeResolver javaTypeResolver = new JavaTypeResolverDefaultImpl();
        FullyQualifiedJavaType calculateJavaType = javaTypeResolver
                .calculateJavaType(introspectedTable.getPrimaryKeyColumns().get(0));

        FullyQualifiedJavaType superInterfaceType = new FullyQualifiedJavaType(
                "BaseMapper<" +
                        introspectedTable.getBaseRecordType() + "," +
                        introspectedTable.getExampleType() + "," +
                        calculateJavaType.getShortName() + ">"
        );
        FullyQualifiedJavaType baseMapperInstance = FullyQualifiedJavaTypeProxyFactory.getCellBaseDaoInstance();

        interfaze.addSuperInterface(superInterfaceType);
        interfaze.addImportedType(baseMapperInstance);

        List<Method> changeMethods = interfaze.getMethods().stream()
                .filter(method -> method.getName().endsWith("WithBLOBs")
                        || method.getReturnType().toString().endsWith("WithBLOBs")
                        || Arrays.toString(method.getParameters().toArray()).contains("WithBLOBs"))
                .collect(Collectors.toList());

        interfaze.getMethods().retainAll(changeMethods);
        if (changeMethods.isEmpty())
            interfaze.getImportedTypes().removeIf(javaType -> javaType.getFullyQualifiedName().equals("java.util.List")
                    || javaType.getFullyQualifiedName().equals("org.apache.ibatis.annotations.Param"));

        return super.clientGenerated(interfaze, introspectedTable);
    }

}
