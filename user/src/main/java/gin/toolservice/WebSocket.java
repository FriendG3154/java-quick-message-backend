package gin.toolservice;

import gin.entity.QmUserAuth;
import gin.model.QmUser.QmUserAuthVO;
import gin.service.util.RedisService;
import gin.tool.JwtTool;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocket extends TextWebSocketHandler {
    private static final Map<String, WebSocketSession> SESSIONS = new ConcurrentHashMap<>();
    private static final Map<String, Long> SESSIONS_LIVE = new ConcurrentHashMap<>();
    private final RedisService redisService;
    private final JwtTool jwtTool;
    public WebSocket(RedisService redisService, JwtTool jwtTool) {
        this.redisService = redisService;
        this.jwtTool = jwtTool;
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        SESSIONS.put((String) session.getAttributes().get("userId"), session);
        redisService.addOnlineUser((String) session.getAttributes().get("userId"));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        var isExist = isExisthSocket(session);
        if(isExist){
            if ("{\"type\":\"ping\"}".equals(payload)) {
                session.sendMessage(new TextMessage("{\"type\":\"pong\"}"));
            }
            /// refreshSocket保活机制
            if ("{\"type\":\"refreshSocket\"}".equals(payload)) {
                refreshSocket(session);
            }
            /// keepalive心跳机制
            if ("{\"type\":\"KeepAlive\"}".equals(payload)) {
                refreshSocket(session);
            }


        }
    }
    /// 断开链接后执行
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        SESSIONS.remove((String) session.getAttributes().get("userId"));
        redisService.removeOnlineUser((String) session.getAttributes().get("userId"));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception){
        SESSIONS.remove((String) session.getAttributes().get("userId"));
        redisService.removeOnlineUser((String) session.getAttributes().get("userId"));
    }

    ///发送请求
    public boolean sendToUser(String userId,String message) {
        WebSocketSession session = SESSIONS.get(userId);
        boolean isExist = session != null;
        if (!isExist) {
            return false;
        }
        try{
            synchronized (userId){
                session.sendMessage(new TextMessage(message));
            }
            return true;
        }catch (Exception e){
            redisService.removeOnlineUser(userId);
            return false;
        }
    }


    /// 广播
    public void broadcast(String message) {
        SESSIONS.forEach((userId, session) -> sendToUser(userId, message));
    }

    /// 判定Socket是否有效
    public Boolean isExisthSocket(WebSocketSession session){
        String token = UriComponentsBuilder.fromUri(session.getUri()).build().getQueryParams().getFirst("token");
        QmUserAuthVO user = jwtTool.validateToken(token);
        if(user == null){
            return false;
        }
        return true;
    }

    /// 服务重启之后，会从redis里的userId重新发送一条数据是否在线的请求，判定是否在线，这个是用来接受是否在线的回复
    public void refreshSocket(WebSocketSession session){
        String userId  = (String) session.getAttributes().get("userId");
        SESSIONS.put(userId, session);
        SESSIONS_LIVE.put(userId, System.currentTimeMillis());
    }
}
