import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat")
public class ChatServlet {
    private static Set<Session> userSessions = Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());
    
    @OnOpen
    public void onOpen(Session currSess)
    {
        userSessions.add(currSess);
    }
            
    @OnClose
    public void onClose(Session currSess)
    {
        userSessions.remove(currSess);
    }
    
    @OnMessage
    public void onMessage(String message, Session userSess)
    {
        for(Session sess : userSessions)
        {
            sess.getAsyncRemote().sendText(message);
        }
    }
}
