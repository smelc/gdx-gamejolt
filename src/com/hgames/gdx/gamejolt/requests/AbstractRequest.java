package com.hgames.gdx.gamejolt.requests;

/**
 * The super type of all requests.
 * 
 * @author smelC
 */
public abstract class AbstractRequest {

	/**
	 * @return This request's kind.
	 */
	public abstract RequestKind getRequestKind();

}
