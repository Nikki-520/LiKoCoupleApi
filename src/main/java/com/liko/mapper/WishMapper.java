package com.liko.mapper;

import com.liko.model.Wish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WishMapper {
    List<Wish> findByCoupleId(@Param("coupleId") Long coupleId);
    Wish findById(@Param("id") Long id);
    int insert(Wish wish);
    int update(Wish wish);
    int deleteById(@Param("id") Long id, @Param("userId") Long userId);
}
