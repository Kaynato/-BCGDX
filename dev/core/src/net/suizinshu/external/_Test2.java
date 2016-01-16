package net.suizinshu.external;

import java.util.ArrayList;

import net.suizinshu.external.component.SpriteDecal;
import net.suizinshu.file.Fetch;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;


public class _Test2 {
	

	private static DecalBatch batch;
	private static ArrayList<Decal> decals = new ArrayList<Decal>();
	
	public static void create(Camera camera) {

		batch = new DecalBatch(new CameraGroupStrategy(camera));
		
		TextureRegion[] textures = {new TextureRegion(Fetch.getImg("cat")),
				new TextureRegion(Fetch.getImg("tieman")),
				new TextureRegion(Fetch.getImg("bounds"))};
		
		SpriteDecal de1 = new SpriteDecal("cat");
		de1.decal.setPosition(0, 0, 0);
		de1.decal.setDimensions(1, 1);
		decals.add(de1.decal);
		
		Decal decal = Decal.newDecal(1, 1, textures[0], true);
		decal.setPosition(0.5f, 0.5f, 1);
		decals.add(decal);
		
		decal = Decal.newDecal(50, 30, textures[2], true);
		decal.setPosition(0, 0, 0);
		decal.rotateX(90);
		decals.add(decal);
		
		decal = Decal.newDecal(1, 1, textures[1], true);
		decal.setPosition(1, 1, -1);
		decals.add(decal);
	}
	
	public static void render(Camera camera) {

		camera.update();
		for (Decal decal : decals) {
//			decal.lookAt(camera.position, camera.up);
			batch.add(decal);
		}
		batch.flush();
		
	}
	
}
