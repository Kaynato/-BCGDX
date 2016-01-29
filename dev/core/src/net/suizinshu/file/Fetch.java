package net.suizinshu.file;

import net.suizinshu.external.Central;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class Fetch {

	/* 
	 * Encrypted files should be stored internally.
	 * Thus, get from Gdx.files.internal(path)
	 * Decode into the temp, access the file data, be done with the file.
	 */

	/** INPUT: Where encrypted resources are stored.*/
	private static final String resourceDir = "resource/";

	/** OUTPUT: Internal directories in temp/wander. */
	private static final String mainDirectory = "wander/",
			sprDirectory = mainDirectory + "spr/",
			musDirectory = mainDirectory + "mus/",
			filDirectory = mainDirectory + "fil/";

	/** OUTPUT: Temporary file folder. */
	private static final FileHandle tempFolder = Gdx.files.absolute(Central.TMPDIR + mainDirectory);

	/**
	 * Fetches a Texture from an encoded png.
	 * @param fileName	Filename of texture to fetch. Must be a .png.
	 * @return			Texture containing the decoded file. If invalid, returns a default texture.
	 */
	public static Texture getImg(String fileName) {

		// Access encoded file
		FileHandle inputFile = Gdx.files.internal(resourceDir + fileName + ".png");
		
		// Check existence and ascribe texture
		if (inputFile.exists()) {
			FileHandle outputFile = Decoder.tempdecrypt(inputFile, sprDirectory);
			Texture spr = new Texture(outputFile);
			outputFile.delete();
			return spr;
		}
		else
			return Central.DEFAULT_TEXTURE;
	}

	/**
	 * Decodes a music and returns location. Extension (.wav .mp3) required.
	 * @param fileName	Filename of music file.
	 * @return			Path to decoded file.
	 */
	public static FileHandle fetchMus(String fileName) {
		return Decoder.tempdecrypt(Gdx.files.internal(resourceDir + "mus/" + fileName), musDirectory);
	}
	
	/**
	 * All this code isn't the best, but, really, it doesn't make it slower or anything...?<br>
	 * Puts file in "filDirectory" anyway.
	 * @param fileName Filename of file to search for.
	 * @return fileHandle	Returned file.
	 */
	public static FileHandle fetchFile(String fileName) {
		return Decoder.tempdecrypt(Gdx.files.internal(resourceDir + fileName), filDirectory);
	}

	/** Remove the entire temp folder */
	public static void removeTemp() {
		tempFolder.deleteDirectory();
		tempFolder.delete();
	}

}
