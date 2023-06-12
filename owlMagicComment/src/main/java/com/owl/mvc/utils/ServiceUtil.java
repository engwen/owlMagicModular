package com.owl.mvc.utils;

import com.owl.mvc.function.SFunction;
import com.owl.mvc.model.ModelBase;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.util.FieldUtil;

import java.lang.reflect.Method;
import java.util.function.Consumer;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2023/6/8.
 */
public class ServiceUtil {

    @SafeVarargs
    public static <T extends ModelBase<ID>, ID, R> MsgResultVO<T> createA(T model,
//                                                                      CellBaseDao<T, ID> cellBaseDao,
                                                                          Consumer<T> setID,
                                                                          SFunction<T, R> idGet,
                                                                          SFunction<T, R>... uniqueGets) throws Exception {
        ModelBase modelBase = model.getClass().newInstance();
        for (SFunction<T, R> uniqueGet : uniqueGets) {
            R value = uniqueGet.apply(model);
            String fieldName = FieldUtil.getFieldName(uniqueGet);
            String firstChar = fieldName.substring(0, 1);
            fieldName = fieldName.replaceFirst(firstChar, firstChar.toUpperCase());
            Method method = model.getClass().getMethod(fieldName, value.getClass());
            method.invoke(modelBase, value);
        }
        System.out.println(modelBase);
        return MsgResultVO.getInstanceSuccess();
    }
}
