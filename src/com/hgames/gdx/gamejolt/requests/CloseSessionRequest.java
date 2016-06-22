package com.hgames.gdx.gamejolt.requests;

/**
 * The request for closing a session as specified in
 * <a href="http://gamejolt.com/api/doc/game/sessions/close">GameJolt's API</a>.
 * 
 * @author smelC
 * 
 * @see OpenSessionRequest
 * @see PingSessionRequest
 */
public class CloseSessionRequest extends AbstractRequest {

	@Override
	public final RequestKind getRequestKind() {
		return RequestKind.CLOSE_SESSION;
	}

}
