package com.tradeshift.messages.dao;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ajo on 18/06/15.
 * <p/>
 * Class @{MessageDao} talks to the database. It gets and updates data from the database.
 * Class @{MessageDao} is used by class @{MessageService}.
 */

@Repository
public class MessageDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MessageRecord> getRecentMsgRecords() {

        String psql = "SELECT * FROM messages ORDER BY receivedat DESC LIMIT 10;";

        return jdbcTemplate.query(psql, new RowMapper<MessageRecord>() {
            public MessageRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new MessageRecord(rs.getInt("id"), rs.getString("content"),
                        new DateTime(rs.getTimestamp("receivedAt")));
            }
        });
    }

    public void insertMessage(String content) {
        String psql = "INSERT INTO messages(content) VALUES(?);";
        jdbcTemplate.update(psql, content);
    }
}
