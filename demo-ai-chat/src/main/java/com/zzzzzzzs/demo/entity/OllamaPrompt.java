package com.zzzzzzzs.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OllamaPrompt {
    private String model;
    private Message[] messages;
    private boolean stream = true;
}