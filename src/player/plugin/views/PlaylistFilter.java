package player.plugin.views;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import player.Track;

public class PlaylistFilter extends ViewerFilter {

	private String searchString;

	public void setSearchText(String s) {
		this.searchString = s.toLowerCase(); // If you use  matches, then change this to "*."+s+".*"
	}

	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (searchString == null || searchString.length() == 0) {
			return true;
		}
		Track track = (Track) element;
		
		// We can use matches() instead of contains().
		if (track.getTitle().toLowerCase().contains(searchString)){
			return true;
		}
		if (track.getAlbum().toLowerCase().contains(searchString)){
			return true;
		}
		if(track.getArtist().toLowerCase().contains(searchString)) {
			return true;
		}

		return false;
	}
}
