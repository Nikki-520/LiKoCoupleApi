package com.liko.mapper;

import com.liko.model.Timeline;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TimelineMapper {
    List<Timeline> findByCoupleId(@Param("coupleId") Long coupleId);
    int insert(Timeline timeline);
    int deleteById(@Param("id") Long id, @Param("userId") Long userId);
}
