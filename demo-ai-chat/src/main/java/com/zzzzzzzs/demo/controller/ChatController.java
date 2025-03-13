package com.zzzzzzzs.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzzzzzzs.demo.entity.Message;
import com.zzzzzzzs.demo.entity.OllamaPrompt;
import com.zzzzzzzs.demo.entity.OllamaResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


/*
curl --location 'http://localhost:8080/demo/api/chat' \
--header 'Content-Type: application/json' \
--data '{
  "model": "deepseek-r1:1.5b",
  "messages": [
    {
      "role": "user",
      "content": "你好"
    }
  ],
  "stream": true
}'
* */

@RestController
public class ChatController {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public ChatController() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:11434")
                .build();
        this.objectMapper = new ObjectMapper();
    }

    @PostMapping(value = "/api/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chat(@RequestBody OllamaPrompt prompt) {
        // 设置默认模型（如果需要）
        prompt.setModel("deepseek-r1:1.5b"); // 替换为你使用的模型

        // 发起流式请求
        return webClient.post()
                .uri("/api/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(prompt)
                .retrieve()
                .bodyToFlux(String.class)
                .map(this::parseResponse);
    }

    private String parseResponse(String response) {
        try {
            // 解析 Ollama 的响应
//            OllamaResponse ollamaResponse = objectMapper.readValue(response, OllamaResponse.class);
//            if (ollamaResponse.getMessage() != null && ollamaResponse.getMessage().getContent() != null) {
//                return ollamaResponse.getMessage().getContent();
//            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return "无法解析的响应";
        }
    }
}