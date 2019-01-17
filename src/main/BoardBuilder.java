package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import board.Board;
import board.Map;
import graphics.Display;
import io.menu_input.MenuInputs;
import io.res.FileHandler;
import main.dialog.Help;
import main.dialog.builder.LoadBoard;
import main.dialog.builder.Resize;
import main.dialog.builder.Selector;
import main.dialog.builder.UserAction;
import main.menu.Items;
import tiles.TileCode;

public class BoardBuilder extends Component {

	private boolean resizeOpen = false;
	private boolean skip = false;

	protected MenuInputs input;

	private static List<Items> items = new ArrayList<Items>();

	private int width, height, overlayPixels[];

	JMenuBar bar;

	public Selector s;

	public final String TITLE = "B O A R D  B U I L D E R  !!";

	public static Board board = new Board(Map.MAP[0][0].getFileName());

	private boolean saved;

	public BoardBuilder(Display display, Main main) {
		super(display, main);
		//NOTHING PAST HERE
	}

	@Override
	public void reinit() {
		saved = true;
		display.setTitle(TITLE + "  [" + (saved == true ? "SAVED" : "NOT SAVED") + "]");
		input.isActive(true);
	}

	@Override
	protected void init() {
		input = new MenuInputs(this);
		this.width = board.getScreenWidth();
		this.height = board.getScreenHeight();

		overlayPixels = new int[width * height];

		saved = true;
		display.setTitle(TITLE + "  [" + (saved == true ? "SAVED" : "NOT SAVED") + "]");
		bar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem help = new JMenuItem("Help");
		JMenuItem selectorItem = new JMenuItem("Selector");

		JMenuItem save = new JMenuItem("Save");
		JMenuItem saveAs = new JMenuItem("Save as...");
		JMenuItem load = new JMenuItem("Load");
		JMenuItem clear = new JMenuItem("Clear");
		JMenuItem resize = new JMenuItem("Resize");

		resize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resize();
			}
		});

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
				saved = true;
			}
		});
		saveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save(JOptionPane.showInputDialog(display, "Filename: "));
				saved = true;
			}
		});

		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < board.boardValues.size(); i++)
					board.boardValues.set(i, 0);
				board.refresh();
			}
		});
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				load();
			}
		});
		file.add(save);
		file.add(saveAs);
		file.add(load);
		file.add(clear);
		file.addSeparator();
		file.add(resize);

		help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Help(FileHandler.boardBuilderHelp);
			}
		});

		selectorItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		bar.add(file);
		bar.add(selectorItem);
		bar.add(help);
		display.frame.setJMenuBar(bar);

		s = new Selector();

		Main.display.addMouseListener(input);
		Main.display.addMouseMotionListener(input);
		Main.display.addKeyListener(input);
		input.isActive(true);
		display.frame.pack();
	}

	private void resize() {
		if (resizeOpen) return;
		resizeOpen = true;
		final JDialog frame = new JDialog();
		frame.setAlwaysOnTop(true);
		frame.setTitle("Resize board");
		frame.setAlwaysOnTop(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				resizeOpen = false;
				frame.dispose();
			}
		});
		frame.setResizable(false);
		frame.add(new Resize(this, board.width, board.height));
		frame.pack();
		frame.setVisible(true);
	}

	public void resize(int bWidth, int bHeight) {
		skip = true;

		int boardCopy[] = new int[bWidth * bHeight];
		for (int i = 0; i < boardCopy.length; i++)
			boardCopy[i] = 0;

		int width = this.width / Board.tileSize;
		int height = this.height / Board.tileSize;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				try {
					boardCopy[x + (y * bWidth)] = board.boardValues.get(x + (y * width));
				} catch (Exception e) {
				}
			}
		}

		board.boardValues.clear();
		for (int i = 0; i < boardCopy.length; i++)
			board.boardValues.add(boardCopy[i]);

		this.width = bWidth * Board.tileSize;
		this.height = bHeight * Board.tileSize;
		display.resize(this.width, this.height);

		board.setSize(bWidth, bHeight);
		board.refresh();

		skip = false;
	}

	public void close() {
		display.frame.setJMenuBar(null);
		Main.startMenu.reinit();
		input.isActive(false);
	}

	private void save() {
		board.save(width / Board.tileSize);
		display.setTitle(TITLE + " [SAVED]");
	}

	public void save(String filename) {
		List<Integer> prevBoardValues = board.boardValues;
		board = new Board(filename + ".txt");
		board.boardValues = prevBoardValues;
		board.refresh();
		board.save(width / Board.tileSize);
		display.setTitle(TITLE + " [SAVED]");
	}

	private void load() {
		JDialog frame = new JDialog();
		frame.setLocationRelativeTo(null);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.add(new LoadBoard(frame));
		frame.pack();
		frame.setVisible(true);
	}

	public void keyClicked(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_1:
			Selector.block = TileCode.REGULAR_TOP;
			break;
		case KeyEvent.VK_2:
			Selector.block = TileCode.ODSKOK;
			break;
		case KeyEvent.VK_3:
			Selector.block = TileCode.STAIRS_UP;
			break;
		case KeyEvent.VK_5:
			Selector.block = TileCode.TELE_TILE;
			break;
		case KeyEvent.VK_6:
			Selector.block = TileCode.REGULAR;
			break;
		case KeyEvent.VK_7:
			Selector.block = TileCode.RED;
			break;
		case KeyEvent.VK_8:
			Selector.block = TileCode.REGULARL;
			break;
		case KeyEvent.VK_9:
			Selector.block = TileCode.REGULARR;
			break;
		case KeyEvent.VK_0:
			Selector.block = TileCode.VOID;
			break;
		case KeyEvent.VK_Z:
			if ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0) UserAction.undoAction();
			break;
		case KeyEvent.VK_Y:
			if ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0) UserAction.redoAction();
			break;
		case KeyEvent.VK_S:
			save();
			saved = true;
			break;
		case KeyEvent.VK_L:
			break;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = (e.getX() / Board.tileSize) * Board.tileSize;
		int y = (e.getY() / Board.tileSize) * Board.tileSize;

		overlayPixels = new int[width * height];
		for (int yi = 0; yi < Board.tileSize; yi++) {
			for (int xi = 0; xi < Board.tileSize; xi++) {
				if ((yi == 0) || (xi == 0)) overlayPixels[(x + xi) + (y + yi) * width] = new Color(255, 0, 0).getRGB();
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (e.isShiftDown()) {
			JOptionPane.showMessageDialog(display, "x: " + e.getX() / Board.tileSize + ", y: " + e.getY() / Board.tileSize);
			return;
		}

		int x = (int) (e.getX() / display.getScale());
		int y = (int) (e.getY() / display.getScale());
		saved = false;
		display.setTitle(TITLE + "  [NOT SAVED]");

		int index = ((x / Board.tileSize) + ((y / Board.tileSize) * board.width));
		int toBeReplaced = board.boardValues.get(index);

		if (Selector.block != toBeReplaced) UserAction.addTileAction(Selector.block, toBeReplaced, index, this);

		setTileDown(index, Selector.block);
	}

	public void setTileDown(int index, int tileCode) {
		board.boardValues.set(index, tileCode);

		board.refresh();
	}

	@Override
	public void render() {
		if (skip) return;

		//Displays the grid
		for (int y = 0; y < display.pHeight; y++) {
			for (int x = 0; x < display.pWidth; x++) {
				if (!(y % Board.tileSize == 0) && !(x % Board.tileSize == 0)) display.pixels[x + y * display.pWidth] = 0xfffff9ff;
			}
		}

		//displays the board
		board.render(display);

		for (int i = 0; i < items.size(); i++)
			items.get(i).render(display);

		for (int y = 0; y < display.pHeight; y++) {
			for (int x = 0; x < display.pWidth; x++) {
				if (overlayPixels[x + y * display.pWidth] != 0) display.pixels[x + y * display.pWidth] = overlayPixels[x + y * display.pWidth];
			}
		}
	}

	/*	@Override
		public void closeComponent() {
			if (!saved) {
				String[] options = new String[] { "Okay", "Cancel" };
				int answer = JOptionPane.showOptionDialog(display.frame, "Any unsaved work will be lost, continue anyway?", "Unsaved work", JOptionPane.INFORMATION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
				if (answer != 0) return;
			}
			s.close();
	
			isRunning = false;
			Main.current = state.START_MENU;
		}*/

	@Override
	protected void update() {

	}

	public Dimension getBoardSize() {
		return new Dimension(width / Board.tileSize, height / Board.tileSize);
	}

	@Override
	public void inputIsActive(boolean active) {
		input.isActive(active);		
	}

}
