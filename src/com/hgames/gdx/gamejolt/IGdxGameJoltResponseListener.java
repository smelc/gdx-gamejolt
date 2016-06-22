package com.hgames.gdx.gamejolt;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpResponseListener;

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
	 * @param answer
	 *            Whether {@code request} was a success.
	 */
	void auth(AuthRequest request, boolean answer);

	/**
	 * @param request
	 *            The request
	 * @param added
	 *            Whether {@code request} was a success.
	 */
	void addTrophies(AddTrophyRequest request, boolean added);

	/**
	 * @param request
	 *            The request.
	 * @param trophies
	 *            Trophies received as an answer to {@code request}.
	 */
	void fetchedTrophies(FetchTrophyRequest request, List<FetchTrophyAnswer> trophies);

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
