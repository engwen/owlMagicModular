package com.owl.mvc.utils;

import com.owl.mvc.dao.RelationBaseDao;
import com.owl.mvc.dto.RelationDTO;
import com.owl.mvc.so.ModelListSO;
import com.owl.mvc.so.ModelSO;
import com.owl.mvc.vo.MsgResultVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 類適用于常見的方法，提供基礎解決方案，繼承service類之後，可在注入dao后使用本工具類快速完成基礎功能代碼
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/10/10.
 */
public abstract class RelationBaseServiceUtil {

    public static <T, MainID, FollowerID> MsgResultVO insert(RelationBaseDao<T, MainID, FollowerID> relationBaseDao, T model) {
        List<T> list = relationBaseDao.selectBySelective(ModelSO.getInstance(model));
        if (list.size() > 0) {
            return MsgResultVO.getInstanceSuccess();
        } else {
            List<T> temp = new ArrayList<>();
            temp.add(model);
            return insertList(relationBaseDao, temp);
        }
    }

    public static <T, MainID, FollowerID> MsgResultVO insertRelation(RelationBaseDao<T, MainID, FollowerID> relationBaseDao, RelationDTO<MainID, FollowerID> relationDTO) {
        relationBaseDao.insertRelation(relationDTO);
        return MsgResultVO.getInstanceSuccess();
    }

    public static <T, MainID, FollowerID> MsgResultVO insertList(RelationBaseDao<T, MainID, FollowerID> relationBaseDao, List<T> modelList) {
        relationBaseDao.insertList(ModelListSO.getInstance(modelList));
        return MsgResultVO.getInstanceSuccess();
    }

    public static <T, MainID, FollowerID> MsgResultVO delete(RelationBaseDao<T, MainID, FollowerID> relationBaseDao, T model) {
        relationBaseDao.delete(ModelSO.getInstance(model));
        return MsgResultVO.getInstanceSuccess();
    }

    public static <T, MainID, FollowerID> MsgResultVO deleteRelation(RelationBaseDao<T, MainID, FollowerID> relationBaseDao, RelationDTO<MainID, FollowerID> relationDTO) {
        relationBaseDao.deleteRelation(relationDTO);
        return MsgResultVO.getInstanceSuccess();
    }

    public static <T, MainID, FollowerID> MsgResultVO deleteList(RelationBaseDao<T, MainID, FollowerID> relationBaseDao, List<T> modelList) {
        relationBaseDao.deleteList(ModelListSO.getInstance(modelList));
        return MsgResultVO.getInstanceSuccess();
    }

    public static <T, MainID, FollowerID> MsgResultVO<List<T>> list(RelationBaseDao<T, MainID, FollowerID> relationBaseDao, T model) {
        return MsgResultVO.getInstanceSuccess(relationBaseDao.selectBySelective(ModelSO.getInstance(model)));
    }

}
