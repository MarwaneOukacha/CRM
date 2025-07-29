package com.crm.order.service.impl;

import com.crm.order.service.TelegramService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
@Service
public class TelegramServiceImpl implements TelegramService {
    @Value("${app.telegram-url}")
    private String telegramUrl;
    @Value("${app.telegram-token}")
    private String token;

    @Override
    public void sendToTelegram(String username, String message) {
        String url = telegramUrl + token + "/sendMessage";


        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("chat_id", username); // OR map from username to chat_id
        body.add("text", message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        restTemplate.postForObject(url, request, String.class);
    }

}
