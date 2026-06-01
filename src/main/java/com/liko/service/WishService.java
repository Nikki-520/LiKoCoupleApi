package com.liko.service;

import com.liko.mapper.WishMapper;
import com.liko.model.Wish;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

    private final WishMapper wishMapper;

    public WishService(WishMapper wishMapper) {
        this.wishMapper = wishMapper;
    }

    public List<Wish> list(Long coupleId) {
        return wishMapper.findByCoupleId(coupleId);
    }

    public Wish create(Wish wish) {
        wishMapper.insert(wish);
        return wishMapper.findById(wish.getId());
    }

    public Wish update(Wish wish) {
        wishMapper.update(wish);
        return wishMapper.findById(wish.getId());
    }

    public boolean delete(Long id, Long userId) {
        return wishMapper.deleteById(id, userId) > 0;
    }
}
