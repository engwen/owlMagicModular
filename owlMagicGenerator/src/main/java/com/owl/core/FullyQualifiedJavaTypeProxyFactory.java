package com.owl.core;

import com.owl.mvc.dto.BanListDTO;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/12/21.
 */
public class FullyQualifiedJavaTypeProxyFactory extends FullyQualifiedJavaType {

    private static FullyQualifiedJavaType modelInstance = new FullyQualifiedJavaType("com.owl.model.ModelPrototype");
    private static FullyQualifiedJavaType pageVOInstance = new FullyQualifiedJavaType("com.owl.mvc.vo.PageVO");
    private static FullyQualifiedJavaType cellBaseDaoInstance = new FullyQualifiedJavaType("com.owl.mvc.dao.CellBaseDao");
    private static FullyQualifiedJavaType relationBaseDaoInstance = new FullyQualifiedJavaType("com.owl.mvc.dao.RelationBaseDao");
    private static FullyQualifiedJavaType cellBaseServiceInstance = new FullyQualifiedJavaType("com.owl.mvc.service.CellBaseService");
    private static FullyQualifiedJavaType relationBaseServiceInstance = new FullyQualifiedJavaType("com.owl.mvc.service.RelationBaseService");
    private static FullyQualifiedJavaType cellBaseServiceImplInstance = new FullyQualifiedJavaType("com.owl.mvc.service.CellBaseServiceAb");
    private static FullyQualifiedJavaType relationBaseServiceImplInstance = new FullyQualifiedJavaType("com.owl.mvc.service.RelationBaseServiceAb");

    public FullyQualifiedJavaTypeProxyFactory(String fullTypeSpecification) {
        super(fullTypeSpecification);
    }

    public static final FullyQualifiedJavaType getPageVOInstance() {
        return pageVOInstance;
    }

    public static FullyQualifiedJavaType getCellBaseDaoInstance() {
        return cellBaseDaoInstance;
    }

    public static final FullyQualifiedJavaType getRelationBaseDaoInstance() {
        return relationBaseDaoInstance;
    }

    public static final FullyQualifiedJavaType getCellBaseServiceInstance() {
        return cellBaseServiceInstance;
    }

    public static final FullyQualifiedJavaType getRelationBaseServiceInstance() {
        return relationBaseServiceInstance;
    }

    public static final FullyQualifiedJavaType getCellBaseServiceImplInstance() {
        return cellBaseServiceImplInstance;
    }

    public static final FullyQualifiedJavaType getRelationBaseServiceImplInstance() {
        return relationBaseServiceImplInstance;
    }


    public static FullyQualifiedJavaType getModelInstance() {
        return modelInstance;
    }

}
