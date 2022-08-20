package com.zzzzzzzs.kafka.websocket.handler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.json.JSONUtil;
import com.zzzzzzzs.kafka.websocket.common.WebSocketConsts;
import com.zzzzzzzs.kafka.websocket.constants.KafkaConsts;
import com.zzzzzzzs.kafka.websocket.model.Server;
import com.zzzzzzzs.kafka.websocket.model.server.Msg;
import com.zzzzzzzs.kafka.websocket.payload.ServerVO;
import com.zzzzzzzs.kafka.websocket.payload.server.MsgVO;
import com.zzzzzzzs.kafka.websocket.util.ServerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 消息处理器
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-01-07 14:58
 */
@Component
@Slf4j
public class MessageHandler {
    @Autowired
    private SimpMessagingTemplate wsTemplate;
    @KafkaListener(topics = KafkaConsts.TOPIC_TEST, containerFactory = "ackContainerFactory")
    public void handleMessage(ConsumerRecord record, Acknowledgment acknowledgment) {
        try {
            String message = (String) record.value();
            log.info("收到消息: {}", message);

            log.info("【推送消息】开始执行：{}", DateUtil.formatDateTime(new Date()));
            // 查询服务器状态
            Server server = new Server();
            server.copyTo();
            ServerVO serverVO = ServerUtil.wrapServerVO(server);
            List<MsgVO> msgs = Arrays.asList(MsgVO.create(new Msg(message)));
            serverVO.setMsg(msgs);
            Dict dict = ServerUtil.wrapServerDict(serverVO);
            wsTemplate.convertAndSend(WebSocketConsts.PUSH_SERVER, JSONUtil.toJsonStr(dict));
            log.info("【推送消息】执行结束：{}", DateUtil.formatDateTime(new Date()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            // 手动提交 offset
            acknowledgment.acknowledge();
        }
    }
}
