//package hanta.bbyuck.egoapiserver.config;
//
//
//import hanta.bbyuck.egoapiserver.websocket.RealTimeMatchingWebSocketHandler;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
//@Configuration
//@RequiredArgsConstructor
//@EnableWebSocket
//public class WebSocketConfig implements WebSocketConfigurer {
//    private final RealTimeMatchingWebSocketHandler webSocketHandler;
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(webSocketHandler, "/lol/rt-matching").setAllowedOrigins("*");
//    }
//}
