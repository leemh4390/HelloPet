package kr.co.hellopet.socket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class EchoHandler extends TextWebSocketHandler {

	private static List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	
}

