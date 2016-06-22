package com.hgames.gdx.gamejolt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * An implementation of {@link GdxGameJolt} that is suited for non-gwt builds
 * (that is desktop, android, or ios).
 * 
 * @author smelC
 * 
 * @see GwtGdxGameJolt A GWT-compatible implementation of {@link GdxGameJolt}.
 */
@GwtIncompatible
public class NonGwtGdxGameJolt extends GdxGameJolt {

	/**
	 * @param gameID
	 *            Your game's identifier. It is available in your dashboard
	 *            under "Game API / API Settings".
	 * @param gamePrivateKey
	 *            Your game's private key. It is available in your dashboard
	 *            under "Game API / API Settings". You should keep it secret.
	 * @param username
	 *            The username of the current player.
	 * @param userToken
	 *            The token of the current player.
	 * @param listener
	 *            How to answer to queries. Can be set later, but must be set
	 *            for request to be sent.
	 */
	public NonGwtGdxGameJolt(int gameID, String gamePrivateKey, String username, String userToken,
			IGdxGameJoltResponseListener listener) {
		super(gameID, gamePrivateKey, username, userToken, listener);
	}

	@Override
	protected String md5(String s) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		final MessageDigest md = MessageDigest.getInstance("MD5");
		final byte[] bytes = s.getBytes("UTF-8");
		final byte[] digest = md.digest(bytes);
		/**
		 * Magic to convert it into an String: an answer at the bottom of:
		 * 
		 * <pre>
		 * http://stackoverflow.com/questions/415953/how-can-i-generate-an-md5-hash
		 * </pre>
		 */
		final StringBuffer sb = new StringBuffer();
		for (int i = 0; i < digest.length; ++i) {
			sb.append(Integer.toHexString((digest[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}

}
