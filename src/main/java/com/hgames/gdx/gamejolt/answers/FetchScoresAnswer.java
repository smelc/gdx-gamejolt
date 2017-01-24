package com.hgames.gdx.gamejolt.answers;

import java.util.List;

import com.hgames.gdx.gamejolt.requests.FetchScoresRequest;

/**
 * The answer of a <a href="http://gamejolt.com/api/doc/game/scores/fetch">fetch
 * scores</a> request.
 * 
 * @author smelC
 * 
 * @see FetchScoresRequest
 */
public class FetchScoresAnswer {

	public final List<FetchedScore> scores;

	public FetchScoresAnswer(List<FetchedScore> scores) {
		this.scores = scores;
	}

	/**
	 * @author smelC
	 */
	public static class FetchedScore {

		public final String score;
		public final int sort;
		public final /* @Nullable */ String extraData;
		public final /* @Nullable */ String username;
		public final /* @Nullable */ String userID;
		public final /* @Nullable */ String guest;
		public final /* @Nullable */ String stored;

		public FetchedScore(String score, int sort, /* @Nullable */ String extraData,
				/* @Nullable */ String username, /* @Nullable */ String userID, /* @Nullable */ String guest,
				/* @Nullable */ String stored) {
			this.score = score;
			this.sort = sort;
			this.extraData = extraData;
			this.username = username;
			this.userID = userID;
			this.guest = guest;
			this.stored = stored;
		}

		@Override
		public String toString() {
			final StringBuilder result = new StringBuilder();
			result.append("score=" + score);
			result.append("sort=" + sort);
			if (extraData != null)
				result.append(" extra_data:" + extraData);
			if (username != null)
				result.append(" user:" + username);
			if (userID != null)
				result.append(" user_id:" + userID);
			if (stored != null)
				result.append(" stored:" + stored);
			return result.toString();
		}

	}

}
