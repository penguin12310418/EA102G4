package groupWebsocketTool;

import java.io.IOException;
import java.util.*;
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


@ServerEndpoint("/MembersCenter/{userName}")
public class MembersCenter {
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();

	@OnOpen
	public void onOpen(@PathParam("userName") String userName, Session userSession) throws IOException {
		/* save the new user in the map */
		sessionsMap.put(userName, userSession);
		System.out.println("userName = "+userName);
		System.out.println("userSession = "+userSession);
		/* Sends all the connected users to the new user */
		Set<String> userNames = sessionsMap.keySet();
		State stateMessage = new State("open", userName, userNames);
		String stateMessageJson = gson.toJson(stateMessage);
		Collection<Session> sessions = sessionsMap.values();
		for (Session session : sessions) {
			if (session.isOpen()) {
				session.getAsyncRemote().sendText(stateMessageJson);
			}
		}

		String text = String.format("Session ID = %s, connected; userName = %s%nusers: %s", userSession.getId(),
				userName, userNames);
		System.out.println(text);
	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
		System.out.println("onMessage = "+message);
		NoticeMessage noticeMessage = gson.fromJson(message, NoticeMessage.class);
		String sender = noticeMessage.getSender();
		String receiver = noticeMessage.getReceiver();
		String noticemessage = noticeMessage.getMessage();
		NoticeMessage finalNoticeMessage = null;
		
		if ("notice".equals(noticeMessage.getType())) {
			String finalMessage = gson.toJson(noticemessage);
			finalNoticeMessage = new NoticeMessage("notice", sender, receiver, finalMessage);
			Session receiverSession = sessionsMap.get(receiver);
			if (receiverSession != null && receiverSession.isOpen()) {
				receiverSession.getAsyncRemote().sendText(gson.toJson(finalNoticeMessage));
			}
		}else if("statusCheck".equals(noticeMessage.getType())) {
			String finalMessage = gson.toJson(noticemessage);
			finalNoticeMessage = new NoticeMessage("statusCheck", sender, receiver, finalMessage);
			Session receiverSession = sessionsMap.get(receiver);
			if (receiverSession != null && receiverSession.isOpen()) {
				receiverSession.getAsyncRemote().sendText(gson.toJson(finalNoticeMessage));
				System.out.println("statusCheck = "+finalMessage);
			}
		}else if("finalStatus".equals(noticeMessage.getType())){
			String finalMessage = gson.toJson(noticemessage);
			finalNoticeMessage = new NoticeMessage("finalStatus", sender, receiver, finalMessage);
			Session receiverSession = sessionsMap.get(receiver);
			if (receiverSession != null && receiverSession.isOpen()) {
				receiverSession.getAsyncRemote().sendText(gson.toJson(finalNoticeMessage));
				System.out.println("finalStatus = "+finalMessage);
			}
		}else if("confirm".equals(noticeMessage.getType())){
			String finalMessage = gson.toJson(noticemessage);
			finalNoticeMessage = new NoticeMessage("confirm", sender, receiver, finalMessage);
			Session receiverSession = sessionsMap.get(receiver);
			if (receiverSession != null && receiverSession.isOpen()) {
				receiverSession.getAsyncRemote().sendText(gson.toJson(finalNoticeMessage));
				System.out.println("confirm = "+finalMessage);
			}
		}
		System.out.println("Message received: " + message);
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		String userNameClose = null;
		Set<String> userNames = sessionsMap.keySet();
		for (String userName : userNames) {
			if (sessionsMap.get(userName).equals(userSession)) {
				userNameClose = userName;
				sessionsMap.remove(userName);
				break;
			}
		}

		if (userNameClose != null) {
			State stateMessage = new State("close", userNameClose, userNames);
			String stateMessageJson = gson.toJson(stateMessage);
			Collection<Session> sessions = sessionsMap.values();
			for (Session session : sessions) {
				session.getAsyncRemote().sendText(stateMessageJson);
			}
		}

		String text = String.format("session ID = %s, disconnected; close code = %d%nusers: %s", userSession.getId(),
				reason.getCloseCode().getCode(), userNames);
		System.out.println(text);
	}
	
	public String getSessonKey() {
		String getKeyName = null;
		Set<String> keySet = sessionsMap.keySet();
		if(keySet.size() != 0) {
			Iterator<String> objs = keySet.iterator();
			if(objs.hasNext()) {
				getKeyName = objs.next();
			}
		}
		return getKeyName;
	}

	public static Map<String, Session> getSessionsMap() {
		return sessionsMap;
	}

	public static void setSessionsMap(Map<String, Session> sessionsMap) {
		MembersCenter.sessionsMap = sessionsMap;
	}
	
	
}
