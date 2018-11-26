package com.tianyi.helmet.server.entity.file;

import com.tianyi.helmet.server.entity.IdEntity;

/**
 * 视频关键词信息
 * <p>
 * Created by liuhanc on 2018/5/25.
 */
public class VideoKeyword extends IdEntity {
    private int videoId;
    private int keywordId;
    private String keywordName;//关键词名称 不入库

    public VideoKeyword() {
    }

    public VideoKeyword(int videoId, int keywordId) {
        this.setVideoId(videoId);
        this.setKeywordId(keywordId);
    }

    public String getKeywordName() {
        return keywordName;
    }

    public void setKeywordName(String keywordName) {
        this.keywordName = keywordName;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public int getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(int keywordId) {
        this.keywordId = keywordId;
    }
}
