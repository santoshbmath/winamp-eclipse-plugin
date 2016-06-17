package player.plugin.views;

import java.io.IOException;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import player.MediaPlayer;
import player.MediaPlayerFactory;
import player.Track;

import de.vdheide.mp3.FrameDamagedException;
import de.vdheide.mp3.ID3v2DecompressionException;
import de.vdheide.mp3.ID3v2IllegalVersionException;
import de.vdheide.mp3.ID3v2WrongCRCException;
import de.vdheide.mp3.MP3File;
import de.vdheide.mp3.NoMP3FrameException;


public class PlaylistContentProvider implements IStructuredContentProvider {
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}

	public void dispose() {
	}

	public Object[] getElements(Object parent) {
		Track tracks[] = new Track[1];
		MediaPlayer player = MediaPlayerFactory.getMediaPlayer();
		
		int total = player.getPlayListLength();
		tracks = new Track[total];

		for (int i = 0; i < total; i++) {
			Track newTrack = new Track();
			try {
				newTrack.setNo(i);

				MP3File mp3File = new MP3File(player.getFileNameInList(i));
				String title = mp3File.getTitle().getTextContent();

				if (title == null) {
					newTrack.setTitle(mp3File.getName());
				} else
					newTrack.setTitle(mp3File.getTitle().getTextContent());

				long time = mp3File.getLength();
				newTrack.setDuration(time / 60 + ":" + time % 60);

				String album = mp3File.getAlbum().getTextContent();
				newTrack.setAlbum(album == null ? "" : album);

				String artist = mp3File.getArtist().getTextContent();
				newTrack.setArtist(artist == null ? "" : artist);

				newTrack.setLocation(mp3File.getParent());
				newTrack.setYear(mp3File.getYear().getTextContent());
			} catch (FrameDamagedException e) {
				e.printStackTrace();
			} catch (ID3v2WrongCRCException e) {
				e.printStackTrace();
			} catch (ID3v2DecompressionException e) {
				e.printStackTrace();
			} catch (ID3v2IllegalVersionException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NoMP3FrameException e) {
				e.printStackTrace();
			}
			tracks[i] = newTrack;
		}
		return tracks;
	}
}