package com.liko.service;

import com.liko.mapper.MessageMapper;
import com.liko.model.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageMapper messageMapper;

    public MessageService(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    public List<Message> list(Long coupleId) {
        return messageMapper.findByCoupleId(coupleId);
    }

    public Message create(Message message) {
        messageMapper.insert(message);
        return messageMapper.findById(message.getId());
    }

    public boolean markRead(Long id, Long userId) {
        return messageMapper.markRead(id, userId) > 0;
    }

    public boolean delete(Long id, Long userId) {
        return messageMapper.deleteById(id, userId) > 0;
    }
}
