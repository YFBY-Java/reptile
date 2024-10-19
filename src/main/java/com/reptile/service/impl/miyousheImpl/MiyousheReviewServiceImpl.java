package com.reptile.service.impl.miyousheImpl;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reptile.constant.MiyousheConstant;
import com.reptile.domain.miyoushe.vo.MiyousheLinkVO;
import com.reptile.entity.miyoushe.MiyousheReview;
import com.reptile.mapper.DbMapper;
import com.reptile.mapper.MiyousheReviewMapper;
import com.reptile.service.miyoushe.IMiyousheReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.reptile.constant.MiyousheConstant.MIYOUSHE_YS_ARTICLE_URL_PREFIX;
import static com.reptile.constant.MiyousheConstant.MIYOUSHE_TABLE_PREFIX;

@Service
@Slf4j
public class MiyousheReviewServiceImpl extends ServiceImpl<MiyousheReviewMapper, MiyousheReview> implements IMiyousheReviewService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MiyousheReviewMapper miyousheReviewMapper;

    @Autowired
    private DbMapper dbMapper;


    @Override
    public String getReview(String articleId) {

        String tableName = MIYOUSHE_TABLE_PREFIX + articleId;
        Boolean existTable = isExistTable(tableName);
        if(!existTable){
            dbMapper.createMiyousheTable(tableName);
        }

        final String urlPre = MiyousheConstant.MIYOUSHE_REVIEW_URL;
        String lastId = "0";
        boolean isLast = false;

        while (!isLast) {
            String url = urlPre + lastId + "&post_id=" + articleId;
            log.info("Request URL: {}", url);

            // 发送HTTP GET请求，接收JSON响应
            JSONObject jsonObject = JSONUtil.parseObj(restTemplate.getForObject(url, String.class));
            JSONObject dataObject = jsonObject.getJSONObject("data");
            String message = jsonObject.getStr("message");
            String retcode = jsonObject.getStr("retcode");
            log.info(" retcode:{}    -----------    message: {}",retcode ,message);
            JSONArray listArray = dataObject.getJSONArray("list");
            isLast = dataObject.getBool("is_last");
            log.info("是否最后一页：{}", isLast);

            String tempLastId = dataObject.getStr("last_id");
            log.info("lastId: {}", tempLastId);
            if (tempLastId.equals(lastId)) {
                break;
            }
            lastId = tempLastId;

            List<MiyousheReview> reviews = listArray.stream().map(obj -> {
                JSONObject reviewObj = (JSONObject) obj;
                JSONObject replyJson = reviewObj.getJSONObject("reply");
                JSONObject userJson = reviewObj.getJSONObject("user");
                JSONObject statJson = reviewObj.getJSONObject("stat");

                MiyousheReview review = new MiyousheReview();
                review.setReplyId(Long.valueOf(replyJson.getStr("reply_id")));
                review.setGameId(replyJson.getInt("game_id"));
                review.setPostId(replyJson.getStr("post_id"));
                review.setUid(replyJson.getStr("uid"));
                review.setRUid(replyJson.getStr("r_uid"));
                review.setContent(replyJson.getStr("content"));
                review.setFForumId(replyJson.getInt("f_forum_id"));
                review.setFloorId(replyJson.getInt("floor_id"));
                review.setIsDeleted(replyJson.getInt("is_deleted") == 1);
                review.setDeleteSrc(replyJson.getInt("delete_src"));
                review.setCreatedAt(replyJson.getLong("created_at"));
                review.setDeletedAt(replyJson.getLong("deleted_at"));
                review.setStructContent(replyJson.getStr("struct_content"));
                review.setIsTop(replyJson.getBool("is_top"));
                review.setHasBlockWord(replyJson.getBool("has_block_word"));
                review.setOvertStatus(replyJson.getBool("overt_status"));
                review.setIsShowingMissing(replyJson.getBool("is_showing_missing"));
                review.setViewStatus(replyJson.getBool("view_status"));

                review.setNickname(userJson.getStr("nickname"));
                JSONObject certification = userJson.getJSONObject("certification");
                review.setCertificationType(certification.getInt("type") == 1);
                review.setCertificationLabel(certification.getStr("label"));
                JSONObject levelExp = userJson.getJSONObject("level_exp");
                review.setLevel(levelExp.getInt("level"));
                review.setExp(levelExp.getInt("exp"));
                review.setIpRegion(userJson.getStr("ip_region"));

                review.setLikeNum(statJson.getInt("like_num"));
                review.setSubNum(statJson.getInt("sub_num"));
                review.setDislikeNum(statJson.getInt("dislike_num"));

                JSONObject masterStatus = reviewObj.getJSONObject("master_status");
                review.setIsOfficialMaster(masterStatus.getBool("is_official_master"));
                review.setIsUserMaster(masterStatus.getBool("is_user_master"));

                return review;
            }).collect(Collectors.toList());

            miyousheReviewMapper.insertMiyousheReviews(reviews,tableName);
        }
        return "爬取完成";
    }

    @Override
    public List<MiyousheLinkVO> statisticsTableNum() {
        return statisticsYsTable();
    }

    private Boolean isExistTable(String tableName){
        int exists = dbMapper.tableExists(tableName);
        return exists > 0;
    }




    private List<MiyousheLinkVO> statisticsYsTable(){
        List<String> tableNames = dbMapper.statisticsTable(MIYOUSHE_TABLE_PREFIX);
        List<MiyousheLinkVO> miyousheLinkVOS = new ArrayList<>();

        for (String tableName : tableNames) {
            MiyousheLinkVO vo = new MiyousheLinkVO();
            Integer articleId = extractArticleIdFromTableName(tableName);
            if (articleId != null) {
                vo.setArticleId(articleId);
                vo.setArticleURL(MIYOUSHE_YS_ARTICLE_URL_PREFIX + articleId);
                miyousheLinkVOS.add(vo);
            }
        }
        return miyousheLinkVOS;
    }

    private Integer extractArticleIdFromTableName(String tableName) {
        // 假设表名格式为 "miyoushe_ys_55221094"
        try {
            String[] parts = tableName.split("_");
            return Integer.parseInt(parts[parts.length - 1]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // 处理表名不包含有效整数ID的情况
            return null;
        }
    }

}
