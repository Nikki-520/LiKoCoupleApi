package com.liko.controller;

import com.liko.dto.ApiResult;
import com.liko.dto.MessageRequest;
import com.liko.mapper.UserMapper;
import com.liko.model.Message;
import com.liko.model.User;
import com.liko.service.MessageService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/couple-api/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserMapper userMapper;

    public MessageController(MessageService messageService, UserMapper userMapper) {
        this.messageService = messageService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ApiResult<?> list(HttpServletRequest request) {
        Long coupleId = (Long) request.getAttribute("coupleId");
        return ApiResult.ok(messageService.list(coupleId));
    }

    @PostMapping
    public ApiResult<?> create(@RequestBody MessageRequest req, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Long coupleId = (Long) request.getAttribute("coupleId");

        User toUser = resolveToUser(req.getTo(), coupleId, userId);
        if (toUser == null) {
            return ApiResult.fail(400, "接收者不存在");
        }

        Message message = new Message();
        message.setCoupleId(coupleId);
        message.setFromId(userId);
        message.setToId(toUser.getId());
        message.setContent(req.getContent());

        return ApiResult.ok(messageService.create(message));
    }

    @PutMapping("/{id}/read")
    public ApiResult<?> markRead(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        boolean ok = messageService.markRead(id, userId);
        return ok ? ApiResult.ok(null) : ApiResult.fail(400, "标记失败");
    }

    @DeleteMapping("/{id}")
    public ApiResult<?> delete(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        boolean ok = messageService.delete(id, userId);
        return ok ? ApiResult.ok(null) : ApiResult.fail(400, "删除失败");
    }

    private User resolveToUser(String to, Long coupleId, Long myId) {
        if (to != null && !to.isEmpty()) {
            User u = userMapper.findByUsername(to);
            if (u != null) {
                return u;
            }
        }
        return userMapper.findOtherInCouple(coupleId, myId);
    }
}
