package com.washingtonpost.videocomments.dao;

import com.washingtonpost.videocomments.model.VideoComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class VideoCommentsDao {

    private Logger log = LoggerFactory.getLogger(VideoCommentsDao.class);

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public List<VideoComment> loadAll(int offset, int limit) {
        List<VideoComment> ret = jdbcTemplate.query("select * from VIDEO_COMMENTS order by CREATED_AT", new VideoCommentRowMapper());
        return ret;
    }

    public void create(final VideoComment comment) {
        Assert.isNull(comment.getId());
        final UUIDKeyHolder keyHolder = new UUIDKeyHolder();
        final PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                comment.setCreatedAt(timestamp);
                comment.setUpdatedAt(timestamp);
                PreparedStatement pst = con.prepareStatement(
                        "insert into VIDEO_COMMENTS (CREATED_AT, UPDATED_AT, VIDEO, THUMBNAIL, COMPLETE)" +
                                " values (?, ?, ?, ?, ?) ", new String[]{"id"});
                pst.setTimestamp(1, timestamp);
                pst.setTimestamp(2, timestamp);
                pst.setInt(3, comment.isHasVideo() ? 1: 0);
                pst.setInt(4, comment.isHasThumbnail() ? 1: 0);
                pst.setInt(5, comment.isComplete() ? 1: 0);
                return pst;
            }
        };

        jdbcTemplate.update(psc, keyHolder);

        comment.setId(keyHolder.getKeyUUID());
        log.debug("Created comment " + comment.getId());
    }

    public boolean update(VideoComment comment) {
        Assert.notNull(comment.getId());
        int count = jdbcTemplate.update("update VIDEO_COMMENTS set UPDATED_AT = ?, VIDEO = ?, THUMBNAIL = ?, COMPLETE = ? where ID = ?",
                new Object[]{
                        comment.getUpdatedAt(),
                        comment.isHasVideo() ? 1: 0,
                        comment.isHasThumbnail() ? 1: 0,
                        comment.isComplete() ? 1: 0,
                        comment.getId()}
        );
        log.debug("Updated node " + comment.getId());
        return count > 0;
    }

    public int delete(UUID id) {
        int count = jdbcTemplate.update("delete from VIDEO_COMMENTS where ID = ?", new Object[]{id});
        log.debug("Deleted node " + id);
        return count;
    }

    public VideoComment load(UUID id) {
        return (VideoComment) jdbcTemplate.queryForObject("select * from VIDEO_COMMENTS where ID=?",
                new Object[]{id}, new VideoCommentRowMapper());
    }

    public int count() {
        int total = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM VIDEO_COMMENTS");
        return total;
    }


    public int deleteAll() {
        int count = jdbcTemplate.update("delete from VIDEO_COMMENTS");
        log.debug("Remove all deleted " + count + " entities");
        return count;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected class VideoCommentRowMapper implements RowMapper {

        @Override
        public VideoComment mapRow(ResultSet resultSet, int i) throws SQLException {
            VideoComment record = new VideoComment();
            record.setId((UUID) resultSet.getObject("ID"));
            record.setCreatedAt(resultSet.getTimestamp("CREATED_AT"));
            record.setUpdatedAt(resultSet.getTimestamp("UPDATED_AT"));
            record.setHasVideo(resultSet.getBoolean("VIDEO"));
            record.setHasThumbnail(resultSet.getBoolean("THUMBNAIL"));
            record.setComplete(resultSet.getBoolean("COMPLETE"));
            return record;
        }
    }

}
