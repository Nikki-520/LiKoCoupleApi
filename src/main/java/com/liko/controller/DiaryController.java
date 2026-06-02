package com.liko.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liko.dto.ApiResult;
import com.liko.dto.DiaryRequest;
import com.liko.model.Diary;
import com.liko.service.DiaryService;
import com.liko.util.ImageUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/couple-api/diaries")
public class DiaryController {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final int MAX_IMAGE_COUNT = 3;

    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @GetMapping
    public ApiResult<?> list(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String mood) {
        Long coupleId = (Long) request.getAttribute("coupleId");
        return ApiResult.ok(diaryService.list(coupleId, mood, page, size));
    }

    @GetMapping("/{id}")
    public ApiResult<?> get(@PathVariable Long id) {
        Diary diary = diaryService.getById(id);
        if (diary == null) {
            return ApiResult.fail(400, "日记不存在");
        }
        return ApiResult.ok(diary);
    }

    @PostMapping
    public ApiResult<?> create(@RequestBody DiaryRequest req, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Long coupleId = (Long) request.getAttribute("coupleId");

        String processedImages = processImages(req.getImages());
        if (processedImages == null) {
            return ApiResult.fail(400, "图片数量不能超过" + MAX_IMAGE_COUNT + "张");
        }

        Diary diary = new Diary();
        diary.setCoupleId(coupleId);
        diary.setUserId(userId);
        diary.setDiaryDate(req.getDiaryDate() != null ? LocalDate.parse(req.getDiaryDate()) : LocalDate.now());
        diary.setMood(req.getMood());
        diary.setContent(req.getContent());
        diary.setImages(processedImages);

        return ApiResult.ok(diaryService.create(diary));
    }

    @PutMapping("/{id}")
    public ApiResult<?> update(@PathVariable Long id, @RequestBody DiaryRequest req, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Long coupleId = (Long) request.getAttribute("coupleId");

        String processedImages = processImages(req.getImages());
        if (processedImages == null) {
            return ApiResult.fail(400, "图片数量不能超过" + MAX_IMAGE_COUNT + "张");
        }

        Diary diary = new Diary();
        diary.setId(id);
        diary.setUserId(userId);
        diary.setCoupleId(coupleId);
        diary.setDiaryDate(req.getDiaryDate() != null ? LocalDate.parse(req.getDiaryDate()) : LocalDate.now());
        diary.setMood(req.getMood());
        diary.setContent(req.getContent());
        diary.setImages(processedImages);

        return ApiResult.ok(diaryService.update(diary));
    }

    @DeleteMapping("/{id}")
    public ApiResult<?> delete(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        boolean ok = diaryService.delete(id, userId);
        return ok ? ApiResult.ok(null) : ApiResult.fail(400, "删除失败");
    }

    private String processImages(String images) {
        if (images == null || images.isEmpty()) {
            return images;
        }

        try {
            List<String> list = objectMapper.readValue(images, new TypeReference<List<String>>() {});
            if (list.size() > MAX_IMAGE_COUNT) {
                return null;
            }

            List<String> compressed = new ArrayList<>();
            for (String img : list) {
                compressed.add(ImageUtil.compress(img));
            }
            return objectMapper.writeValueAsString(compressed);
        } catch (Exception e) {
            return images;
        }
    }
}
