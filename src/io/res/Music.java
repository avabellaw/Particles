package io.res;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music {

	private Clip clip;

	public static Music background = new Music("res/audio/fast thing.wav");
	public static Music bee = new Music("res/audio/bee_buzz.wav");
	private int offSet = 0;
	AudioInputStream audioIn;

	boolean isMuted = false;
	
	public Music(String path) {
		try {
			audioIn = AudioSystem.getAudioInputStream(new File(path));
			clip = AudioSystem.getClip();
			clip.open(audioIn);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}
	
	public void setVolume(int amnt) {
		FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		volume.setValue(amnt);
	}

	public void offSet(int offSet) {
		this.offSet = offSet;
	}
	
	public void beginning() {
		clip.setFramePosition(0);
	}

	public void start() {
		if(isMuted) return;
		clip.setFramePosition(offSet);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void pause() {
		if (clip != null) {
			clip.stop();
		}
	}

	public void resume() {
		if(isMuted) return;
		if (clip != null) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} else System.out.println("There's no music to resume");
	}
	
	public void mute(boolean mute) {
		isMuted = mute;
	}
}
