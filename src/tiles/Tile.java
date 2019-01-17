package tiles;

import java.awt.Color;

import board.Board;
import entity.Entity;
import graphics.sprites.Animation;
import graphics.sprites.Sprite;

public abstract class Tile extends Entity {

	protected int[] pixels;
	protected static Animation spriteAni;
	public boolean isAnimated = false;
	
	public static Sprite listOfSprites[] = new Sprite[] { Sprite.regularTop, Animation.odskok.getFrame(), Sprite.stairsUp, Sprite.stairsDown, Sprite.teleTile, Sprite.regular , new Sprite(Board.tileSize, Board.tileSize, Color.RED.getRGB()), Sprite.regularLeft, Sprite.regularRight, Sprite.regularLUp, Sprite.regularRUp};


	public Tile(int x, int y, int width, int height, Sprite sprite) {
		super(x, y, width, height, sprite);


		pixels = new int[width * height];

		pixels = sprite.getSprite();
	}

	public void update() {
		
	};
}
