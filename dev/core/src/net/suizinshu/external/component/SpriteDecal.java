package net.suizinshu.external.component;

import net.suizinshu.file.Fetch;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;


public class SpriteDecal extends Component {
	
	public Texture image;
	public Decal sprite;
	
	public SpriteDecal(String img, 
			TextureFilter minFilter, TextureFilter maxFilter, 
			TextureWrap texWrapU, TextureWrap texWrapV) {
		image = Fetch.getImg(img);
        image.setFilter(minFilter, maxFilter);
        image.setWrap(texWrapU, texWrapV);

        float w = image.getWidth();
        float h = image.getHeight();
        
        sprite = Decal.newDecal(w, h, new TextureRegion(image), true);
	}
	
	public SpriteDecal(String img, TextureFilter filter, TextureWrap wrap) {
		this(img, filter, filter, wrap, wrap);
	}
	
	public SpriteDecal(String img) {
		this(img, TextureFilter.MipMapLinearNearest, TextureWrap.ClampToEdge);
	}
	
}
