package com.hgames.gdx.gamejolt.internal;

import com.badlogic.gdx.Net.HttpResponseListener;
import com.hgames.gdx.gamejolt.AbstractRequest;
import com.hgames.gdx.gamejolt.GdxGameJolt;
import com.hgames.gdx.gamejolt.IGdxGameJoltResponseListener;

/**
 * An helper class for the implementation of {@link GdxGameJolt}, that does
 * forwarding to the client's listener (the delegate here).
 * 
 * @author smelC
 */
public abstract class HttpResponseListenerForwarder implements HttpResponseListener {

	protected final IGdxGameJoltResponseListener delegate;
	protected final AbstractRequest request;

	public HttpResponseListenerForwarder(IGdxGameJoltResponseListener delegate, AbstractRequest request) {
		this.delegate = delegate;
		this.request = request;
	}

	@Override
	public void failed(Throwable t) {
		/* Forward to client, adding the concerned request. */
		delegate.failed(request, t);
	}

	@Override
	public void cancelled() {
		/* Forward to client, adding the concerned request. */
		delegate.cancelled(request);
	}

}
