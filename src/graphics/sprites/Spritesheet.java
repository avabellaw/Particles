package graphics.sprites;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import board.Board;

public class Spritesheet {

	private int width, height, pixels[];

	private final static int BLOCK_WIDTH = 200, BLOCK_HEIGHT = 50;

	public static final Spritesheet MENU_ITEMS = new Spritesheet("res/Spritesheet_menu.png", 600, 600);
	public static final Spritesheet ENTITIES = new Spritesheet("res/Spritesheet_entity.png", 600, 600);
	public static final Spritesheet BACKGROUNDS = new Spritesheet("res/Spritesheet_background.png", 1120, 896);
	public static final Spritesheet LIGHTING = new Spritesheet("res/Lighting.png", 1120, 896);
	
	public static final Spritesheet DIALOG_FLOOR1_1 = new Spritesheet("res/dialog/floor1/1.png", 550, 200);
	public static final Spritesheet DIALOG_FLOOR1_2 = new Spritesheet("res/dialog/floor1/2.png", 550, 200);
	
	public static final Spritesheet PAUSED = new Spritesheet("res/dialog/paused/pause_background.png", 35 * Board.tileSize, 28 * Board.tileSize);

	private BufferedImage img;

	public Spritesheet(String pathname, int width, int height) {
		File spritesheet = new File(pathname);
		try {
			img = ImageIO.read(spritesheet);
			pixels = img.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			System.out.println("Spritesheet file issue, pathname: " + pathname);
		}

		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[] getPixels() {
		return pixels;
	}

	public static Dimension getBlockDimensions() {
		return new Dimension(BLOCK_WIDTH, BLOCK_HEIGHT);
	}

	public BufferedImage getImg() {
		return img;
	}
}
