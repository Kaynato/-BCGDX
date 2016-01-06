package net.suizinshu.external;

import java.util.HashMap;

import net.suizinshu.file.Fetch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class MusicManager {
	
	///////////
	// MUSIC //
	///////////
	
	/** List of music. */
	private static HashMap<String, FileHandle> musCache;

	/** List of the files of the unencrypted music. */
	private static FileHandle[] oldMusFiles;

	/** Number of music files. */
	private static int count;

	static {
		initializeMusic();
	}

	/* Setup. */
	private static void initializeMusic() {
		FileHandle musicDirectory = Gdx.files.internal("resources/mus");
		
		oldMusFiles = musicDirectory.list();
		
		count = oldMusFiles.length;
		musCache = new HashMap<String, FileHandle>(count);
		for (FileHandle f : oldMusFiles)
				musCache.put(f.nameWithoutExtension(), Fetch.fetchMus(f.name()));
				
	}

	
	
}
