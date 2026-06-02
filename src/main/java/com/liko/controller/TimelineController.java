package com.liko.controller;

import com.liko.dto.ApiResult;
import com.liko.dto.TimelineRequest;
import com.liko.model.Timeline;
import com.liko.service.TimelineService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/timelines")
public class TimelineController {

    private final TimelineService timelineService;

    public TimelineController(TimelineService timelineService) {
        this.timelineService = timelineService;
    }

    @GetMapping
    public ApiResult<?> list(HttpServletRequest request) {
        Long coupleId = (Long) request.getAttribute("coupleId");
        return ApiResult.ok(timelineService.list(coupleId));
    }

    @PostMapping
    public ApiResult<?> create(@RequestBody TimelineRequest req, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Long coupleId = (Long) request.getAttribute("coupleId");

        Timeline timeline = new Timeline();
        timeline.setCoupleId(coupleId);
        timeline.setUserId(userId);
        timeline.setEventDate(req.getEventDate() != null ? LocalDate.parse(req.getEventDate()) : LocalDate.now());
        timeline.setTitle(req.getTitle());
        timeline.setDescription(req.getDescription());
        timeline.setImageUrl(req.getImageUrl());

        return ApiResult.ok(timelineService.create(timeline));
    }

    @DeleteMapping("/{id}")
    public ApiResult<?> delete(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        boolean ok = timelineService.delete(id, userId);
        return ok ? ApiResult.ok(null) : ApiResult.fail(400, "删除失败");
    }
}
