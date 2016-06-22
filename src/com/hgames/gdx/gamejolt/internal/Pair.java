package com.hgames.gdx.gamejolt.internal;

/**
 * @author smelC
 * 
 * @param <T>
 * @param <U>
 */
@Deprecated
class Pair<T, U> {

	public final T fst;
	public final U snd;

	public Pair(T fst, U snd) {
		this.fst = fst;
		this.snd = snd;
	}

	public Pair<T, U> of(T fst, U snd) {
		return new Pair<T, U>(fst, snd);
	}

}
