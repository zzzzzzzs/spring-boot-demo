package com.zzzzzzzs.kafka.websocket.model.server;

import cn.hutool.core.util.NumberUtil;

/**
 * <p>
 * CPU相关信息实体
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-14 16:09
 */
public class Msg {
    /**
     * 消息内容
     */
    private String msg;

    public Msg(String msg) {
        this.msg = msg;
    }

    public Msg() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
