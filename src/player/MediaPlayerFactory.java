package player;

/**
 * Factory which will return the media player
 * 
 */
public class MediaPlayerFactory {

	private static WinAmpPlayer winAmp = null;

	public static MediaPlayer getMediaPlayer() {
		if (winAmp == null) {
			winAmp = new WinAmpPlayer();
		}
		return winAmp;
	}
}
