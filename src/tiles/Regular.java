package tiles;

import board.Board;
import graphics.sprites.Sprite;

public class Regular extends Tile {

	public Regular(int x, int y) {
		super(x, y, Board.tileSize, Board.tileSize, Sprite.regularTop);
		hardness = 1.1;
	}

	public Regular(int x, int y, Sprite sprite) {
		super(x, y, Board.tileSize, Board.tileSize, sprite);
		hardness = 1.1;
	}

	public static class Left extends Regular {

		public Left(int x, int y) {
			super(x, y, Sprite.regularLeft);
		}

	}

	public static class Right extends Regular {

		public Right(int x, int y) {
			super(x, y, Sprite.regularRight);
		}

	}

	public static class LUp extends Regular {

		public LUp(int x, int y) {
			super(x, y, Sprite.regularLUp);
		}

	}

	public static class RUp extends Regular {

		public RUp(int x, int y) {
			super(x, y, Sprite.regularRUp);
		}

	}

}
