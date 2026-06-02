package com.liko.mapper;

import com.liko.model.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper {
    List<Message> findByCoupleId(@Param("coupleId") Long coupleId);
    Message findById(@Param("id") Long id);
    int insert(Message message);
    int markRead(@Param("id") Long id, @Param("coupleId") Long coupleId);
    int deleteById(@Param("id") Long id, @Param("userId") Long userId);
}
