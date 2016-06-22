package com.hgames.gdx.gamejolt.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.hgames.gdx.gamejolt.GdxGameJolt;
import com.hgames.gdx.gamejolt.answers.FetchTrophyAnswer;
import com.hgames.gdx.gamejolt.requests.RequestKind;

/**
 * @author smelC
 */
public class AnswerParser {

	/**
	 * @param answer
	 * @return {@code false} is the trophy was achieved already, {@code true} if
	 *         added, {@code null} if answer is badly formed.
	 */
	/* An example answer */
	// success:"true"
	// message:"The user already has this trophy."
	public Boolean parseAddTrophyAnswer(String answer) {
		final Map<String, String> keyPairs = asKeyPairs(answer);
		final Boolean success = getSuccess(keyPairs, answer);
		if (success == null)
			return null;
		final String msg = keyPairs.get("message");
		if (msg == null) {
			log("Key \"message\" is missing in answer to " + RequestKind.ADD_TROPHY + " request");
			return null;
		} else {
			return !"The user already had this trophy".equals(msg);
		}
	}

	/**
	 * @param answer
	 * @return Whether the authentification was successful, or {@code null} if
	 *         the answer is badly formed.
	 */
	public Boolean parseAuthAnswer(String answer) {
		return getSuccess(asKeyPairs(answer), answer);
	}

	/**
	 * @param answer
	 *            Whether {@code request} was a success.
	 */
	public Boolean parseCloseSessionAnswer(String answer) {
		return getSuccess(asKeyPairs(answer), answer);
	}

	/**
	 * @param answer
	 * @return The list of trophies, or {@code null} if the answer is
	 *         badly-formed.
	 */
	/* An example answer */
	// success:"true"
	// id:"60593"
	// title:"Bronze Mercenary 2"
	// description:"Reach the dungeon's depth 2"
	// difficulty:"Bronze"
	// image_url:"http://s.gjcdn.net/img/trophy-bronze-1.jpg"
	// achieved:"false"
	public /* @Nullable */ List<FetchTrophyAnswer> parseFetchTrophyAnswer(String answer) {
		throw new IllegalStateException("Implement me");
	}

	/**
	 * @param answer
	 * @return Whether the session could be opened, or {@code null} if the
	 *         answer is badly-formed.
	 */
	public Boolean parseOpenSessionAnswer(String answer) {
		return getSuccess(asKeyPairs(answer), answer);
	}

	/**
	 * @param answer
	 * @return Whether the ping could be opened, or {@code null} if the answer
	 *         is badly-formed.
	 */
	public Boolean parsePingSessionAnswer(String answer) {
		return getSuccess(asKeyPairs(answer), answer);
	}

	/**
	 * @param keyPairs
	 * @param wholeAnswer
	 *            For logging purposes only.
	 * @return Whether {@code keyPairs} contains {@code "success" -> "true"}.
	 */
	private static Boolean getSuccess(Map<String, String> keyPairs, String wholeAnswer) {
		final String value = keyPairs.get("success");
		if (value == null) {
			log("success key is missing in answer: " + wholeAnswer);
			return null;
		} else {
			if ("true".equals(value))
				return true;
			else if ("false".equals(value))
				return false;
			else {
				log("success value is unrecognized in answer: " + value);
				return null;
			}
		}
	}

	private static Map<String, String> asKeyPairs(String answer) {
		final List<String> lines = split(answer);
		final int sz = lines.size();
		final Map<String, String> result = new HashMap<String, String>(lines.size());
		for (int i = 0; i < sz; i++) {
			final String line = lines.get(i);
			final String key = key(line);
			if (key == null) {
				log("Could not recognize key in " + line);
				continue;
			}
			if (result.containsKey(key))
				log("Duplicated key: " + key + " in answer: " + answer);
			else {
				final String value = value(line);
				if (value == null)
					log("Could not recognize value in " + line);
				else
					result.put(key, value);
			}
		}
		return result;
	}

	private static List<String> split(String answer) {
		final ArrayList<String> result = new ArrayList<String>(4);

		String current = answer;

		while (true) {
			final int nextEOL = current.indexOf("\r\n");
			if (nextEOL < 0) {
				/* The last line */
				result.add(current);
				break;
			} else {
				result.add(current.substring(0, nextEOL));
				if (nextEOL == current.length() - 2)
					/* The last line */
					break;
				else
					current = current.substring(nextEOL + 2);
			}
		}

		return result;
	}

	/**
	 * @param answer
	 *            A string of the form {@code key:"value"}
	 * @return The key or {@code null} if not found.
	 */
	private static String key(String answer) {
		final int idx = answer.indexOf(":");
		if (idx < 0)
			return null;
		else
			return answer.substring(0, idx);
	}

	/**
	 * @param answer
	 *            A string of the form {@code key:"value"}
	 * @return The value, without its enclosing quotes; or {@code null} if not
	 *         found.
	 */
	private static String value(String answer) {
		final int idx = answer.indexOf(":");
		if (idx < 0)
			return null;
		if (idx == answer.length() - 1)
			/* ":" is the last character */
			return null;
		final String end = answer.substring(idx + 1);
		if (end.startsWith("\"")) {
			if (end.endsWith("\""))
				return end.substring(1, end.length() - 1);
			else
				return null;
		} else
			return null;
	}

	/**
	 * @param answer
	 *            The answer to a request of kind {@code kind}
	 * @param kind
	 * @return Whether {@code answer} represents a success.
	 */
	@SuppressWarnings("unused")
	@Deprecated
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
			if (!equalsBracketed(after, false))
				log("Unrecognized content after \"success:\" in answer of " + kind + ": " + after);
			return false;
		}
	}

	/**
	 * @param s
	 * @param val
	 * @return Whether {@code s} is {@code "true"} or {@code "false"}.
	 */
	@Deprecated
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
	@Deprecated
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
