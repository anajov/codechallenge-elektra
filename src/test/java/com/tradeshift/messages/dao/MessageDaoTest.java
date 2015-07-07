package com.tradeshift.messages.dao;

import junit.framework.TestCase;
import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.Matchers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ajo on 18/06/15.
 * <p/>
 * Test for @{MessageDao} class.
 */
public class MessageDaoTest extends TestCase {
    private final JdbcTemplate jdbcTemplateMock = mock(JdbcTemplate.class);
    private final MessageDao msgDao = new MessageDao(jdbcTemplateMock);

    private List<MessageRecord> generateListRecentMsgsTest() {
        List<MessageRecord> messageRecords = new ArrayList<MessageRecord>();
        messageRecords.add(new MessageRecord(1, "ana", new DateTime()));
        messageRecords.add(new MessageRecord(2, "leo", new DateTime()));
        messageRecords.add(new MessageRecord(3, "johannes", new DateTime()));
        return messageRecords;
    }

    private void setupMocksGetRecentMsgs(List<MessageRecord> messageRecords) throws SQLException {
        when(jdbcTemplateMock.query(anyString(), Matchers.any(RowMapper.class)))
                .thenReturn(messageRecords);
    }

    /**
     * Avoids comparing just milliseconds since there can be insignificant difference, occurred
     * due to parsing.
     *
     * @param first
     * @param second
     */
    private void compareDateTimes(DateTime first, DateTime second) {
        assertEquals(first.getYear(), second.getYear());
        assertEquals(first.getMonthOfYear(), second.getMonthOfYear());
        assertEquals(first.getDayOfMonth(), second.getDayOfMonth());
        assertEquals(first.getHourOfDay(), second.getHourOfDay());
        assertEquals(first.getMinuteOfHour(), second.getMinuteOfHour());
        assertEquals(first.getSecondOfMinute(), second.getSecondOfMinute());
    }

    @Test
    public void testGetRecentMessages() throws SQLException {
        List<MessageRecord> expectedMsgRecords = generateListRecentMsgsTest();
        setupMocksGetRecentMsgs(expectedMsgRecords);

        List<MessageRecord> msgRecords = msgDao.getRecentMsgRecords();

        assertEquals(expectedMsgRecords.size(), msgRecords.size());
        for (int i = 0; i < msgRecords.size(); i++) {
            assertEquals(expectedMsgRecords.get(i).id, msgRecords.get(i).id);
            assertEquals(expectedMsgRecords.get(i).content, msgRecords.get(i).content);
            compareDateTimes(expectedMsgRecords.get(i).receivedAt, msgRecords.get(i).receivedAt);
        }
    }
}
