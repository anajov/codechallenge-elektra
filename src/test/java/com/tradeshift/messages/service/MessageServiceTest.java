package com.tradeshift.messages.service;

import com.tradeshift.messages.dao.MessageDao;
import com.tradeshift.messages.dao.MessageRecord;
import com.tradeshift.messages.forjson.ResponseMessage;
import com.tradeshift.messages.forjson.ResponseRecentMessages;
import junit.framework.TestCase;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ajo on 18/06/15.
 * <p/>
 * Test for @{MessageService} class
 */
public class MessageServiceTest extends TestCase {
    private static final String NAME = "ana";
    private final MessageDao msgDaoMock = mock(MessageDao.class);
    private final MessageService msgService = new MessageService(msgDaoMock);

    private List<MessageRecord> generateMsgRecords() {
        List<MessageRecord> messageRecords = new ArrayList<MessageRecord>();
        messageRecords.add(new MessageRecord(1, "ana", new DateTime()));
        messageRecords.add(new MessageRecord(2, "leo", new DateTime()));
        messageRecords.add(new MessageRecord(3, "johannes", new DateTime()));
        return messageRecords;
    }

    private void setupGetRecentMsgsTest(List<MessageRecord> messageRecords) {
        when(msgDaoMock.getRecentMsgRecords()).thenReturn(messageRecords);
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
    public void testGetResponseMessage() {
        ResponseMessage msg = msgService.getResponseMessage(NAME);
        assertEquals(NAME, msg.getMessage().getContent());
    }

    @Test
    public void testGetRecentMessages() {
        List<MessageRecord> expectedMsgRecords = generateMsgRecords();
        setupGetRecentMsgsTest(expectedMsgRecords);
        ResponseRecentMessages response = msgService.getRecentMessages();

        assertEquals(expectedMsgRecords.size(), response.getMessagesCount());
        for (int i = 0; i < response.getMessagesCount(); i++) {
            assertEquals(expectedMsgRecords.get(i).content,
                    response.getMessages().get(i).getMessage().getContent());
        }

        compareDateTimes(expectedMsgRecords.get(2).receivedAt,
                ISODateTimeFormat.dateTime().parseDateTime(response.getLastMessage()));
    }

    @Test
    public void testGetRecentMessagesEmpty() {
        setupGetRecentMsgsTest(Collections.<MessageRecord>emptyList());
        ResponseRecentMessages response = msgService.getRecentMessages();

        assertEquals(response.getMessagesCount(), 0);
        assertEquals(response.getLastMessage(), MessageService.NO_RECENT_MESSAGES);
        assertEquals(response.getMessages().size(), 0);
    }
}
