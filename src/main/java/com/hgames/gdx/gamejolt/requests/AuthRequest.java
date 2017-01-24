package com.hgames.gdx.gamejolt.requests;

import com.hgames.gdx.gamejolt.GdxGameJolt;

/**
 * The request to authenticate an user. It doesn't have state, as the username
 * and user token are provided by {@link GdxGameJolt}.
 * 
 * @author smelC
 */
public class AuthRequest extends AbstractRequest {

	@Override
	public RequestKind getRequestKind() {
		return RequestKind.AUTH;
	}

	@Override
	public String toString() {
		return getRequestKind().toString();
	}

}
