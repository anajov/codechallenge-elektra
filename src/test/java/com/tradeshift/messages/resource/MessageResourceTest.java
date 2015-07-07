package com.tradeshift.messages.resource;

import com.tradeshift.messages.forjson.Message;
import com.tradeshift.messages.forjson.ResponseMessage;
import com.tradeshift.messages.forjson.ResponseRecentMessages;
import com.tradeshift.messages.service.MessageService;
import junit.framework.TestCase;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ajo on 18/06/15.
 * <p/>
 * Test for @{MessageResource} class.
 */
public class MessageResourceTest extends TestCase {
    private static final String NAME = "ana";
    private final MessageService msgServiceMock = mock(MessageService.class);
    private final MessageResource msgResource = new MessageResource(msgServiceMock);
    private final String currentTime = ISODateTimeFormat.dateTime().print(new DateTime());

    private List<ResponseMessage> generateResponseMsgsList() {
        List<ResponseMessage> responseMsgs = new ArrayList<ResponseMessage>();
        responseMsgs.add(new ResponseMessage("ana"));
        responseMsgs.add(new ResponseMessage("leo"));
        responseMsgs.add(new ResponseMessage("johannes"));
        return responseMsgs;
    }

    private void setupTestGetResponseMessage() {
        when(msgServiceMock.getResponseMessage(eq(NAME))).thenReturn(new ResponseMessage(NAME));
    }

    private void setupTestGetRecentMessagesTest(List<ResponseMessage> responseMsgs) {
        when(msgServiceMock.getRecentMessages()).thenReturn(
                new ResponseRecentMessages(responseMsgs.size(), currentTime, responseMsgs));
    }

    @Test
    public void testGetResponseMessage() {
        setupTestGetResponseMessage();
        ResponseMessage responseMsgs = msgResource.getResponseMessage(new Message(NAME));
        assertEquals(NAME, responseMsgs.getMessage().getContent());
    }

    @Test
    public void testGetRecentMessages() {
        List<ResponseMessage> expectedRecentMsgs = generateResponseMsgsList();
        setupTestGetRecentMessagesTest(expectedRecentMsgs);

        ResponseRecentMessages recentMsgs = msgResource.getRecentMessages();

        assertEquals(expectedRecentMsgs.size(), recentMsgs.getMessagesCount());
        for (int i = 0; i < recentMsgs.getMessagesCount(); i++) {
            assertEquals(expectedRecentMsgs.get(i).getMessage().getContent(),
                    recentMsgs.getMessages().get(i).getMessage().getContent());
        }
        assertEquals(currentTime, recentMsgs.getLastMessage());
    }
}
