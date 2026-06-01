package com.liko.mapper;

import com.liko.model.Couple;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CoupleMapper {
    Couple findById(Long id);
}
