package kr.co.n2m.smartcity.notification.common.sender.modules.websocket.sender;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ClientEndpoint
public class WebsocketClientEndpoint {

    Session userSession = null;

    public WebsocketClientEndpoint(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @OnOpen
    public void onOpen(Session userSession) {
    	log.info("WebSocket Open");
    	this.userSession = userSession;
    }
    
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
    	log.info("WebSocket close");
        this.userSession = null;
    }
    
    @OnError
    public void error(Session session, Throwable t) {
    	log.info("WebSocket Send Error");
    	//TODO: 웹 소켓 연결 실패 시 해당 재 연결 설정 추가 예정... 
    }

    public void sendMessage(String message) throws IOException {
        this.userSession.getAsyncRemote().sendText(message);
        this.userSession.close();
    }
    
}

