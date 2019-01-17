package tiles;

import board.Board;
import graphics.sprites.Sprite;
import main.Application;

public class TeleTile extends Tile {

	int xPull = 0, yPull = 0;

	public TeleTile(int x, int y) {
		super(x, y, Board.tileSize, Board.tileSize, Sprite.teleTile);

		makeVoid();
	}

	//method to get coords of point to teleto or saved previous;

	@Override
	public void update() {
		xPull = 0;
		yPull = 0;

		int distX = Application.player.x - x;
		if (distX < 0) distX *= -1;
		int distY = Application.player.y - y;
		if (distY < 0) distY *= -1;

		if (Application.player.x != x) xPull = Application.player.x < x ? 1 : -1;
		if (Application.player.y != y) yPull = Application.player.y > y ? -1 : 1;

		Application.board.GRAVITY = 0;
		
		System.out.println("distx" + distX + ", disty " + distY);
		
		if(distY < distX ) xPull *= distX / (distY == 0 ? 1 : distY);
		
		if(xPull > 5) xPull = 5;

		Application.player.pull(xPull, yPull);
	}

}
