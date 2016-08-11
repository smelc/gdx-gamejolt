package com.hgames.gdx.gamejolt.requests;

/**
 * The request to record a score, as specified in
 * <a href="http://gamejolt.com/api/doc/game/scores/add">GameJolt 's API</a>.
 * 
 * @author smelC
 */
public class AddScoreRequest extends AbstractRequest {

	public final String score;
	public final int sort;
	public final /* @Nullable */ String guest;
	public final /* @Nullable */ String extraData;
	public final /* @Nullable */ String tableID;

	/**
	 * @param score
	 *            This is a string value associated with the score. Example:
	 *            "234 Jumps".
	 * @param sort
	 *            This is a numerical sorting value associated with the score.
	 *            All sorting will work off of this number. Example: "234".
	 * @param guest
	 *            guest The guest's name. Leave null if you're storing for a
	 *            user.
	 * @param extraData
	 *            If there's any extra data you would like to store (as a
	 *            string), you can use this variable. Note that this is only
	 *            retrievable through the API. It never shows on the actual
	 *            site.
	 * @param tableID
	 *            The id of the high score table that you want to submit to. If
	 *            null, the score will be submitted to the primary high score
	 *            table.
	 */
	public AddScoreRequest(String score, int sort, /* @Nullable */ String guest,
			/* @Nullable */ String extraData, /* @Nullable */ String tableID) {
		this.score = score;
		this.sort = sort;
		this.guest = guest;
		this.extraData = extraData;
		this.tableID = tableID;
	}

	@Override
	public final RequestKind getRequestKind() {
		return RequestKind.ADD_SCORE;
	}

	@Override
	public String toString() {
		final StringBuilder result = new StringBuilder("score=" + score + " sort=" + sort);
		if (guest != null)
			result.append(" guest=" + guest);
		if (extraData != null)
			result.append(" extra_data=" + extraData);
		if (tableID != null)
			result.append(" table_id=" + tableID);
		return result.toString();
	}

}
