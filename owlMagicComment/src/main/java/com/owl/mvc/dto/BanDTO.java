package com.owl.mvc.dto;

/**
 * 禁用传递对象接收类
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/2/26.
 */
public class BanDTO<ID> {
    /*
     * 对象id
     * */
    private ID id;
    /*
     * 是否禁用
     * */
    private Boolean hasBan;

    public static <ID> BanDTO getInstance(ID id, Boolean hasBan) {
        return new BanDTO<>(id, hasBan);
    }

    public BanDTO() {
    }

    private BanDTO(ID id, Boolean hasBan) {
        this.id = id;
        this.hasBan = hasBan;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public Boolean getHasBan() {
        return hasBan;
    }

    public void setHasBan(Boolean ban) {
        this.hasBan = ban;
    }
}
