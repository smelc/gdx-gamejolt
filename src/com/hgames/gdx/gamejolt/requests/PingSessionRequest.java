package com.hgames.gdx.gamejolt.requests;

/**
 * The request for opening a session as specified in
 * <a href="http://gamejolt.com/api/doc/game/sessions/ping">GameJolt's API</a>.
 * 
 * @author smelC
 * 
 * @see OpenSessionRequest The request to do before this one
 */
public class PingSessionRequest extends AbstractRequest {

	public final Boolean activeOrIdle;

	/**
	 * @param activeOrIdle
	 *            Whether the user is active or idle, or {@code null} to leave
	 *            it unspecified.
	 */
	public PingSessionRequest(Boolean activeOrIdle) {
		this.activeOrIdle = activeOrIdle;
	}

	@Override
	public final RequestKind getRequestKind() {
		return RequestKind.PING_SESSION;
	}

}
