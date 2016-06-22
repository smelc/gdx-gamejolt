package com.hgames.gdx.gamejolt.answers;

/**
 * The answer of a
 * <a href="http://gamejolt.com/api/doc/game/trophies/fetch">fetch trophy
 * request</a>.
 * 
 * @author smelC
 */
public class FetchTrophyAnswer {

	public final String trophyID;
	public final String title;
	public final String description;
	public final Difficulty difficulty;
	/** The URL to the trophy's thumbnail. */
	public final String imageUrl;
	/** Whether the trophy was achieved by the user */
	public final boolean achieved;

	FetchTrophyAnswer(String trophyID, String title, String description, Difficulty difficulty, String imageUrl,
			boolean achieved) {
		this.trophyID = trophyID;
		this.title = title;
		this.description = description;
		this.difficulty = difficulty;
		this.imageUrl = imageUrl;
		this.achieved = achieved;
	}

}
