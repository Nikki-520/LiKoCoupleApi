package com.liko.service;

import com.liko.mapper.DiaryMapper;
import com.liko.model.Diary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaryService {

    private final DiaryMapper diaryMapper;

    public DiaryService(DiaryMapper diaryMapper) {
        this.diaryMapper = diaryMapper;
    }

    public List<Diary> list(Long coupleId, String mood, int page, int size) {
        int offset = (page - 1) * size;
        return diaryMapper.findByCoupleId(coupleId, mood, offset, size);
    }

    public Diary getById(Long id) {
        return diaryMapper.findById(id);
    }

    public Diary create(Diary diary) {
        diaryMapper.insert(diary);
        return diaryMapper.findById(diary.getId());
    }

    public Diary update(Diary diary) {
        diaryMapper.update(diary);
        return diaryMapper.findById(diary.getId());
    }

    public boolean delete(Long id, Long userId) {
        return diaryMapper.deleteById(id, userId) > 0;
    }
}
