package net.suizinshu.external.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.*;

public class MyBulletTest implements ApplicationListener {

	/**
	 * Start bullet by itself,
	 * Figure it out,
	 * Get it to work,
	 * Then move it over to Artemis piece-by-piece
	 */
	
	private btCollisionConfiguration collisionConfig;
	private btDispatcher dispatcher;
	private btBroadphaseInterface broadphase;
	private btCollisionWorld collisionWorld;
	private ContactListener contactListener;
	
	private btCollisionObject big;
	private btCollisionObject tiny;


	class MyContactListener extends ContactListener {
		@Override
		public boolean onContactAdded (int userValue0, int partId0, int index0, int userValue1, int partId1, int index1) {
			System.out.println("added");
			return true;
		}
	}
	
	@Override
	public void create() {
		Bullet.init();

		collisionConfig = new btDefaultCollisionConfiguration();
		dispatcher = new btCollisionDispatcher(collisionConfig);
		broadphase = new btDbvtBroadphase();
		collisionWorld = new btCollisionWorld(dispatcher, broadphase, collisionConfig);
		contactListener = new MyContactListener();
		
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void render() {
		
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {
		collisionWorld.dispose();
		broadphase.dispose();
		dispatcher.dispose();
		collisionConfig.dispose();

		contactListener.dispose();
	}
	
	public static void main(String[] args) {
		new LwjglApplication(new MyBulletTest());
	}

}
