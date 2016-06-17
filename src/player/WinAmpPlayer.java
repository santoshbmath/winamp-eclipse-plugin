package player;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.qotsa.exception.InvalidHandle;
import com.qotsa.exception.InvalidParameter;
import com.qotsa.jni.controller.WinampController;

/**
 * It's a winamp utility class, which will have all the methods.
 */
public class WinAmpPlayer extends MediaPlayer {

	/**
	 * Default constructor, load the library here
	 */
	public WinAmpPlayer() {
		System.loadLibrary("wpcom");
	}

	/**
	 * Starts the winamp
	 */
	public static boolean startPlayer() {
		try {
			WinampController.getStatus();
			return true;
		} catch (InvalidHandle e) {
			MessageDialog.openInformation(Display.getCurrent()
					.getActiveShell(), "Launch WinAmp",
					"Winamp is not running. Please launch the winamp and click again.");
			return false;
		}
	}

	/**
	 * Plays the current track
	 */
	public void play() {
		try {
			if(!startPlayer())
				return;
			WinampController.play();
		} catch (InvalidHandle e) {
			logException("play(): " + e.getMessage());
		} catch (Exception e) {
			logException("play(): " + e.getMessage());
		}
	}

	/**
	 * Stops the current playing track
	 */
	public void stop() {
		try {
			if(!startPlayer())
				return;

			WinampController.stop();
		} catch (InvalidHandle e) {
			logException("stop(): " + e.getMessage());
		} catch (Exception e) {
			logException("stop(): " + e.getMessage());
		}
	}

	/**
	 * Pauses the current playing track
	 */
	public void pause() {
		try {
			if(!startPlayer())
				return;

			WinampController.pause();
		} catch (InvalidHandle e) {
			logException("pause(): " + e.getMessage());
		} catch (Exception e) {
			logException("pause(): " + e.getMessage());
		}
	}

	/**
	 * Plays next track
	 */
	public void playNext() {
		try {
			if(!startPlayer())
				return;

			WinampController.nextTrack();
		} catch (InvalidHandle e) {
			logException("playNext(): " + e.getMessage());
		} catch (Exception e) {
			logException("playNext(): " + e.getMessage());
		}
	}

	/**
	 * Plays previous track
	 */
	public void playPrev() {
		try {
			if(!startPlayer())
				return;

			WinampController.previousTrack();
		} catch (InvalidHandle e) {
			logException("playPrev(): " + e.getMessage());
		} catch (Exception e) {
			logException("playPrev(): " + e.getMessage());
		}
	}

	/**
	 * Increases volume of winamp
	 */
	public void volumeUp() {
		try {
			WinampController.increaseVolume();
		} catch (InvalidHandle e) {
			logException("volumeUp(): " + e.getMessage());
		}
	}

	/**
	 * Decreases volume of winamp
	 */
	public void volumeDown() {
		try {
			WinampController.decreaseVolume();
		} catch (InvalidHandle e) {
			logException("volumeDown(): " + e.getMessage());
		}
	}

	/**
	 * Enables/disables repeat
	 */
	public void repeat() {
		try {
			if (WinampController.isRepeatStatusOn())
				WinampController.setRepeatStatusOn(false);
			else
				WinampController.setRepeatStatusOn(true);
		} catch (InvalidHandle e) {
			MessageDialog
					.openInformation(Display.getCurrent().getActiveShell(),
							"Launch WinAmp",
							"Winamp is not running. Launch the winamp and click this button");
		}
	}

	/**
	 * Enables/Disables shuffle
	 */
	public void shuffle() {
		try {
			if (WinampController.isShuffleStatusOn())
				WinampController.setShuffleStatusOn(false);
			else
				WinampController.setShuffleStatusOn(true);
		} catch (InvalidHandle e) {
			MessageDialog
					.openInformation(Display.getCurrent().getActiveShell(),
							"Launch WinAmp",
							"Winamp is not running. Launch the winamp and click this button");
		}
	}

	/**
	 * Add the track to the playlist
	 * 
	 * @param path
	 *            file path to add to playlist
	 */
	public void addFileToPlaylist(String path) {
		try {
			if(!startPlayer())
				return;

			WinampController.appendToPlayList(path);
		} catch (InvalidHandle e) {
			logException("addFileToPlaylist(): " + e.getMessage());
		} catch (InvalidParameter e) {
			logException("addFileToPlaylist(): " + e.getMessage());
		}
	}

	/**
	 * Adds all the media tracks to playlist
	 * 
	 * @param path
	 */
	public void addFolderToPlaylist(String path) {
		addFileToPlaylist(path);
	}

	/**
	 * Forwards current playing track by 5 seconds
	 */
	public void fastForward() {
		try {
			WinampController.fwd5Secs();
		} catch (InvalidHandle e) {
			MessageDialog
					.openInformation(Display.getCurrent().getActiveShell(),
							"Launch WinAmp",
							"Winamp is not running. Launch the winamp and click this button");
		}
	}

	/**
	 * Rewinds current playing track by 5 seconds
	 */
	public void slowRewind() {
		try {
			WinampController.rew5Secs();
		} catch (InvalidHandle e) {
			MessageDialog
					.openInformation(Display.getCurrent().getActiveShell(),
							"Launch WinAmp",
							"Winamp is not running. Launch the winamp and click this button");
		}
	}

	/**
	 * Plays the track with the given index
	 * 
	 * @param index
	 */
	public void playSelectedSong(int index) {
		try {
			if(!startPlayer())
				return;

			WinampController.setPlaylistPosition(index);
			WinampController.play();
		} catch (InvalidHandle e) {
			logException("playSelectedSong(): " + e.getMessage());
		} catch (InvalidParameter e) {
			logException("playSelectedSong(): " + e.getMessage());
		}
	}

	/**
	 * Returns the length of the playlist
	 * 
	 * @return
	 */
	public int getPlayListLength() {
		try {
			if(!startPlayer())
				return 0;

			return WinampController.getPlayListLength();
		} catch (InvalidHandle e) {
			logException("getPlayListLength(): " + e.getMessage());
		}
		return 0;
	}

	/**
	 * Returns the filename for the index specified
	 * 
	 * @param index
	 * @return
	 */
	public String getFileNameInList(int index) {
		try {
			if(!startPlayer())
				return "";

			return WinampController.getFileNameInList(index);
		} catch (InvalidHandle e) {
			logException("getFileNameInList(): " + e.getMessage());
		} catch (InvalidParameter e) {
			logException("getFileNameInList(): " + e.getMessage());
		}
		return "";
	}

	private static void logException(String message) {
		System.out.println("ERROR:: " + message);
	}

	public void exit() {
		try {
			WinampController.getStatus();
			boolean result = MessageDialog.openQuestion(Display.getDefault()
					.getActiveShell(), "Exit Winamp?",
					"Winamp is still running, do you want to exit?");
			if (result){
				WinampController.exit();
			}
		} catch (InvalidHandle e1) {
			logException("exit(): " + e1.getMessage());
		}
	}
}
