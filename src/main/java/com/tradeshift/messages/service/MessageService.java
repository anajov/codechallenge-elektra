package com.tradeshift.messages.service;

import com.tradeshift.messages.dao.MessageRecord;
import com.tradeshift.messages.forjson.ResponseMessage;
import com.tradeshift.messages.forjson.ResponseRecentMessages;
import com.tradeshift.messages.dao.MessageDao;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ajo on 08/06/15.
 * <p/>
 *
 * @{MessageService} class performs the logic of the webapp.
 * It responds to function calls from @{MessageResource} class.
 * It talks to @{MessageDAO} class for accessing permanent data.
 */

@Service
public class MessageService {
    public static final String NO_RECENT_MESSAGES = "No recent messages";
    private final MessageDao msgDAO;

    @Autowired
    public MessageService(MessageDao msgDAO) {
        this.msgDAO = msgDAO;
    }

    public ResponseMessage getResponseMessage(String message) {
        msgDAO.insertMessage(message);
        return new ResponseMessage(message);
    }

    public ResponseRecentMessages getRecentMessages() {
        List<MessageRecord> msgRecords = msgDAO.getRecentMsgRecords();

        if (msgRecords.isEmpty()) {
            return new ResponseRecentMessages(0, NO_RECENT_MESSAGES, Collections.<ResponseMessage>emptyList());
        }

        List<ResponseMessage> recentMessages = new ArrayList<ResponseMessage>();

        for (MessageRecord msgRecord : msgRecords) {
            recentMessages.add(new ResponseMessage(msgRecord.content));
        }

        return new ResponseRecentMessages(recentMessages.size(),
                ISODateTimeFormat.dateTime().print(msgRecords.get(0).receivedAt),
                recentMessages);
    }
}
