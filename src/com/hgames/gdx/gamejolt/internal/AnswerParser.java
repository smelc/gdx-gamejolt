package com.hgames.gdx.gamejolt.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hgames.gdx.gamejolt.GdxGameJolt;
import com.hgames.gdx.gamejolt.answers.Difficulty;
import com.hgames.gdx.gamejolt.answers.FetchTrophiesAnswer;
import com.hgames.gdx.gamejolt.answers.FetchTrophiesAnswer.FetchedTrophy;
import com.hgames.gdx.gamejolt.requests.RequestKind;

/**
 * @author smelC
 */
public class AnswerParser {

	protected final GdxGameJolt api;

	public AnswerParser(GdxGameJolt api) {
		this.api = api;
	}

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
			api.log("Key \"message\" is missing in answer to " + RequestKind.ADD_TROPHY + " request", null);
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
	// id:"59133"
	// title:"Bronze Mercenary 6"
	// description:"Reach the dungeon's depth 6"
	// difficulty:"Bronze"
	// image_url:"http://s.gjcdn.net/img/trophy-bronze-1.jpg"
	// achieved:"false"
	// id:"60592"
	// title:"Chuck Norris"
	// description:"Kill a monster with your fists"
	// difficulty:"Bronze"
	// image_url:"http://s.gjcdn.net/img/trophy-bronze-1.jpg"
	// achieved:"false"
	// id:"60619"
	// title:"Pyrotechnist"
	// description:"Blow up a door with a dynamite bomb"
	// difficulty:"Bronze"
	// image_url:"http://s.gjcdn.net/img/trophy-bronze-1.jpg"
	// achieved:"1 day ago"
	public /* @Nullable */ FetchTrophiesAnswer parseFetchTrophyAnswer(String answer) {
		/*
		 * Don't change this parser naively, it is pretty smart as it doesn't
		 * split 'answer' recursively, but line by line, to minimize
		 * allocations. 'answer' can be large if all achievements are requested.
		 */

		int curstart;

		final int len = answer.length();

		{
			final int idx = answer.indexOf("\r\n");
			final String beginning = idx < 0 ? answer : answer.substring(0, idx);
			/*
			 * idx < 0 can happen if no trophy was requested I guess, the answer
			 * containing the single line 'success:"true"'
			 */
			final Map<String, String> keyPairs = asKeyPairs(beginning);
			final Boolean success = getSuccess(keyPairs, answer);
			if (success == null || !success)
				return null;
			if (idx < 0 || len <= idx + 2)
				return null;

			curstart = idx + 2;
		}

		final List<FetchedTrophy> result = new ArrayList<FetchedTrophy>(32);

		final int singleTrophyNbOfLines = 6;
		final String[] buf = new String[singleTrophyNbOfLines];

		while (true) {
			/* Parsing: */
			// id:"60592"
			// title:"Chuck Norris"
			// description:"Kill a monster with your fists"
			// difficulty:"Bronze"
			// image_url:"http://s.gjcdn.net/img/trophy-bronze-1.jpg"
			// achieved:"false"
			curstart = nextLines(answer, curstart, singleTrophyNbOfLines, buf);
			final String id = valueOfKey(buf[0], "id");
			final String title = valueOfKey(buf[1], "title");
			final String description = valueOfKey(buf[2], "description");
			final String difficulty = valueOfKey(buf[3], "difficulty");
			final String image_url = valueOfKey(buf[4], "image_url");
			final String achieved = valueOfKey(buf[5], "achieved");
			if (id == null) {
				api.log("Skipping a trophy with a null ID", null);
			} else {
				final Difficulty dif = Difficulty.of(difficulty);
				result.add(
						new FetchedTrophy(id, title, description, dif, image_url, !"false".equals(achieved)));
			}
			if (curstart < 0 || len <= curstart)
				break;
		}

		return new FetchTrophiesAnswer(result);
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
	private Boolean getSuccess(Map<String, String> keyPairs, String wholeAnswer) {
		final String value = keyPairs.get("success");
		if (value == null) {
			api.log("success key is missing in answer: " + wholeAnswer, null);
			return null;
		} else {
			if ("true".equals(value))
				return true;
			else if ("false".equals(value))
				return false;
			else {
				api.log("success value is unrecognized in answer: " + value, null);
				return null;
			}
		}
	}

	private Map<String, String> asKeyPairs(String answer) {
		final List<String> lines = splitInLines(answer);
		final int sz = lines.size();
		final Map<String, String> result = new HashMap<String, String>(lines.size());
		for (int i = 0; i < sz; i++) {
			final String line = lines.get(i);
			final String key = key(line);
			if (key == null) {
				api.log("Could not recognize key in " + line, null);
				continue;
			}
			if (result.containsKey(key))
				api.log("Duplicated key: " + key + " in answer: " + answer, null);
			else {
				final String value = value(line);
				if (value == null)
					api.log("Could not recognize value in " + line, null);
				else
					result.put(key, value);
			}
		}
		return result;
	}

	/**
	 * @param keyValue
	 * @param key
	 * @return The value associated to {@code key} in {@code keyValue}, if
	 *         {@code key} is indeed a key. {@code null} if something
	 *         unrecognized.
	 */
	private String valueOfKey(String keyValue, String key) {
		if (keyValue == null)
			return null;
		final String k = key(keyValue);
		if (k == null || !k.equals(key)) {
			api.log("Expected key: " + key + ", but received: " + k, null);
			return null;
		} else
			return value(keyValue);
	}

	/**
	 * @param string
	 * @param start
	 *            The index at which to start the search in {@code string}
	 * @param nb
	 *            The number of lines to read from {@code string}, starting at
	 *            {@code start}.
	 * @param buf
	 *            An array of size {@code nb} (or more), where to write the
	 *            first nb lines
	 * @return THe index of the continuation (the next {@code start}), or
	 *         anything negative to tell to stop.
	 */
	private int nextLines(String string, int start, int nb, String[] buf) {
		/* Reinit result */
		for (int i = 0; i < buf.length; ++i)
			buf[i] = null;

		final int len = string.length();

		int now = 0;
		int current = start;

		while (now < nb) {
			final int idx = string.indexOf("\r\n", current);
			if (idx < 0) {
				/* The last line */
				buf[now] = string.substring(current);
				return -1;
			} else {
				final String line = string.substring(current, idx);
				buf[now] = line;
				if (len <= idx - 1) {
					return -1;
				} else {
					now++;
					current = idx + 2;
				}
			}
		}
		return current;
	}

	private List<String> splitInLines(String answer) {
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
	private String key(String answer) {
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
	private String value(String answer) {
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
}
