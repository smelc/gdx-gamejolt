# gdx-gamejolt

An implementation of [GameJolt](gamejolt.com)'s [API](http://gamejolt.com/api/doc/game) that uses [libgdx](https://github.com/libgdx/libgdx) to send HTTP requests. Here are the library's advantages over other Java implementations of GameJolt's API:

* This library's only dependency is libgdx.
* This library is compatible with the GWT compiler (no gson parsing, no java.io).
* This library is well documented. The library's center is [GdxGameJolt](https://github.com/smelc/gdx-gamejolt/blob/master/src/com/hgames/gdx/gamejolt/GdxGameJolt.java). The interaction between this class and your code is done by an instance [IGdxGameJoltResponseListener](https://github.com/smelc/gdx-gamejolt/blob/master/src/com/hgames/gdx/gamejolt/IGdxGameJoltResponseListener.java) that you should provide.

Here are the library's defects:

* It only implements the following requests:
    * Authentication of the player
    * Open a session
    * Ping that a session is active
    * Close a session
    * Add an achievement
