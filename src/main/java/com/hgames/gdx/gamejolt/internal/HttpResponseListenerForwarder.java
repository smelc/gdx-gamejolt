package com.hgames.gdx.gamejolt.internal;

import com.badlogic.gdx.Net.HttpResponseListener;
import com.hgames.gdx.gamejolt.GdxGameJolt;
import com.hgames.gdx.gamejolt.IGdxGameJoltResponseListener;
import com.hgames.gdx.gamejolt.requests.AbstractRequest;

/**
 * An helper class for the implementation of {@link GdxGameJolt}, that does
 * forwarding to the client's listener (the delegate here).
 * 
 * @author smelC
 */
public abstract class HttpResponseListenerForwarder implements HttpResponseListener {

	protected final GdxGameJolt api;
	protected final IGdxGameJoltResponseListener delegate;
	protected final AbstractRequest request;

	public HttpResponseListenerForwarder(GdxGameJolt api, IGdxGameJoltResponseListener delegate,
			AbstractRequest request) {
		this.api = api;
		this.delegate = delegate;
		this.request = request;
	}

	@Override
	public void failed(Throwable t) {
		if (delegate == null) {
			/*
			 * This may not be a bug, for example if a connection isn't
			 * available, it'll trigger a 'java.net.UnknownHostException'.
			 */
			api.log("A request failed, this may not be a bug (no connection)", t);
		} else {
			/* Forward to client, adding the concerned request. */
			delegate.failed(request, t);
		}
	}

	@Override
	public void cancelled() {
		if (delegate != null) {
			/* Forward to client, adding the concerned request. */
			delegate.cancelled(request);
		}
	}

}
