package com.hgames.gdx.gamejolt.requests;

/**
 * The request for opening a session as specified in
 * <a href="http://gamejolt.com/api/doc/game/sessions/open">GameJolt's API</a>.
 * 
 * @author smelC
 * 
 * @see PingSessionRequest
 * @see CloseSessionRequest
 */
public class OpenSessionRequest extends AbstractRequest {

	@Override
	public final RequestKind getRequestKind() {
		return RequestKind.OPEN_SESSION;
	}

}
