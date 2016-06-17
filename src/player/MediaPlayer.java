package player;

/**
 * Abstract MediaPlayer class
 */
public abstract class MediaPlayer {

	public abstract void play();

	public abstract void stop();

	public abstract void pause();

	public abstract void playNext();

	public abstract void playPrev();

	public abstract void repeat();

	public abstract void shuffle();
	
	public abstract void exit();

	public abstract void volumeUp();

	public abstract void volumeDown();

	public abstract void addFileToPlaylist(String path);

	public abstract void addFolderToPlaylist(String path);

	public abstract void fastForward();

	public abstract void slowRewind();

	public abstract void playSelectedSong(int track);

	public abstract int getPlayListLength();

	public abstract String getFileNameInList(int track);
}
