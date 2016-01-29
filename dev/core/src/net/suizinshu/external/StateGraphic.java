package net.suizinshu.external;

import java.util.HashMap;
import java.util.Map;

import net.suizinshu.file.Fetch;

import com.badlogic.gdx.graphics.Texture;

/**
 * Static class for accessing Texture objects, preventing unnecessary reloading.
 * @author Zicheng Gao
 */
public class StateGraphic {

	private static Map<String, Texture> textures = new HashMap<String, Texture>();

	public static Texture get(String name) {
		if (textures.containsKey(name))
			return textures.get(name);
		else
			return load(name);
	}

	private static Texture load(String name) {
		Texture texture = Fetch.getImg(name);
		textures.put(name, texture);
		return texture;
	}
	
	// TODO maybe call unload for sprites that haven't been used for a long time...
	// Since, well, not doing that would be a memory leak...
	
	public static void unload(String name) {
		if (textures.containsKey(name)) {
			textures.get(name).dispose();
			textures.remove(name);
		}
	}

}
