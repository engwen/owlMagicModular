package com.owl.mvc.dto;

import java.util.List;

/**
 * 删除接收类
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/2/26.
 */
public class IdAndIdListDTO<ID> {
    /*
     * id
     * */
    private ID id;
    /*
     * id集合
     * */
    private List<ID> idList;

    public static <ID> IdAndIdListDTO<ID> getInstance(ID id, List<ID> idList) {
        return new IdAndIdListDTO<>(id, idList);
    }

    public IdAndIdListDTO() {
    }

    public IdAndIdListDTO(ID id, List<ID> idList) {
        this.id = id;
        this.idList = idList;
    }


    private IdAndIdListDTO(ID id) {
        this.id = id;
    }

    private IdAndIdListDTO(List<ID> idList) {
        this.idList = idList;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public List<ID> getIdList() {
        return idList;
    }

    public void setIdList(List<ID> idList) {
        this.idList = idList;
    }
}
