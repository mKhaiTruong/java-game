package application;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {
	protected String path;
	private Media sound;
	private MediaPlayer mediaPlayer;
	
	public Sound() {
		sound = new Media(getClass().getResource(getPath()).toExternalForm());
		mediaPlayer = new MediaPlayer(sound);	
	}
	
	public void playSound() {
		mediaPlayer.play();
	}
	
	public String getPath() {
		return path;
	}
}
