//package hanta.bbyuck.egoapiserver.websocket;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.websocket.*;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.Set;
//import java.util.concurrent.CopyOnWriteArraySet;
//
//@Slf4j
//@Component
//@ServerEndpoint(value = "/rt-matching")
//public class Socket {
//    private Session session;
//    public static Set<Socket> listeners = new CopyOnWriteArraySet<>();
//    private static int onlineCount = 0;
//
//    // 클라이언트가 소켓에 연결될 때마다 호출
//    @OnOpen
//    public void onOpen(Session session) {
//        onlineCount++;
//        this.session = session;
//        listeners.add(this);
//        log.info("onOpen called, userCount : " + onlineCount);
//    }
//
//    // 클라이언트와 소켓과의 연결이 끊어질 때마다 호출
//    @OnClose
//    public void onClose(Session session) {
//        onlineCount--;
//        listeners.remove(this);
//        log.info("onClose called, userCount : " + onlineCount);
//    }
//
//    // 에러 발생
//    @OnError
//    public void onError(Session session, Throwable throwable) {
//        log.error("onError called, error : " + throwable.getMessage());
//        listeners.remove(this);
//        onlineCount--;
//    }
//
//    public static void broadcast(Object data) {
//        for (Socket listenr : listeners) {
//            listenr.sendObject(data);
//        }
//    }
//
//    private void sendObject(Object data) {
//        try {
//            this.session.getBasicRemote().sendObject(data);
//        } catch (EncodeException | IOException e) {
//            log.error("Caught exception while sending message to Session + " + this.session.getId() + "er");
//        }
//    }
//}
