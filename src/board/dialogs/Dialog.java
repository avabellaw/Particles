package board.dialogs;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import board.Board;
import graphics.sprites.Spritesheet;

public class Dialog {

	int count = 0;
	public int dialogNumber, pixels[];

	private final int border = 5;
	private BufferedImage img;
	int width = 35 * Board.tileSize - border * 2, height = 28 * Board.tileSize;
	int dialogHeight = 100, dialogWidth = 550;
	graphics.sprites.Sprite[] sprites;

	private DialogAction action;

	public Dialog(DialogAction action, graphics.sprites.Sprite... sprites) {
		this.sprites = sprites;
		this.action = action;
		dialogNumber = sprites.length;

		img = new BufferedImage(width, dialogHeight, BufferedImage.TYPE_INT_ARGB);

		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

		for (int y = 0; y < dialogHeight; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * (width)] = sprites[0].getSprite()[x + y * dialogWidth];
			}
		}
	}
	
	public Dialog(graphics.sprites.Sprite sprite, DialogAction action) {
		this.sprites = new graphics.sprites.Sprite[] {sprite};
		this.action = action;
		dialogNumber = sprites.length;
		
		dialogHeight = 28 * Board.tileSize;
		width = 35 * Board.tileSize;
		dialogWidth = width;
		
		img = new BufferedImage(width, dialogHeight, BufferedImage.TYPE_INT_ARGB);
		
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		
		for (int y = 0; y < dialogHeight; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * (width)] = sprites[0].getSprite()[x + y * dialogWidth];
			}
		}
	}

	public void render(Graphics2D g) {
		g.drawImage(img, border, height - dialogHeight - border, null);
	}

	public void advance() {
		count++;
		if (count == dialogNumber) {
			action.close();
			return;
		}
		//advance image;
		for (int y = 0; y < dialogHeight; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * (width)] = sprites[count].getSprite()[x + y * dialogWidth];
			}
		}
	}

	public boolean isTriggered() {
		return action.isTriggered();
	}

	public boolean hasNext() {
		if (count < dialogNumber) return true;
		else return false;
	}

	public static class Sprite {
		public static graphics.sprites.Sprite first = new graphics.sprites.Sprite(0, 0, 550, 100, Spritesheet.DIALOG_FLOOR1_1);
		public static graphics.sprites.Sprite first2 = new graphics.sprites.Sprite(0, 100, 550, 100, Spritesheet.DIALOG_FLOOR1_1);
		
		public static graphics.sprites.Sprite odskok1 = new graphics.sprites.Sprite(0, 0, 550, 100, Spritesheet.DIALOG_FLOOR1_2);
		public static graphics.sprites.Sprite odskok2 = new graphics.sprites.Sprite(0, 100, 550, 100, Spritesheet.DIALOG_FLOOR1_2);
		
		public static graphics.sprites.Sprite pause_menu = new graphics.sprites.Sprite(0, 0, 35*Board.tileSize, 28 * Board.tileSize, Spritesheet.PAUSED);
	}
}
