package player.plugin.views;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import player.Activator;
import player.Constants;
import player.Track;


class PlaylistLabelProvider extends LabelProvider implements
		ITableLabelProvider {
	public String getColumnText(Object obj, int index) {
		String columnText = "";
		Track track = (Track) obj;
		if (index == 1) {
			columnText = track.getTitle();
		} else if (index == 2) {
			columnText = track.getDuration();
		} else if (index == 3) {
			columnText = track.getAlbum();
		} else if (index == 4) {
			columnText = track.getArtist();
		} else if (index == 5) {
			columnText = track.getYear();
		} else if (index == 6) {
			columnText = track.getLocation();
		}

		return columnText;
	}

	public Image getColumnImage(Object obj, int index) {
		if (index == 1) {
			return getImage(obj);
		}
		return null;
	}

	public Image getImage(Object obj) {
		return Activator.getDefault().getImageDescriptor(Constants.ICON_WINAMP)
				.createImage();
	}
}