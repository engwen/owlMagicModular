package com.owl.io.socket.model;

import com.owl.model.ModelPrototype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/19.
 */
public class SocketMsg extends ModelPrototype {
    //消息數據
    private Map<String, String> msg;
    //发送人id
    private String senderId;
    //收件人
    private List<String> accepterIds;
    //是否需要回執
    private boolean needBack;
    //消息類型.請求為1,答復為0
    private int msgType;

    public static SocketMsg getInstance() {
        SocketMsg socketMsg = new SocketMsg();
        socketMsg.msg = new HashMap<>();
        return socketMsg;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public List<String> getAccepterIds() {
        return accepterIds;
    }

    public void setAccepterIds(List<String> accepterIds) {
        this.accepterIds = accepterIds;
    }

    public boolean isNeedBack() {
        return needBack;
    }

    public void setNeedBack(boolean needBack) {
        this.needBack = needBack;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public Map<String, String> getMsg() {
        return msg;
    }

    public void setMsg(Map<String, String> msg) {
        this.msg = msg;
    }
}
