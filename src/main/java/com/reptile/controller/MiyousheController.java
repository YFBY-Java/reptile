package com.reptile.controller;

import com.reptile.domain.miyoushe.vo.MiyousheLinkVO;
import com.reptile.entity.result.Result;
import com.reptile.service.miyoushe.IMiyousheReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * <p>
 * 注释：米游社模块
 * </p>
 * Since: 2024/7/28
 * Author: ChaserFire
 */
@RestController
@RequestMapping("/miyoushe")
@Tag(name = "米游社模块")
public class MiyousheController {

    @Autowired
    private IMiyousheReviewService miyousheReviewService;

    @Autowired
    public MiyousheController(IMiyousheReviewService miyousheReviewService) {
        this.miyousheReviewService = miyousheReviewService;
    }

    @GetMapping("/review/{articleId}")
    @Operation(summary = "爬取米游社指定文章评论",description = "爬取米游社指定文章评论")
    public Result<String> getReview(@PathVariable String articleId) {
        String result = miyousheReviewService.getReview(articleId);
        return Result.success(result);
    }


    @GetMapping("statisticsTableNum")
    @Operation(summary = "统计米游社爬取过的文章",description = "统计米游社爬取过的文章")
    public Result<List<MiyousheLinkVO>> statisticsTableNum() {
        List<MiyousheLinkVO> miyousheLinkVOS = miyousheReviewService.statisticsTableNum();
        Result<List<MiyousheLinkVO>> result = Result.success(miyousheLinkVOS);
        result.setMsg(String.valueOf(miyousheLinkVOS.size()));
        return result;
    }

}
