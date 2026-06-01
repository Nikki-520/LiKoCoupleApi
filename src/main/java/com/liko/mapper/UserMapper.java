package com.liko.mapper;

import com.liko.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findByUsername(@Param("username") String username);
    User findById(@Param("id") Long id);
    User findOtherInCouple(@Param("coupleId") Long coupleId, @Param("excludeId") Long excludeId);
}
