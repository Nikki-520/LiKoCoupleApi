package com.liko.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class QuoteService {

    private static final String QUOTE_API = "https://api.suyanw.cn/api/love.php";

    public Map<String, Object> getQuote() {
        Map<String, Object> result = new LinkedHashMap<>();
        try {
            RestTemplate restTemplate = new RestTemplate();
            String text = restTemplate.getForObject(QUOTE_API, String.class);
            if (text != null && !text.isEmpty()) {
                text = text.trim();
                try {
                    if (text.startsWith("{")) {
                        @SuppressWarnings("rawtypes")
                        Map json = restTemplate.getForObject(QUOTE_API, Map.class);
                        if (json != null) {
                            Object quote = json.get("hitokoto");
                            if (quote == null) quote = json.get("text");
                            if (quote == null) quote = json.get("content");
                            if (quote == null) quote = json.get("data");
                            if (quote == null) quote = text;
                            result.put("quote", quote != null ? quote.toString() : text);
                            return result;
                        }
                    }
                } catch (Exception ignored) {
                }
                result.put("quote", text);
                return result;
            }
        } catch (Exception ignored) {
        }
        result.put("quote", "爱在一起的每一刻");
        return result;
    }
}
