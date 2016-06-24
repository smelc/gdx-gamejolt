package com.hgames.gdx.gamejolt.answers;

/**
 * The difficulty of a trophy.
 * 
 * @author smelC
 */
public enum Difficulty {
	BRONZE, SILVER, GOLD, PLATINUM;

	public static /* @Nullable */ Difficulty of(String s) {
		if (s == null)
			return null;
		final String lc = s.toLowerCase();
		if ("bronze".equals(lc))
			return Difficulty.BRONZE;
		else if ("silver".equals(lc))
			return Difficulty.SILVER;
		else if ("gold".equals(lc))
			return Difficulty.SILVER;
		else if ("platinum".equals(lc))
			return Difficulty.SILVER;
		else
			return null;
	}
}
