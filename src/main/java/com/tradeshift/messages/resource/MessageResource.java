package com.tradeshift.messages.resource;

import com.tradeshift.messages.forjson.Message;
import com.tradeshift.messages.forjson.ResponseMessage;
import com.tradeshift.messages.forjson.ResponseRecentMessages;
import com.tradeshift.messages.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by ajo on 08/06/15.
 * <p/>
 *
 * @{MessageResource} is a REST Resource class.
 * It talks to @{MessageService} class.
 */

@Component
@Scope("prototype")
@Path("/")
public class MessageResource {
    private final MessageService msgService;

    @Autowired
    public MessageResource(MessageService msgService) {
        this.msgService = msgService;
    }

    @POST
    @Path("/names")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseMessage getResponseMessage(Message message) {
        //TODO(ajo): Maybe change constructor for ResponseMessage and the rest.
        return msgService.getResponseMessage(message.getContent());
    }

    @GET
    @Path("/recent")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseRecentMessages getRecentMessages() {
        return msgService.getRecentMessages();
    }
}
