package com.reptile.domain.miyoushe.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 注释：查询爬过的米游社评论链接
 * </p>
 * Since: 2024/8/3
 * Author: ChaserFire
 */
@Data
public class MiyousheLinkVO {

    /**
     * 米游社文章id
     */
    @Schema(description = "文章id")
    private Integer articleId;

    /**
     * 文章链接
     */
    @Schema(description = "文章链接")
    private String articleURL;
}
