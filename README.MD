# gdx-gamejolt

An implementation of [GameJolt](gamejolt.com)'s [API](http://gamejolt.com/api/doc/game) that uses [libgdx](https://github.com/libgdx/libgdx) to send HTTP requests. Here are this library's advantages over other Java implementations of GameJolt's API:

* This library's only dependency is libgdx.
* This library is compatible with the GWT compiler (no gson parsing, no java.io).
* This library's API is tight and object-oriented.
* This library is well documented. The library's center is [GdxGameJolt](https://github.com/smelc/gdx-gamejolt/blob/master/src/main/java/com/hgames/gdx/gamejolt/GdxGameJolt.java). The interaction between this class and your code is done by an instance [IGdxGameJoltResponseListener](https://github.com/smelc/gdx-gamejolt/blob/master/src/main/java/com/hgames/gdx/gamejolt/IGdxGameJoltResponseListener.java) that you should provide.

Here are this library's defects:

* It only implements the following requests:
    * Authentication of the player
    * Open a session
    * Ping that a session is active
    * Close a session
    * Add an achievement
    * Fetch achievements
    * Add a score
    * Fetch scores

Code in this library is in the public domain (see LICENSE).
