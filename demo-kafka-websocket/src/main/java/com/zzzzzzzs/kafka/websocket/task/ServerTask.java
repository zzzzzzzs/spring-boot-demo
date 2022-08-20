package com.zzzzzzzs.kafka.websocket.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.zzzzzzzs.kafka.websocket.common.WebSocketConsts;
import com.zzzzzzzs.kafka.websocket.model.Server;
import com.zzzzzzzs.kafka.websocket.model.server.Msg;
import com.zzzzzzzs.kafka.websocket.payload.ServerVO;
import com.zzzzzzzs.kafka.websocket.payload.server.MsgVO;
import com.zzzzzzzs.kafka.websocket.util.ServerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务器定时推送任务
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-14 16:04
 */
@Slf4j
@Component
public class ServerTask {
    @Autowired
    private SimpMessagingTemplate wsTemplate;

    /**
     * 按照标准时间来算，每隔 2s 执行一次
     */
    @Scheduled(cron = "0/2 * * * * ?")
    public void websocket() throws Exception {
        log.info("【推送消息】开始执行：{}", DateUtil.formatDateTime(new Date()));
        // 查询服务器状态
        Server server = new Server();
        server.copyTo();
        ServerVO serverVO = ServerUtil.wrapServerVO(server);
        List<MsgVO> msgs = Arrays.asList(MsgVO.create(new Msg("sadsad")));
        serverVO.setMsg(msgs);
        Dict dict = ServerUtil.wrapServerDict(serverVO);
        wsTemplate.convertAndSend(WebSocketConsts.PUSH_SERVER, JSONUtil.toJsonStr(dict));
        log.info("【推送消息】执行结束：{}", DateUtil.formatDateTime(new Date()));
    }
}
