package com.liko.controller;

import com.liko.dto.ApiResult;
import com.liko.service.QuoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/couple-api/quote")
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping
    public ApiResult<?> getQuote() {
        return ApiResult.ok(quoteService.getQuote());
    }
}
