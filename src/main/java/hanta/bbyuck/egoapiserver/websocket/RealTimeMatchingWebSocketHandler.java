//package hanta.bbyuck.egoapiserver.websocket;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.*;
//import org.springframework.web.socket.handler.AbstractWebSocketHandler;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//@Slf4j
//@Component
//public class RealTimeMatchingWebSocketHandler extends AbstractWebSocketHandler {
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        TextMessage textMessage = new TextMessage(session.getId() + "EGO - Real Time Matching Server Connect Success");
//        session.sendMessage(textMessage);
//    }
//
//    @Override
//    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//        Object payload = message.getPayload();
//        log.info(session.getId() + " : " + payload.toString());
//
//        TextMessage textMessage = new TextMessage("User " + session.getId() + " send message.");
//        session.sendMessage(textMessage);
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
//        log.info("Connection Closed : " + closeStatus.getReason());
//        TextMessage textMessage = new TextMessage("Connection Closed : " + closeStatus.getReason());
//        session.sendMessage(textMessage);
//    }
//}
