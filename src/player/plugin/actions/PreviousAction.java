package player.plugin.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import player.MediaPlayer;
import player.MediaPlayerFactory;

public class PreviousAction implements IWorkbenchWindowActionDelegate {

	@Override
	public void run(IAction action) {
		MediaPlayer player = MediaPlayerFactory.getMediaPlayer();
		player.playPrev();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void init(IWorkbenchWindow window) {
	}
}
