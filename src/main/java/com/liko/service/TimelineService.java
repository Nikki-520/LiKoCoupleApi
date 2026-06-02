package com.liko.service;

import com.liko.mapper.TimelineMapper;
import com.liko.model.Timeline;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimelineService {

    private final TimelineMapper timelineMapper;

    public TimelineService(TimelineMapper timelineMapper) {
        this.timelineMapper = timelineMapper;
    }

    public List<Timeline> list(Long coupleId) {
        return timelineMapper.findByCoupleId(coupleId);
    }

    public Timeline create(Timeline timeline) {
        timelineMapper.insert(timeline);
        return timeline;
    }

    public boolean delete(Long id, Long userId) {
        return timelineMapper.deleteById(id, userId) > 0;
    }
}
