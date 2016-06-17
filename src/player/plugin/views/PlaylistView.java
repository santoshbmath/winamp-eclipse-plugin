package player.plugin.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.jface.action.*;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import player.Activator;
import player.Constants;
import player.MediaPlayer;
import player.MediaPlayerFactory;
import player.Track;
import player.WinAmpPlayer;

@SuppressWarnings("deprecation")
public class PlaylistView extends ViewPart {
	public static final String ID = "player.views.PlaylistView";

	private Table table;
	private TableViewer tableViewer;
	private PlaylistFilter filter;
	
	private Action volumeDown;
	private Action volumeUp;
	
	private Action addFile;
	private Action addFolder;
	
	private Action repeat;
	private Action shuffle;
	
	private Action refresh;
	
	private Action forward;
	private Action rewind;
	
	private Action doubleClickAction;

	private final String TITLE_COLUMN = "Title";
	private final String TIME_COLUMN = "Time";
	private final String ALBUM_COLUMN = "Album";
	private final String ARTIST_COLUMN = "Artist";
	private final String YEAR_COLUMN = "Year";
	private final String LOCATION_COLUMN = "File Location";

	private String[] columnNames = new String[] { TITLE_COLUMN, TIME_COLUMN, ALBUM_COLUMN, ARTIST_COLUMN, YEAR_COLUMN, LOCATION_COLUMN };

	public PlaylistView() {
	}
	
	public void createPartControl(Composite parent) {
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);
		
		Label searchLabel = new Label(parent, SWT.NONE);
		searchLabel.setText("Search: (by title, artist, album)");
		
		final Text searchText = new Text(parent, SWT.BORDER | SWT.SEARCH);
		searchText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		searchText.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				filter.setSearchText(searchText.getText());
				tableViewer.refresh();				
			}
			
			public void keyPressed(KeyEvent e) {
			}
		});

		createTable(parent);
		createTableViewer();

		//WinAmpPlayer.startPlayer();
		tableViewer.setContentProvider(new PlaylistContentProvider());
		tableViewer.setLabelProvider(new PlaylistLabelProvider());
		tableViewer.setInput(getViewSite());
		
		filter = new PlaylistFilter();
		tableViewer.addFilter(filter);


		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
		
		setTitle("Playlist contains "+String.valueOf(tableViewer.getTable().getItemCount()) +" songs");
		
		packColumns();
	}
	
	private void packColumns(){
		table.getColumn(1).pack();
		table.getColumn(2).pack();
		table.getColumn(3).pack();
		table.getColumn(4).pack();
		table.getColumn(5).pack();
		table.getColumn(6).pack();
	}
	
	public TableViewer getTableViewer(){
		return tableViewer;
	}

	private void createTableViewer() {		
			tableViewer = new TableViewer(table);
			tableViewer.setUseHashlookup(true);
			tableViewer.setColumnProperties(columnNames);
	}

	private void createTable(Composite parent) {
		int style = SWT.SINGLE | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.FILL;

		GridLayout layout = new GridLayout(1, true);

		table = new Table(parent, style);
		table.setLayout(layout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 3;
		table.setLayoutData(gridData);

		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		TableColumn column = new TableColumn(table, SWT.CENTER, 0);
		column = new TableColumn(table, SWT.LEFT, 1);
		column.setText(TITLE_COLUMN);
		
		column = new TableColumn(table, SWT.LEFT, 2);
		column.setText(TIME_COLUMN);
		
		column = new TableColumn(table, SWT.LEFT, 3);
		column.setText(ALBUM_COLUMN);
		
		column = new TableColumn(table, SWT.LEFT, 4);
		column.setText(ARTIST_COLUMN);
		
		column = new TableColumn(table, SWT.LEFT, 5);
		column.setText(YEAR_COLUMN);
		
		column = new TableColumn(table, SWT.LEFT, 6);
		column.setText(LOCATION_COLUMN);
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		Menu menu = menuMgr.createContextMenu(tableViewer.getControl());
		tableViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, tableViewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(refresh);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		manager.add(repeat);
		manager.add(shuffle);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		manager.add(addFile);
		manager.add(addFolder);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		manager.add(volumeDown);
		manager.add(volumeUp);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		manager.add(rewind);
		manager.add(forward);
	}

	private void makeActions() {
		final MediaPlayer player = MediaPlayerFactory.getMediaPlayer();
		
		refresh = new Action() {
			public void run() {
				tableViewer.refresh();
				packColumns();
				setTitle("Playlist contains "+String.valueOf(tableViewer.getTable().getItemCount()) +" songs");
			}
		};
		refresh.setToolTipText(Constants.TOOLTIP_REFRESH);
		refresh.setImageDescriptor(Activator.getDefault().getImageDescriptor(Constants.ICON_REFRESH));

		
		repeat = new Action() {
			public void run() {
				player.repeat();
			}
		};
		repeat.setToolTipText(Constants.TOOLTIP_REPEAT);
		repeat.setImageDescriptor(Activator.getDefault().getImageDescriptor(Constants.ICON_REPEAT));
		
		shuffle = new Action() {
			public void run() {
				player.shuffle();
			}
		};
		shuffle.setToolTipText(Constants.TOOLTIP_SHUFFLE);
		shuffle.setImageDescriptor(Activator.getDefault().getImageDescriptor(Constants.ICON_SHUFFLE));

		addFile = new Action() {
			public void run() {
				if(!WinAmpPlayer.startPlayer())
					return;
				
				FileDialog dialog = new FileDialog(tableViewer.getControl().getShell(), SWT.NULL);
				String path = dialog.open();
				
				if(path != null && path != ""){
					player.addFileToPlaylist(path);
				
					tableViewer.refresh();
					packColumns();
					setTitle("Playlist contains "+String.valueOf(tableViewer.getTable().getItemCount()) +" songs");
				}
			}
		};
		addFile.setToolTipText(Constants.TOOLTIP_ADD_FILE);
		addFile.setImageDescriptor(Activator.getDefault().getImageDescriptor(Constants.ICON_ADD_FILE));

		addFolder = new Action() {
			public void run() {
				if(!WinAmpPlayer.startPlayer())
					return;
				
				DirectoryDialog dialog = new DirectoryDialog(tableViewer.getControl().getShell());
				String path = dialog.open();
				
				if(path != null && path != ""){
					player.addFolderToPlaylist(path);
				
					tableViewer.refresh();
					packColumns();
					setTitle("Playlist contains "+String.valueOf(tableViewer.getTable().getItemCount()) +" songs");
				}
			}
		};
		addFolder.setToolTipText(Constants.TOOLTIP_ADD_FOLDER);
		addFolder.setImageDescriptor(Activator.getDefault().getImageDescriptor(Constants.ICON_ADD_FOLDER));

		volumeDown = new Action() {
			public void run() {
				player.volumeDown();
			}
		};
		volumeDown.setToolTipText(Constants.TOOLTIP_VOL_DOWN);
		volumeDown.setImageDescriptor(Activator.getDefault().getImageDescriptor(Constants.ICON_VOL_UP));

		volumeUp = new Action() {
			public void run() {
				player.volumeUp();
			}
		};
		volumeUp.setToolTipText(Constants.TOOLTIP_VOL_UP);
		volumeUp.setImageDescriptor(Activator.getDefault().getImageDescriptor(Constants.ICON_VOL_DOWN));
		
		forward = new Action() {
			public void run() {
				player.fastForward();
			}
		};
		forward.setToolTipText(Constants.TOOLTIP_FORWARD);
		forward.setImageDescriptor(Activator.getDefault().getImageDescriptor(Constants.ICON_FORWARD));

		rewind = new Action() {
			public void run() {
				player.slowRewind();
			}
		};
		rewind.setToolTipText(Constants.TOOLTIP_REWIND);
		rewind.setImageDescriptor(Activator.getDefault().getImageDescriptor(Constants.ICON_REWIND));

		
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = tableViewer.getSelection();
				Track obj = (Track)((IStructuredSelection) selection).getFirstElement();
				player.playSelectedSong(obj.getNo());
			}
		};
	}

	private void hookDoubleClickAction() {
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	public void setFocus() {
		tableViewer.getControl().setFocus();
	}
}