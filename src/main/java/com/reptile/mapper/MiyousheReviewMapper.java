package com.reptile.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reptile.entity.miyoushe.MiyousheReview;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MiyousheReviewMapper extends BaseMapper<MiyousheReview> {

    void insertMiyousheReviews(@Param("list") List<MiyousheReview> list,@Param("tableName") String tableName);

}
