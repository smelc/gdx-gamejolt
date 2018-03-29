package com.hgames.gdx.gamejolt.requests;

/**
 * The request to fetch a scores table, as specified in
 * <a href="http://gamejolt.com/api/doc/game/scores/fetch">GameJolt 's API</a>.
 * 
 * @author smelC
 */
public class FetchScoresRequest extends AbstractRequest {

	public final String /* @Nullable */ username;
	public final String /* @Nullable */ userToken;
	public final int limit;
	public final String /* @Nullable */ tableID;

	/**
	 * @param username
	 *            Only pass the user information in if you would like to retrieve
	 *            scores for just that user. Leave the user information blank to
	 *            retrieve all scores.
	 * @param userToken
	 *            Only pass the user information in if you would like to retrieve
	 *            scores for just that user. Leave the user information blank to
	 *            retrieve all scores.
	 * @param limit
	 *            The number of scores you'd like to return. The default value (if
	 *            null) is 10 scores. The maximum amount of scores you can retrieve
	 *            is 100.
	 * @param tableID
	 *            The id of the high score table that you want to get high scores
	 *            for. If null the scores from the primary high score table will be
	 *            returned.
	 */
	public FetchScoresRequest(String username, /* @Nullable */ String userToken, /* @Nullable */ Integer limit,
			/* @Nullable */ String tableID) {
		this.username = username;
		this.userToken = userToken;
		this.limit = limit == null ? 10 : Math.min(100, limit.intValue());
		this.tableID = tableID;
	}

	@Override
	public final RequestKind getRequestKind() {
		return RequestKind.FETCH_SCORES;
	}

	@Override
	public String toString() {
		final StringBuilder result = new StringBuilder();
		if (username != null)
			result.append("username=" + username);
		if (userToken != null)
			result.append(" user_token=" + userToken);
		result.append(" limit=" + limit);
		if (tableID != null)
			result.append(" table_id=" + tableID);
		return result.toString();
	}

}
