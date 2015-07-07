package com.tradeshift.messages.forjson;

/**
 * Created by ajo on 08/06/15.
 * <p/>
 *
 * @{ResponseMessage} class is a DTO object that will be serialized via JSON by Jackson library.
 */
public class ResponseMessage {
    private final Message message;

    public ResponseMessage(String content) {
        this.message = new Message(content);
    }

    public Message getMessage() {
        return message;
    }

}
