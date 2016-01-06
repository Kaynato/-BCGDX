package net.suizinshu.external;

import java.util.HashMap;
import java.util.Map;

import net.suizinshu.file.Fetch;

import com.badlogic.gdx.graphics.Texture;

/**
 * Static class for accessing Texture objects, preventing unnecessary reloading.
 * @author Zicheng Gao
 */
public class CacheGraphic {

	private static Map<String, Texture> textures = new HashMap<String, Texture>();

	public static Texture get(String name) {
		if (textures.containsKey(name))
			return textures.get(name);
		else {
			Texture texture = Fetch.getImg(name);
			textures.put(name, texture);
			return texture;
		}
	}


}
