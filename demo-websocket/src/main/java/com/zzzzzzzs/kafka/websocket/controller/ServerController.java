package com.zzzzzzzs.kafka.websocket.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.json.JSONUtil;
import com.zzzzzzzs.kafka.websocket.common.WebSocketConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 服务器监控Controller
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-17 10:22
 */
@Slf4j
@RestController
@RequestMapping("/server")
public class ServerController {
    @Autowired
    private SimpMessagingTemplate wsTemplate;

    int count = 0;
    @GetMapping("/aaa")
    public void serverInfo() throws Exception {
        while (true) {
            String msg = "{\"msg\":" + count++ + "}";
            log.info("{}", msg);
            wsTemplate.convertAndSend(WebSocketConsts.PUSH_SERVER, JSONUtil.toJsonStr(msg));
        }
    }

}
