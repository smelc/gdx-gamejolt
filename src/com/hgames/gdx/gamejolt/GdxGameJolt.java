package com.hgames.gdx.gamejolt;

import java.util.List;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.hgames.gdx.gamejolt.internal.AnswerParser;
import com.hgames.gdx.gamejolt.internal.HttpResponseListenerForwarder;
import com.hgames.gdx.gamejolt.internal.RequestBuilder;

/**
 * The entry point to the library. Check subtypes for concrete implementations:
 * 
 * <ul>
 * <li>{@link NonGwtGdxGameJolt} for an implementation that uses the JDK's MD5
 * algorithm.</li>
 * <li>{@link GwtGdxGameJolt} for an implementation that uses a
 * javascript-native MD5 algorithm. It can be compiled to javascript with
 * Google's GWT library.</li>
 * </ul>
 * 
 * <p>
 * Here's this library workflow:
 * 
 * You should:
 * <ul>
 * <li>Provide an implementation of {@link IGdxGameJoltResponseListener}.</li>
 * <li>Do calls to the various methods of this class that take a subclass
 * {@link AbstractRequest} as first parameter.</li>
 * </ul>
 * 
 * Given these two elements, the library takes care of appropriately calling the
 * instance of {@link IGdxGameJoltResponseListener} that you provided, with the
 * answers of the request you posted. For you to know to which request an answer
 * corresponds, the library gives you back your request in callbacks to methods
 * of @{link IGdxGameJoltResponseListener}.
 * 
 * Usually, implementations of {@link IGdxGameJoltResponseListener} store the
 * requests that have been submitted but for which answers haven't been received
 * yet. Then, when an answer comes, the instance fetches from its internal state
 * what to do with it; and removes the request from the unanswered list.
 * </p>
 * 
 * <p>
 * If you're a newcomer to the library, you shouldn't crawl other files for now.
 * To do your first steps with the library, instantiate this class, and setup an
 * appropriate {@link IGdxGameJoltResponseListener}. The latter interface is how
 * you must implement communication between this library and your code.
 * </p>
 * 
 * <p>
 * This class is asynchronous because {@link Gdx#net} is. As a consequence,
 * there may be some time in-between a call to a public method of this class and
 * the corresponding callback on your instance of
 * {@link IGdxGameJoltResponseListener}.
 * </p>
 * 
 * @author smelC
 */
public abstract class GdxGameJolt {

	/**
	 * The tag given to {@link Application#log(String, String)} when this API
	 * logs something. You can change it, but do not set it to {@code null}.
	 */
	public static String TAG = "GdxGameJolt";

	protected final int gameID;
	protected final String gamePrivateKey;

	public final String username;
	public final String userToken;

	protected IGdxGameJoltResponseListener listener;

	protected boolean authentified;

	private final AnswerParser parser = new AnswerParser();

	/**
	 * @param gameID
	 *            Your game's identifier. It is available in your dashboard
	 *            under "Game API / API Settings".
	 * @param gamePrivateKey
	 *            Your game's private key. It is available in your dashboard
	 *            under "Game API / API Settings". You should keep it secret.
	 * @param username
	 *            The username of the current player. If you ship your
	 *            application as an executable recognized by GameJolt's client,
	 *            it is given on the command line ({@code gjapi_username=foo}
	 *            syntax).
	 * @param userToken
	 *            The token of the current player. If you ship your application
	 *            as an executable recognized by GameJolt's client, it is given
	 *            on the command line ({@code gjapi_token=foo} syntax).
	 * @param listener
	 *            How to answer to queries. Can be set later, but must be set
	 *            for request to be sent.
	 */
	public GdxGameJolt(int gameID, String gamePrivateKey, String username, String userToken,
			IGdxGameJoltResponseListener listener) {
		this.gameID = gameID;
		this.gamePrivateKey = gamePrivateKey;

		this.username = username;
		this.userToken = userToken;

		this.listener = listener;
	}

	public void setListener(IGdxGameJoltResponseListener listener) {
		this.listener = listener;
	}

	public void auth(final AuthRequest request) {
		if (!initialized())
			return;

		final RequestBuilder builder = new RequestBuilder("http://gamejolt.com/api/game/v1/users/auth/");
		builder.addKeyValuePair("game_id", String.valueOf(gameID));
		builder.addKeyValuePair("username", username);
		builder.addKeyValuePair("user_token", userToken);

		final HttpRequest http = buildRequest(builder.build());
		if (http == null)
			return;

		Gdx.net.sendHttpRequest(http, new HttpResponseListenerForwarder(listener, request) {
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				final boolean answer = parser.parseAuthAnswer(httpResponse.getResultAsString());
				GdxGameJolt.this.authentified = answer;

				log((answer ? "Successfully authentified" : "Could not authentify") + " " + username + "/"
						+ userToken + " on GameJolt");
			}
		});
	}

	/**
	 * The request for a trophy as specified in
	 * <a href="http://gamejolt.com/api/doc/game/trophies/fetch">GameJolt's
	 * API</a>.
	 * 
	 * @param ftr
	 *            The trophies to request. Must not be empty.
	 */
	public void fetchTrophy(final FetchTrophyRequest ftr) {
		if (!initialized())
			return;

		if (ftr.trophyIDs.length == 0) {
			Gdx.app.log(TAG, "Skipping erroneous 'fetchTrophy' request: it has no trophy identifier.");
			return;
		}

		final RequestBuilder builder = new RequestBuilder("http://gamejolt.com/api/game/v1/trophies/");
		builder.addKeyValuePair("game_id", String.valueOf(gameID));
		builder.addKeyValuePair("username", username);
		builder.addKeyValuePair("user_token", userToken);
		if (ftr.achieved != null)
			builder.addKeyValuePair("achieved", String.valueOf(ftr.achieved));

		builder.append("&trophy_id");
		builder.append("=");
		for (int i = 0; i < ftr.trophyIDs.length; i++) {
			builder.append(ftr.trophyIDs[i]);
			if (i < ftr.trophyIDs.length - 1)
				/* Not the last one */
				builder.append(",");
		}

		final HttpRequest http = buildRequest(builder.build());
		if (http == null)
			return;

		Gdx.net.sendHttpRequest(http, new HttpResponseListenerForwarder(listener, ftr) {
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				final List<FetchTrophyAnswer> trophies = parser
						.parseFetchTrophyAnswer(httpResponse.getResultAsString());

				if (listener != null)
					listener.fetchedTrophies(ftr, trophies);
			}
		});
	}

	/**
	 * Launches a request to record that a trophy was achieved, as specified in
	 * <a href="http://gamejolt.com/api/doc/game/trophies/add-achieved">GameJolt
	 * 's API</a>.
	 * 
	 * @param atr
	 *            The trophy to add
	 */
	public void addTrophy(final AddTrophyRequest atr) {
		if (!initialized())
			return;

		final RequestBuilder builder = new RequestBuilder(
				"http://gamejolt.com/api/game/v1/trophies/add-achieved");
		builder.addKeyValuePair("game_id", String.valueOf(gameID));
		builder.addKeyValuePair("username", username);
		builder.addKeyValuePair("user_token", userToken);
		builder.addKeyValuePair("trophy_id", atr.trophyID);

		final HttpRequest http = buildRequest(builder.build());
		if (http == null)
			return;

		Gdx.net.sendHttpRequest(http, new HttpResponseListenerForwarder(listener, atr) {
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				final boolean answer = parser.parseAddTropyAnswer(httpResponse.getResultAsString());

				if (listener != null)
					listener.addTrophies(atr, answer);
			}
		});
	}

	protected /* @Nullable */ HttpRequest buildRequest(String request) {
		System.out.println("Received request: " + request);

		/* Generate signature */
		final String signature;
		try {
			signature = md5(request + gamePrivateKey);
		} catch (Exception e) {
			/* Do not leak 'gamePrivateKey' in log */
			Gdx.app.log(TAG, "Cannot honor request: " + request, e);
			return null;
		}
		/* Append signature */
		String complete = request;
		complete += "&";
		complete += "signature";
		complete += "=";
		complete += signature;

		final HttpRequest http = new HttpRequest();
		http.setMethod(HttpMethods.GET);
		http.setUrl(complete);

		System.out.println("Built request: " + complete);

		return http;
	}

	protected void log(String log) {
		if (Gdx.app == null) {
			/* Can happen when app is initializing */
			System.out.println("[" + TAG + "] " + log);
			return;
		} else
			Gdx.app.log(TAG, log);
	}

	protected boolean initialized() {
		return Gdx.net != null && username != null && userToken != null && listener != null;
	}

	protected abstract String md5(String s) throws Exception;

}
