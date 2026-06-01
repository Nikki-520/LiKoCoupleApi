package com.liko.mapper;

import com.liko.model.Diary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiaryMapper {
    List<Diary> findByCoupleId(@Param("coupleId") Long coupleId, @Param("mood") String mood,
                               @Param("offset") int offset, @Param("size") int size);
    Diary findById(@Param("id") Long id);
    int insert(Diary diary);
    int update(Diary diary);
    int deleteById(@Param("id") Long id, @Param("userId") Long userId);
}
