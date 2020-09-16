package groupWebsocketTool;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

@ServerEndpoint("/Calendar/{userName}")
public class CalendarWebsocket {
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();

	@OnOpen
	public void onOpen(@PathParam("userName") String userName, Session userSession) throws IOException {
		
		sessionsMap.put(userName, userSession);
		Set<String> userNames = sessionsMap.keySet();
	
		State stateMessage = new State("open", userName, userNames);
		String stateMessageJson = gson.toJson(stateMessage);
		Collection<Session> sessions = sessionsMap.values();
		for (Session session : sessions) {
			if (session.isOpen()) {
				session.getAsyncRemote().sendText(stateMessageJson);
				System.out.println("µn¤J");
				System.out.println("YOOOOOOOOOOOOOOOOOOOO");
			}
		}
		String text = String.format("Session ID = %s, connected; userName = %s%nusers: %s", userSession.getId(),
				userName, userNames);
		System.out.println(text);
	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
		AppointmentMsg appointmentMsg = gson.fromJson(message, AppointmentMsg.class);
		String sender = appointmentMsg.getSender();
		String receiver = appointmentMsg.getReceiver();
	
		NoticeMessage finalNoticeMessage =null;
//			System.out.println("53");
			String finalMessage = gson.toJson(appointmentMsg);
			finalNoticeMessage = new NoticeMessage("confirm", sender, receiver, finalMessage);
			Session receiverSession = sessionsMap.get(receiver);
			if (receiverSession != null && receiverSession.isOpen()) {
				receiverSession.getAsyncRemote().sendText(gson.toJson(finalNoticeMessage));
//				userSession.getAsyncRemote().sendText(message);
			}
		System.out.println("Messagreceivede : " + message);
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {


	}
}
