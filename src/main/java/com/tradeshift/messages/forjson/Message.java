package com.tradeshift.messages.forjson;

/**
 * Created by ajo on 08/06/15.
 * <p/>
 *
 * @{Message} class represents message content wrapped in the @{ResponseMessage} object.
 */

public class Message {
    private String content;

    public Message() {
    }

    public Message(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
