package com.liko.controller;

import com.liko.dto.ApiResult;
import com.liko.dto.WishRequest;
import com.liko.model.Wish;
import com.liko.service.WishService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@RestController
@RequestMapping("/couple-api/wishes")
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ApiResult<?> list(HttpServletRequest request) {
        Long coupleId = (Long) request.getAttribute("coupleId");
        return ApiResult.ok(wishService.list(coupleId));
    }

    @PostMapping
    public ApiResult<?> create(@RequestBody WishRequest req, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Long coupleId = (Long) request.getAttribute("coupleId");

        Wish wish = new Wish();
        wish.setCoupleId(coupleId);
        wish.setUserId(userId);
        wish.setTitle(req.getTitle());
        wish.setDescription(req.getDescription());

        return ApiResult.ok(wishService.create(wish));
    }

    @PutMapping("/{id}")
    public ApiResult<?> update(@PathVariable Long id, @RequestBody WishRequest req, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Long coupleId = (Long) request.getAttribute("coupleId");

        Wish wish = new Wish();
        wish.setId(id);
        wish.setUserId(userId);
        wish.setCoupleId(coupleId);
        wish.setTitle(req.getTitle());
        wish.setDescription(req.getDescription());
        wish.setIsDone(req.getIsDone());
        wish.setDoneDate(req.getDoneDate() != null ? LocalDate.parse(req.getDoneDate()) : null);

        return ApiResult.ok(wishService.update(wish));
    }

    @DeleteMapping("/{id}")
    public ApiResult<?> delete(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        boolean ok = wishService.delete(id, userId);
        return ok ? ApiResult.ok(null) : ApiResult.fail(400, "删除失败");
    }
}
