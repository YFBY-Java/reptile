package com.reptile.entity.miyoushe;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "")
public class MiyousheReview implements Serializable {

    @TableId
    @Schema(description = "评论ID")
    private Long replyId;

    @Schema(description = "游戏的社区，2为原神")
    private Integer gameId;

    @Schema(description = "帖子ID")
    private String postId;

    @Schema(description = "用户ID")
    private String uid;

    @Schema(description = "回复的用户ID (为0时表示这是一级评论)")
    private String rUid = "0";

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "未知字段")
    private Integer fForumId;

    @Schema(description = "楼ID (评论第几楼)")
    private Integer floorId;

    @Schema(description = "是否删除 (逻辑删除标记)")
    private Boolean isDeleted = false;

    @Schema(description = "删除源")
    private Integer deleteSrc = 0;

    @Schema(description = "创建时间戳 (发评论时间)")
    private Long createdAt;

    @Schema(description = "删除时间戳")
    private Long deletedAt;

    @Schema(description = "评论格式化字符串")
    private String structContent;

    @Schema(description = "是否置顶")
    private Boolean isTop = false;

    @Schema(description = "是否包含屏蔽词")
    private Boolean hasBlockWord = false;

    @Schema(description = "公开状态，1表示公开")
    private Boolean overtStatus = false;

    @Schema(description = "是否显示缺失")
    private Boolean isShowingMissing = false;

    @Schema(description = "显示状态")
    private Boolean viewStatus = false;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户认证类型")
    private Boolean certificationType = false;

    @Schema(description = "用户认证标签")
    private String certificationLabel = "";

    @Schema(description = "用户等级")
    private Integer level = 0;

    @Schema(description = "用户经验")
    private Integer exp = 0;

    @Schema(description = "IP地址")
    private String ipRegion = "";

    @Schema(description = "点赞数")
    private Integer likeNum = 0;

    @Schema(description = "回复数")
    private Integer subNum = 0;

    @Schema(description = "点踩数")
    private Integer dislikeNum = 0;

    @Schema(description = "是否官方")
    private Boolean isOfficialMaster = false;

    @Schema(description = "未知字段")
    private Boolean isUserMaster = false;
}