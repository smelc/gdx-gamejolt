package com.hgames.gdx.gamejolt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.hgames.gdx.gamejolt.answers.FetchTrophiesAnswer;
import com.hgames.gdx.gamejolt.requests.AbstractRequest;
import com.hgames.gdx.gamejolt.requests.AddTrophyRequest;
import com.hgames.gdx.gamejolt.requests.AuthRequest;
import com.hgames.gdx.gamejolt.requests.CloseSessionRequest;
import com.hgames.gdx.gamejolt.requests.FetchTrophyRequest;
import com.hgames.gdx.gamejolt.requests.OpenSessionRequest;
import com.hgames.gdx.gamejolt.requests.PingSessionRequest;

/**
 * The interface that clients of {@link GdxGameJolt} should implement. Calls to
 * these methods are asynchronous with respect to submission of request in
 * {@link GdxGameJolt}, because {@link Gdx#net} is asynchronous.
 * 
 * @author smelC
 */
public interface IGdxGameJoltResponseListener {

	/**
	 * @param request
	 *            The request
	 * @param added
	 *            Whether {@code request} was a success.
	 */
	void addTrophies(AddTrophyRequest request, boolean added);

	/**
	 * @param request
	 *            The request
	 * @param answer
	 *            Whether {@code request} was a success.
	 */
	void auth(AuthRequest request, boolean answer);

	/**
	 * @param request
	 *            The request
	 * @param answer
	 *            Whether {@code request} was a success.
	 */
	void closedSession(CloseSessionRequest request, Boolean answer);

	/**
	 * @param request
	 *            The request.
	 * @param trophies
	 *            Trophies received as an answer to {@code request}.
	 */
	void fetchedTrophies(FetchTrophyRequest request, FetchTrophiesAnswer trophies);

	/**
	 * @param request
	 *            The request
	 * @param answer
	 *            Whether {@code request} was a success.
	 */
	void openedSession(OpenSessionRequest request, boolean answer);

	/**
	 * @param request
	 *            The request
	 * @param answer
	 *            Whether {@code request} was a success.
	 */
	void pingedSession(PingSessionRequest request, boolean answer);

	/**
	 * See {@link HttpResponseListener#failed(Throwable)}.
	 * 
	 * @param request
	 *            The request whose sending caused {@code t}.
	 * @param t
	 */
	void failed(AbstractRequest request, Throwable t);

	void cancelled(AbstractRequest request);

}
