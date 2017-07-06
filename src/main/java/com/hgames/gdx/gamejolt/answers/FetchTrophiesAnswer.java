package com.hgames.gdx.gamejolt.answers;

import java.util.List;

import com.hgames.gdx.gamejolt.requests.FetchTrophyRequest;

/**
 * The answer of a
 * <a href="http://gamejolt.com/api/doc/game/trophies/fetch">fetch trophy
 * request</a>.
 * 
 * @author smelC
 * 
 * @see FetchTrophyRequest
 */
public class FetchTrophiesAnswer {

	public final List<FetchedTrophy> trophies;

	public FetchTrophiesAnswer(List<FetchedTrophy> backup) {
		this.trophies = backup;
	}

	@Override
	public String toString() {
		return trophies.toString();

	}

	/**
	 * @author smelC
	 */
	public static class FetchedTrophy {

		public final String trophyID;
		public final String title;
		public final String description;
		public final Difficulty difficulty;
		/** The URL to the trophy's thumbnail. */
		public final String imageUrl;
		/** Whether the trophy was achieved by the user */
		public final boolean achieved;

		public FetchedTrophy(String trophyID, String title, String description, Difficulty difficulty,
				String imageUrl, boolean achieved) {
			this.trophyID = trophyID;
			this.title = title;
			this.description = description;
			this.difficulty = difficulty;
			this.imageUrl = imageUrl;
			this.achieved = achieved;
		}

		@Override
		public String toString() {
			final String sep = "|";
			final StringBuilder result = new StringBuilder();
			result.append("trophyID:" + trophyID + sep);
			result.append("title:" + title + sep);
			result.append("description:" + description + sep);
			result.append("imageUrl:" + imageUrl + sep);
			result.append("achieved:" + achieved);
			return result.toString();
		}

	}
}
