package com.reptile.service.miyoushe;


import com.baomidou.mybatisplus.extension.service.IService;
import com.reptile.domain.miyoushe.vo.MiyousheLinkVO;
import com.reptile.entity.miyoushe.MiyousheReview;

import java.util.List;

public interface IMiyousheReviewService extends IService<MiyousheReview> {

    /**
     * 爬取米游社评论
     * @param articleId
     * @return
     */
    String getReview(String articleId);

    List<MiyousheLinkVO> statisticsTableNum();
}
