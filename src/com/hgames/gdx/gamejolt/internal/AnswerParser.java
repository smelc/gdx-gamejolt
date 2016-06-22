package com.hgames.gdx.gamejolt.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.hgames.gdx.gamejolt.FetchTrophyAnswer;
import com.hgames.gdx.gamejolt.GdxGameJolt;
import com.hgames.gdx.gamejolt.RequestKind;

/**
 * @author smelC
 */
public class AnswerParser {

	public List<FetchTrophyAnswer> parseFetchTrophyAnswer(String answer) {
		String after = after(answer, "success:");
		if (after == null) {
			log("Expected answer to " + RequestKind.FETCH_TROPHY + " request to start with \"success:\"");
			return Collections.emptyList();
		}
		// success:"true"
		// id:"60593"
		// title:"Bronze Mercenary 2"
		// description:"Reach the dungeon's depth 2"
		// difficulty:"Bronze"
		// image_url:"http://s.gjcdn.net/img/trophy-bronze-1.jpg"
		// achieved:"false"
		System.out.println("Answer: ");
		System.out.println(answer);
		final List<FetchTrophyAnswer> result = new ArrayList<FetchTrophyAnswer>();
		return result;
	}

	public boolean parseAddTropyAnswer(String answer) {
		return parseBooleanAnswer(answer, RequestKind.ADD_TROPHY);
	}

	public boolean parseAuthAnswer(String answer) {
		return parseBooleanAnswer(answer, RequestKind.AUTH);
	}

	/**
	 * @param answer
	 *            The answer to a request of kind {@code kind}
	 * @param kind
	 * @return Whether {@code answer} represents a success.
	 */
	private static boolean parseBooleanAnswer(String answer, RequestKind kind) {
		String after = after(answer, "success:");
		if (after == null) {
			log("Expected answer to " + kind + " to start with \"success:\"");
			return false;
		}

		after = trimTrailingNewLines(after);

		if (equalsBracketed(after, true))
			/* Success */
			return true;
		else {
			if (equalsBracketed(after, false))
				log("Unrecognized content after \"success:\" in answer of " + kind + ": " + after);
			return false;
		}
	}

	/**
	 * @param s
	 * @param val
	 * @return Whether {@code s} is {@code "true"} or {@code "false"}.
	 */
	private static boolean equalsBracketed(String s, boolean val) {
		return ("\"" + String.valueOf(val) + "\"").equals(s);
	}

	/**
	 * @param entire
	 * @param prefix
	 * @return The part of {@code entire} after {@code prefix}, if
	 *         {@code entire} starts with {@code prefix}. Otherwise {@code null}
	 *         .
	 */
	private static String after(String entire, String prefix) {
		if (entire.startsWith(prefix)) {
			return entire.substring(prefix.length());
		} else
			return null;
	}

	private static void log(String s) {
		if (Gdx.app == null)
			System.out.println(s);
		else
			Gdx.app.log(GdxGameJolt.TAG, s);
	}

	private static String trimTrailingNewLines(String s) {
		if (s.endsWith("\r\n"))
			return trimTrailingNewLines(s.substring(0, s.length() - 2));
		else if (s.endsWith("\n"))
			return trimTrailingNewLines(s.substring(0, s.length() - 1));
		else
			return s;
	}

}
