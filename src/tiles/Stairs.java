package tiles;

import board.Board;
import graphics.sprites.Sprite;

public class Stairs extends Tile {

	public Stairs(int x, int y) {
		super(x, y, Board.tileSize, Board.tileSize, Sprite.stairsUp);
		hardness = 1;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
}
