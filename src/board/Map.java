package board;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import board.dialogs.Dialog;
import board.dialogs.DialogAction;
import entity.mob.Bee;
import graphics.sprites.Sprite;
import main.Application;
import tiles.Odskok;

public class Map {
	private static int x = 0, y = 0;

	private final static Dimension MAP_SIZE = new Dimension(6, 2);
	private static Clip clip;
	static AudioInputStream audioIn;
	public static int dialogCount = 0;
	
	public final static Board[][] MAP = new Board[MAP_SIZE.width][MAP_SIZE.height];

	public static final Dialog[] DIALOG_LIST = new Dialog[] {
			new Dialog(new DialogAction.first(), Dialog.Sprite.first, Dialog.Sprite.first2
			/* There s no light here but there is a lot of magic" */),
			new Dialog(new DialogAction.onTile(new Odskok(0, 0)), Dialog.Sprite.odskok1, Dialog.Sprite.odskok2) };

	static {		
		init();
		try {
			audioIn = AudioSystem.getAudioInputStream(new File("res/audio/dead.wav"));
			clip = AudioSystem.getClip();
			clip.open(audioIn);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public static void init() {
		for (int y = 0; y < MAP_SIZE.getHeight(); y++) {
			for (int x = 0; x < MAP_SIZE.getWidth(); x++) {
				MAP[x][y] = new Board((int) y + "/" + (int) x + ".txt", Application.player,
						x + y * MAP_SIZE.getWidth());
				
				for(int i = 0; i < MAP[x][y].dialogNum; i++) MAP[x][y].addDialog(DIALOG_LIST[dialogCount++]);
			}
		}
		for (int i = 0; i < 10; i++) {
			MAP[3][0].entities.add(new Bee(new Random().nextInt(35 * Board.tileSize - Board.tileSize),
					new Random().nextInt(28 * Board.tileSize - Board.tileSize * 2), 16, 32, Sprite.bee));
		}
	}

	public static Board getCurrentMap() {
		return MAP[x][y];
	}

	public static void refresh() {
		x = y = 0;
	}

	public static void moveToCheckPoint() {
		Application.board.inactive();
		playSound();
		refresh();
		Application.board = MAP[0][0];

		Application.player.x = Board.tileSize * 5;
		Application.player.y = Board.tileSize * (28 - 5);
		Application.board.active();
	}

	private static void playSound() {
		clip.setFramePosition(0);
		clip.start();
	}

	public static Board moveMapRight() {
		changed();

		if (x != MAP_SIZE.width - 1) {
			x++;
			Application.player.x = 0;
		}

		MAP[x][y].active();

		return MAP[x][y];
	}

	public static Board moveMapLeft() {
		changed();

		if (x > -1) {
			x--;
			Application.player.pushToEnd();
		}
		MAP[x][y].active();

		return MAP[x][y];
	}

	public static Board moveMapBelow(int x) {
		changed();
		if (y < MAP_SIZE.height - 1) {
			y++;
			Map.x = x;
		}
		MAP[x][y].active();

		return MAP[x][y];
	}

	public static Board getMapRight() {
		return MAP[x + 1][y];
	}

	public static Board getMapLeft() {
		return MAP[x - 1][y];
	}

	private static void changed() {
		MAP[x][y].inactive();
	}

	public static boolean hasLeft() {
		if (x == 0)
			return false;
		return true;
	}

	public static boolean hasRight() {
		if (x == MAP_SIZE.width - 1)
			return false;
		return true;
	}
}
