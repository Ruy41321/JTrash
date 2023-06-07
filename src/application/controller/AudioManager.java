package application.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioManager {
	/** mouse click audio */
	public static final String clickSound = AudioManager.class.getResource("/audio/click.wav").toString().substring(5);
	/** mouse click with no effect audio */
	public static final String nullClickSound = AudioManager.class.getResource("/audio/nullClick.wav").toString()
			.substring(5);
	/** card showed up audio */
	public static final String cardSwipeSound = AudioManager.class.getResource("/audio/pickUp.wav").toString()
			.substring(5);
	/** continue or not vegeth audio */
	public static final String continueOrNot = AudioManager.class.getResource("/audio/continueOrNot.wav").toString()
			.substring(5);
	/** saying trash audio */
	public static final String trash = AudioManager.class.getResource("/audio/trash.wav").toString().substring(5);
	/** clapping hands audio */
	public static final String claps = AudioManager.class.getResource("/audio/claps.wav").toString().substring(5);
	/** jazz music */
	public static final String jazz = AudioManager.class.getResource("/audio/jazz.wav").toString().substring(5);
	/** the last sound reproduced */
	private Clip lastSound;
	/** background music clip */
	private Clip bg;
	/** instance of this singleton class */
	private static AudioManager instance;

	/** getter of instance 
	 *@return the instance 
	 */
	public static AudioManager getInstance() {
		if (instance == null)
			instance = new AudioManager();
		return instance;
	}

	/**
	 * private constructor to make the class available only through getInstance()
	 */
	private AudioManager() {

	}

	/**
	 * Method used to reproduce the audio file got as parameter
	 * @param filename the path of the file to reproduce
	 */
	public void play(String filename) {

		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(filename));
			Clip clip = AudioSystem.getClip();
			lastSound = clip;

			clip.open(audioIn);
			((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(-15f);
			clip.start();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Method used to stop the last Sound 
	 */
	public void stopLast() {
		lastSound.stop();
	}

	/**
	 * Method used to set the music on background
	 * @param filename The path of the audio file
	 */
	public void setBackgroundMusic(String filename) {
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(filename));
			bg = AudioSystem.getClip();

			bg.open(audioIn);
			((FloatControl) bg.getControl(FloatControl.Type.MASTER_GAIN)).setValue(-25f);
			bg.loop(Clip.LOOP_CONTINUOUSLY);
			bg.start();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
	}
}