package com.washingtonpost.videocomments.dao;

import com.washingtonpost.videocomments.model.VideoComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;

@ContextConfiguration( locations = "classpath:/persistent.xml")
@Test
public class VideoCommentsDaoTest extends AbstractTestNGSpringContextTests {
    
    @Autowired
    private VideoCommentsDao videoCommentsDao;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @BeforeMethod
    public void setup() {
        jdbcTemplate.execute("truncate TABLE VIDEO_COMMENTS");
    }

    public void testCreateLoad() {
        VideoComment videoComment = newVideoComment("name");
        videoCommentsDao.create(videoComment);

        VideoComment loadedRecord = videoCommentsDao.load(videoComment.getId());
        Assert.assertEquals(loadedRecord.getId(), videoComment.getId());
        Assert.assertEquals(loadedRecord, videoComment);
    }

    private VideoComment newVideoComment(String name) {
        VideoComment comment = new VideoComment();
        comment.setFormat("mp4");
        comment.setComplete(false);
        comment.setHasVideo(true);
        comment.setHasThumbnail(true);
        return comment;
    }

    public void testLoadAll() {
        VideoComment comment1 = newVideoComment("name1");
        VideoComment comment2 = newVideoComment("name2");
        VideoComment comment3 = newVideoComment("name3");
        videoCommentsDao.create(comment1);
        videoCommentsDao.create(comment2);
        videoCommentsDao.create(comment3);

        List<VideoComment> list = videoCommentsDao.loadAll(0, 10);
        Assert.assertTrue(list.size() == 3);
        Assert.assertTrue(list.contains(comment1));
        Assert.assertTrue(list.contains(comment3));
    }

    public void testUpdate() {
        VideoComment record = newVideoComment("name");
        videoCommentsDao.create(record);
        UUID id = record.getId();
        record.setComplete(false);
        videoCommentsDao.update(record);
        VideoComment updatedRecord = videoCommentsDao.load(id);
        Assert.assertEquals(updatedRecord.getId(), id);
        Assert.assertEquals(updatedRecord, record);
    }

    public void testDelete() {
        VideoComment record = newVideoComment("name");
        videoCommentsDao.create(record);
        videoCommentsDao.delete(record.getId());
        List<VideoComment> list = videoCommentsDao.loadAll(0, 10);

        Assert.assertFalse(list.contains(record));
        Assert.assertTrue(list.size() == 0);
    }
}
