package net.suizinshu.external;

import java.util.HashMap;
import java.util.function.BiConsumer;

import net.suizinshu.central.Central;
import net.suizinshu.file.Fetch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class Manager_Audio {

	/** List of accessible audio. */
	private static HashMap<String, FileHandle> decodedAudioFiles;

	/** List of encoded audio. */
	private static HashMap<String, FileHandle> encodedAudioFiles;

	/** Active sounds. */
	private static HashMap<String, IndexedSound> sounds;


	/**
	 * Please do not extend from.
	 * @author Zicheng Gao
	 */
	static class IndexedSound {
		Sound sound;

		long index;

		boolean indexed = false;

		public IndexedSound(Sound sound) {
			this.sound = sound;
		}

	}

	/** Currently playing music. */
	public static Music music;


	/* Setup. */
	public static void initialize() {

		String prefix = "resource/mus/";

		FileHandle[] encodedAudioArray = new FileHandle[] {
				Gdx.files.internal(prefix + "abnormality.ogg"),
				Gdx.files.internal(prefix + "walking.ogg"),
				Gdx.files.internal(prefix + "town.ogg")
		};

		decodedAudioFiles = new HashMap<String, FileHandle>(Central.MUS_COUNT);
		encodedAudioFiles = new HashMap<String, FileHandle>(Central.MUS_COUNT);
		
		sounds = new HashMap<String, IndexedSound>(Central.MUS_COUNT);

		for (FileHandle f : encodedAudioArray) {
			encodedAudioFiles.put(f.nameWithoutExtension(), f);	
			decodedAudioFiles.put(f.nameWithoutExtension(), Fetch.fetchMus(f.name()));
		}
	}

	public static void loadMusic(String name) {
		if (music != null)
			music.dispose();
		music = Gdx.audio.newMusic(decodedAudioFiles.get(name));
	}

	public static void stopMusic() {
		if (music != null) {
			music.stop();
			music.dispose();
		}
	}

	public static void loadSound(String name) {
		if (!sounds.containsKey(name)) {
			IndexedSound newSound = new IndexedSound(Gdx.audio.newSound(decodedAudioFiles.get(name)));
			sounds.put(name, newSound);
		}
	}

	public static void playSound(String name, float volume, float pitch, float pan) {
		if (!sounds.containsKey(name)) 
			loadSound(name);
		IndexedSound sound = sounds.get(name);
		if (!sound.indexed) {
			sound.index = sound.sound.play(volume, pitch, pan);
			sound.indexed = true;
		}
	}

	public static void playSound(String name, float volume, float pitch) {
		playSound(name, volume, pitch, 0);
	}

	public static void playSound(String name, float volume) {
		playSound(name, volume, 1, 0);
	}

	public static void playSound(String name) {
		playSound(name, 1, 1, 0);
	}
	
	public static void loopSound(String name, float volume, float pitch, float pan) {
		if (!sounds.containsKey(name)) 
			loadSound(name);
		IndexedSound sound = sounds.get(name);
		if (!sound.indexed) {
			sound.index = sound.sound.loop(volume, pitch, pan);
			sound.indexed = true;
		}
	}

	public static void loopSound(String name, float volume, float pitch) {
		loopSound(name, volume, pitch, 0);
	}

	public static void loopSound(String name, float volume) {
		loopSound(name, volume, 1, 0);
	}

	public static void loopSound(String name) {
		loopSound(name, 1, 1, 0);
	}
	
	public static void funcSound(String name, BiConsumer<Sound, Long> function) {
		IndexedSound sound = sounds.get(name);
		if (sound.indexed)
			function.accept(sound.sound, sound.index);
	}
	
	public static int isSndIndexed(String name) {
		if (sounds.containsKey(name))
			return (sounds.get(name).indexed) ? (1) : (0);
		else
			return -1;
	}

}
