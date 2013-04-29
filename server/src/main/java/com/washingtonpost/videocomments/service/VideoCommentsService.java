package com.washingtonpost.videocomments.service;


import com.washingtonpost.videocomments.dao.VideoCommentsDao;
import com.washingtonpost.videocomments.model.VideoComment;

import java.util.Date;

public class VideoCommentsService {

    private VideoCommentsDao videoCommentsDao;

    public Long createNewComment() {
        VideoComment videoComment = new VideoComment();
        videoCommentsDao.create(videoComment);
        return videoComment.getId();
    }

    public VideoComment loadComment(Long id) {
        VideoComment comment = videoCommentsDao.load(id);
        return comment;
    }


    public void complete(Long id) {
        VideoComment videoComment = videoCommentsDao.load(id);
        if (!videoComment.isComplete()) {
            //TODO upload video
            //delete files
            videoComment.setComplete(true);
            videoComment.setUpdatedAt(new Date());
            videoCommentsDao.update(videoComment);
        }
    }

    public void setThumbnail(Long id, byte[] image) {
        VideoComment videoComment = videoCommentsDao.load(id);
    }


    public void setVideoCommentsDao(VideoCommentsDao videoCommentsDao) {
        this.videoCommentsDao = videoCommentsDao;
    }
}
