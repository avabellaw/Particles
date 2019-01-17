package board;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import board.dialogs.Dialog;
import entity.Background;
import entity.Entity;
import entity.player.Player;
import graphics.Display;
import graphics.sprites.AniCounter;
import graphics.sprites.Animation;
import graphics.sprites.Sprite;
import io.res.BoardFile;
import main.Application;
import main.Main;
import tiles.Odskok;
import tiles.RedVoid;
import tiles.Regular;
import tiles.Stairs;
import tiles.TeleTile;
import tiles.Tile;
import tiles.TileCode;

public class Board {
	public int width, height;
	public double GRAVITY = 0.4;
	public List<Entity> entities = new ArrayList<Entity>();
	public List<Integer> boardValues = new ArrayList<Integer>();
	private BoardFile boardFile;
	private Background[] background;
	public int dialogNum = 0;
	
	private AniCounter counter;
	public boolean isPaused = false, backgrndIsAni = true;

	public Dialog current;

	public final double ID;
	private boolean advance = false;

	private List<Dialog> dialogs = new ArrayList<Dialog>();

	int testCounter = 0; //////////////////////////////////////////////////////

	public static final int tileSize = 16;

	public Board(String board, Player player, double ID) {
		this.ID = ID;
		init(board);
		player.x = Board.tileSize * 5;
		player.y = Board.tileSize * (28 - 3);

		entities.add(player);
	}

	public Board(String board) {
		this.ID = -1;
		init(board);
		backgrndIsAni = false;
	}

	private void init(String board) {
		boardFile = new BoardFile("res/map/" + board);

		width = boardFile.getDimension().width;
		height = boardFile.getDimension().height;
		this.boardValues = boardFile.getBoard();

		dialogNum = boardFile.dialogNum;
		
		if (boardFile.boardBackground != null) {
			setBackground(boardFile.boardBackground.firstMoving, boardFile.boardBackground.sprite);
		} else {
			System.err.println("No background tag");
			setBackground(false, Sprite.defaultBackground);
		}

		refresh();
	}

	public void save(int width) {
		boardFile.save(boardValues, width);
	}

	private void clearDisplay() {
		entities = new ArrayList<Entity>();
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void refresh() {
		clearDisplay();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

				if (boardValues.get(x + (y * width)) == 0)
					continue;

				switch (boardValues.get(x + (y * width))) {
				case TileCode.REGULAR_TOP:
					addTileToBoard(new Regular(x, y));
					break;
				case TileCode.ODSKOK:
					addTileToBoard(new Odskok(x, y));
					break;
				case TileCode.STAIRS_UP:
					addTileToBoard(new Stairs(x, y));
					break;
				case TileCode.TELE_TILE:
					TeleTile teleTile = new TeleTile(x, y);
					addTileToBoard(teleTile);
					break;
				case TileCode.REGULAR:
					addTileToBoard(new Regular(x, y, Sprite.regular));
					break;
				case TileCode.RED:
					addTileToBoard(new RedVoid(x, y));
					break;
				case TileCode.REGULARL:
					addTileToBoard(new Regular.Left(x, y));
					break;
				case TileCode.REGULARR:
					addTileToBoard(new Regular.Right(x, y));
					break;
				case TileCode.REGULARLUP:
					addTileToBoard(new Regular.LUp(x, y));
					break;
				case TileCode.REGULARRUP:
					addTileToBoard(new Regular.RUp(x, y));
					break;
				}
			}
		}
	}

	public void setBackground(boolean firstMoving, Sprite... sprites) {
		if (sprites.length == 1) {
			Sprite sprite = sprites[0];
			background = new Background[1];
			background[0] = new Background(sprite.getSprite());
			counter = new AniCounter(sprite.getWidth(), 1);
			backgrndIsAni = firstMoving;
			return;
		}

		background = new Background[sprites.length];
		int count = 0;
		for (Sprite sprite : sprites) {
			background[count] = new Background(sprite.getSprite());
			count++;
		}
		counter = new AniCounter(sprites[0].getWidth(), 1);
		backgrndIsAni = firstMoving;
	}

	private void addTileToBoard(Tile tile) {
		tile.x = tile.x * tile.getWidth();
		tile.y = tile.y * tile.getHeight();
		entities.add(tile);
	}

	public void render(Display d) {
		if (background != null || backgrndIsAni)
			renderBackground(d);

		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).draw(d);
		}
	}

	public void renderMasks(Display d) {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).renderMask(d);
		}
	}

	public void renderBackground(Display d) {
		for (int yi = 0; yi < getScreenHeight(); yi++) {
			for (int xi = 0; xi < getScreenWidth(); xi++) {

				if (backgrndIsAni) {
					if (background[0].pixels[xi + (yi * getScreenWidth())] == new Color(186, 255, 245).getRGB())
						continue;
					d.pixels[(xi) + ((yi) * (d.pWidth))] = background[0].pixels[((xi + counter.getCount())
							% (background[0].pixels.length / Main.display.pHeight))
							+ (yi * (background[0].pixels.length / Main.display.pHeight))];

					if (background.length > 1) {
						if (!Display.isVoid(background[1].pixels[xi + (yi * getScreenWidth())])) {
							;
							d.pixels[(xi) + ((yi) * (d.pWidth))] = background[1].pixels[(xi) + (yi * d.pWidth)];
						}
					}
				} else {
					if (background[0].pixels[xi + (yi * getScreenWidth())] == new Color(186, 255, 245).getRGB()) {
						continue;
					}
					d.pixels[(xi) + ((yi) * (d.pWidth))] = background[0].pixels[xi + (yi * d.pWidth)];
				}
			}
		}
	}

	public void update() {
		if (backgrndIsAni)
			counter.updateCount();

		if (Main.showPausedMenu) {
			return;
		}
		if (current != null) {
			if (advance) {
				current.advance();
				advance = false;
				if (!current.hasNext()) {
					current = null;
				}
			}
			return;
		}

		for (int i = 0; i < dialogs.size(); i++) {
			if (!dialogs.get(i).isTriggered())
				continue;
			current = dialogs.get(i);
			dialogs.remove(i);
		}

		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
			/*
			 * if (i + 1 < entities.size() && entities.get(i).x == entities.get(i + 1).x &&
			 * entities.get(i).y == entities.get(i + 1).y) { entities.get(i).x += 10; }
			 */
		}

		Animation.aniUpdate();
	}

	public void advanceDialog() {
		advance = true;
	}

	public void inactive() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).inactive();
		}
	}

	public void active() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).active();
		}
	}

	public int getScreenWidth() {
		return width * tileSize;
	}

	public int getScreenHeight() {
		return height * tileSize;
	}

	public String getFileName() {
		return boardFile.getFileName();
	}

	public void addDialog(Dialog dialog) {
		this.dialogs.add(dialog);
	}

	public void addDialog(Dialog[] dialog) {
		// make list or array to add to dialogs
	}

	public boolean isShowingDialog() {
		if (current != null)
			return true;
		else
			return false;
	}

	public void resume(boolean affectPausemenu) {
		active();
		isPaused = false;

		if (!affectPausemenu)
			return;
		Main.application.reinit();
		Application.pauseMenu.close();
	}

	public void pause(boolean affectPausemenu) {
		inactive();
		isPaused = true;
		if (!affectPausemenu)
			return;
		Main.application.close();
		Application.pauseMenu.reinit();
	}

}
