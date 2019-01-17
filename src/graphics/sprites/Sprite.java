package graphics.sprites;

import java.awt.Color;
import java.awt.image.BufferedImage;

import board.Board;
import graphics.Display;
import main.menu.Button;

public class Sprite {

	private int width, height, colour;
	private int[] pixels;

	public static Sprite helpButton = new Sprite(200, 50, 20, 20, Spritesheet.MENU_ITEMS);

	public static Sprite startButton = new Sprite(0, 0, Button.Start.dimensions.width, Button.Start.dimensions.height, Spritesheet.MENU_ITEMS);
	public static Sprite builderButton = new Sprite(0, Button.Start.dimensions.height, Button.Builder.dimensions.width, Button.Builder.dimensions.height, Spritesheet.MENU_ITEMS);

	public static Sprite musicOn = new Sprite(startButton.positionRight(), 0, 32, 32, Spritesheet.MENU_ITEMS);
	public static Sprite musicOff = new Sprite(musicOn.positionRight(), 0, 32, 32, Spritesheet.MENU_ITEMS);

	public static Sprite background1 = new Sprite(0, 0, 35 * Board.tileSize * 2, 28 * Board.tileSize, Spritesheet.BACKGROUNDS);
	public static Sprite background2 = new Sprite(0, 28 * Board.tileSize, 35 * Board.tileSize, 28 * Board.tileSize, Spritesheet.BACKGROUNDS);
	public static Sprite background1_ = new Sprite(background2.positionRight(), background1.positionUnder(), 35 * Board.tileSize, 28 * Board.tileSize, Spritesheet.BACKGROUNDS);
	public static Sprite defaultBackground = new Sprite(35 * Board.tileSize, 28 * Board.tileSize, Color.green.getRGB());
	
	public static Sprite resumeButton = new Sprite(0, builderButton.positionUnder(), 350, 60, Spritesheet.MENU_ITEMS);
	public static Sprite mainMenuButton = new Sprite(0, resumeButton.positionUnder(), 350, 60, Spritesheet.MENU_ITEMS);

	public static Sprite defaultButton = new Sprite(200, 50, Color.GRAY.getRGB());

	public static Sprite player = new Sprite(0, 0, 16, 32, Spritesheet.ENTITIES);
	public static Sprite bee = new Sprite(0, player.positionUnder(), Board.tileSize, Board.tileSize * 2, Spritesheet.ENTITIES);

	public static Sprite icon = new Sprite(0, player.positionUnder(), Board.tileSize, Board.tileSize, Spritesheet.ENTITIES);

	public static Sprite title = new Sprite(0, 300, 400, 177, Spritesheet.MENU_ITEMS);

	public static Sprite lighting = new Sprite(0, 0, 1120, 896, Spritesheet.LIGHTING);
	public static Sprite noLighting = new Sprite(1120, 896, Color.BLACK.getRGB());

	public static Sprite stairsUp = new Sprite(16, 0, Board.tileSize, Board.tileSize, Spritesheet.ENTITIES);
	public static Sprite stairsDown = new Sprite(32, 0, Board.tileSize, Board.tileSize, Spritesheet.ENTITIES);
	public static Sprite teleTile = new Sprite(16, 16, Board.tileSize, Board.tileSize, Spritesheet.ENTITIES);

	//	public static Sprite odskok = new Sprite(Board.tileSize, Board.tileSize, Color.cyan.getRGB());
	public static Sprite regularTop = new Sprite(stairsDown.positionRight(), 0, Board.tileSize, Board.tileSize, Spritesheet.ENTITIES);
	public static Sprite regular = new Sprite(regularTop.positionRight(), 0, Board.tileSize, Board.tileSize, Spritesheet.ENTITIES);
	public static Sprite regularLeft = new Sprite(regular.positionRight(), 0, Board.tileSize, Board.tileSize, Spritesheet.ENTITIES);
	public static Sprite regularRight = new Sprite(regularLeft.positionRight(), 0, Board.tileSize, Board.tileSize, Spritesheet.ENTITIES);
	public static Sprite regularLUp = new Sprite(regularRight.positionRight(), 0, Board.tileSize, Board.tileSize, Spritesheet.ENTITIES);
	public static Sprite regularRUp = new Sprite(regularLUp.positionRight(), 0, Board.tileSize, Board.tileSize, Spritesheet.ENTITIES);

	public static Sprite testParticle2 = new Sprite(10, 10, Color.green.getRGB());
	public static Sprite testParticle = new Sprite(10, 10, Color.blue.getRGB());

	public Sprite(int width, int height, int colour) {
		this.width = width;
		this.height = height;
		this.colour = colour;

		this.pixels = new int[this.width * this.height];

		for (int i = 0; i < pixels.length; i++)
			pixels[i] = this.colour;
	}

	public int positionX, positionY;

	public Sprite(int xOff, int yOff, int width, int height, Spritesheet spritesheet) {
		this.width = width;
		this.height = height;
		this.positionX = xOff;
		this.positionY = yOff;
		int sheetWidth = spritesheet.getWidth(), sheetPixels[] = spritesheet.getPixels();

		pixels = new int[width * height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + (y * width)] = sheetPixels[(x + xOff) + ((y + yOff) * sheetWidth)];
			}
		}
	}

	public int positionUnder() {
		return positionY + height;
	}

	public int positionRight() {
		return positionX + width;
	}

	public int positionLeft() {
		return positionX - width;
	}

	public int[] getSprite() {
		return pixels;
	}

	public BufferedImage getImg() {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < pixels.length; i++) {
			if (Display.isVoid(pixels[i])) pixels[i] = new Color(0, 0, 0, 255).getAlpha();
		}
		img.setRGB(0, 0, width, height, pixels, 0, width);
		return img;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}

/*public Sprite(Sprite sprite, int width, int height, int colourToRmv) {
this.width = width;
this.height = height;

BufferedImage orginal = new BufferedImage(sprite.width, sprite.height, BufferedImage.TYPE_INT_RGB);
int[] originalImagePixels = ((DataBufferInt)orginal.getRaster().getDataBuffer()).getData();
for(int i = 0; i < originalImagePixels.length; i++) {
	originalImagePixels[i] = sprite.getSprite()[i];
	if(colourToRmv != -1 && originalImagePixels[i] == colourToRmv) originalImagePixels[i] = new Color(186, 255, 245).getRGB();
}
//orginal.setRGB(0, 0, sprite.width, sprite.height, originalImagePixels, 0, sprite.width);



Image shrunk = orginal.getScaledInstance(width, height, Image.SCALE_SMOOTH);
BufferedImage shrunkBI = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

Graphics2D g2 = (Graphics2D) shrunkBI.getGraphics();
g2.drawImage(shrunk, 0, 0, width, height, null);
g2.dispose();
pixels = shrunkBI.getRGB(0, 0, width, height, null, 0, width);
//if (pixels[xi + (yi * width)] == new Color(186, 255, 245).getRGB()) continue;
}
*/
