package com.zzzzzzzs.kafka.websocket.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.zzzzzzzs.kafka.websocket.common.WebSocketConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

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

    int count = 0;

    /**
     * 按照标准时间来算，每隔 2s 执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void websocket() throws Exception {
        while (true) {
            log.info("【推送消息】开始执行：{}", DateUtil.formatDateTime(new Date()));
            // 查询服务器状态
            String msg = "{\"msg\":" + count++ + "}";
            log.info("{}", msg);
            wsTemplate.convertAndSend(WebSocketConsts.PUSH_SERVER, JSONUtil.toJsonStr(msg));
            log.info("【推送消息】执行结束：{}", DateUtil.formatDateTime(new Date()));
        }
    }
}
