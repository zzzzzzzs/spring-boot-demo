package com.zzzzzzzs.kafka.websocket.payload.server;

import com.google.common.collect.Lists;
import com.zzzzzzzs.kafka.websocket.model.server.Msg;
import com.zzzzzzzs.kafka.websocket.payload.KV;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * CPU相关信息实体VO
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-14 17:27
 */
@Data
public class MsgVO {
    List<KV> data = Lists.newArrayList();

    public static MsgVO create(Msg msg) {
        MsgVO vo = new MsgVO();
        vo.data.add(new KV("消息内容", msg.getMsg()));
        return vo;
    }
}
