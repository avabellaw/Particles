package tiles;

import java.awt.Color;

import board.Board;
import graphics.Display;
import graphics.sprites.Animation;
import graphics.sprites.Sprite;

public class RedVoid extends Tile {

	public RedVoid(int x, int y) {
		super(x, y, Board.tileSize, Board.tileSize, new Sprite(Board.tileSize, Board.tileSize, Color.red.getRGB()));
		makeVoid();
		
		isAnimated = true;
	}
	
	@Override
	public void draw(Display d) {
		for (int yi = 0; yi < height; yi++) {
			for (int xi = 0; xi < width; xi++) {
				if (Display.isVoid(pixels[xi + yi * width])) continue;
				try {
					int rx = x;
					if (rx < 0) rx = 0;
					d.pixels[(rx + xi) + ((y + yi) * (d.pWidth))] = Animation.red.getFrame().getSprite()[xi + (yi * width)];
				} catch (Exception e) {
					System.err.println("Array index out of bounds when trying to render");
				}
			}
		}
	}
	
	@Override
	public void active() {
		if (isAnimated) Animation.red.setRenderAnimation(true);
	}

	@Override
	public void inactive() {
		if (isAnimated) Animation.red.setRenderAnimation(false);
	}


}
