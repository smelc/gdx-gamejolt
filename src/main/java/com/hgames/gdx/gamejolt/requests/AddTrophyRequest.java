package com.hgames.gdx.gamejolt.requests;

/**
 * The request for a trophy as specified in
 * <a href="http://gamejolt.com/api/doc/game/trophies/add-achieved">GameJolt's
 * API</a>.
 * 
 * @author smelC
 */
public class AddTrophyRequest extends AbstractRequest {

	public final String trophyID;

	/**
	 * @param trophyID
	 *            The identifier of the trophy to add.
	 */
	public AddTrophyRequest(String trophyID) {
		this.trophyID = trophyID;
	}

	@Override
	public RequestKind getRequestKind() {
		return RequestKind.ADD_TROPHY;
	}

	@Override
	public String toString() {
		return getRequestKind() + "(" + trophyID + ")";
	}

}
