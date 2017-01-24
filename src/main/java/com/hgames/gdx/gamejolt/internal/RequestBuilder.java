package com.hgames.gdx.gamejolt.internal;

/**
 * An helper class to generate URLs of requests. Nothing smart.
 * 
 * @author smelC
 */
public class RequestBuilder {

	protected final StringBuilder builder;

	protected boolean first = true;

	/**
	 * @param base
	 *            The start of the URL.
	 */
	public RequestBuilder(String base) {
		this.builder = new StringBuilder(base);
	}

	public void append(String s) {
		builder.append(s);
	}

	public void addKeyValuePair(String key, String value) {
		if (key == null)
			throw new NullPointerException("Key should not be null when building a request");
		if (value == null)
			throw new NullPointerException("Value should not be null when building a request");

		builder.append(first ? "?" : "&");
		first = false;
		builder.append(key);
		builder.append('=');
		builder.append(value);
	}

	public String build() {
		return builder.toString();
	}

}
