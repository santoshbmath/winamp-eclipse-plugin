package player.plugin.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import player.MediaPlayer;
import player.MediaPlayerFactory;

public class PauseAction implements IWorkbenchWindowActionDelegate {

	public void run(IAction action) {
		MediaPlayer player = MediaPlayerFactory.getMediaPlayer();
		player.pause();
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
	}
}
