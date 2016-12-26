package com.tt.socket.handler;

import com.tt.schedule.WebTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Created by tt on 2016/12/26.
 */
public class TextMsgHandler extends TextWebSocketHandler {
    @Autowired
    private WebTask task;
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String msg;
        if("getStatus".equals(message.getPayload())){
            msg = "status:::"+task.getStatus().toString();
        }else{
            msg  = "event:::"+WebTask.getMsg().toString();
        }
        session.sendMessage(new TextMessage(msg));
    }
}
