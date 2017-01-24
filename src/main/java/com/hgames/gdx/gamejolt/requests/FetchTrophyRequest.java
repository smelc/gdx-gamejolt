package com.hgames.gdx.gamejolt.requests;

/**
 * The request for a trophy as specified in
 * <a href="http://gamejolt.com/api/doc/game/trophies/fetch">GameJolt's API</a>.
 * 
 * @author smelC
 */
public class FetchTrophyRequest extends AbstractRequest {

	public final Boolean achieved;
	public final String[] trophyIDs;

	/**
	 * @param achieved
	 *            Set to "true" to return only the achieved trophies for a user
	 *            or "false" to return only trophies the user hasn't achieved
	 *            yet. {@code null} to retrieve all trophies.
	 * @param trophyIDs
	 *            The trophies to request. Must not be empty.
	 */
	public FetchTrophyRequest(Boolean achieved, String[] trophyIDs) {
		this.achieved = achieved;
		this.trophyIDs = trophyIDs;
	}

	@Override
	public final RequestKind getRequestKind() {
		return RequestKind.FETCH_TROPHY;
	}

}
