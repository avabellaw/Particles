package entity.mob;

import graphics.sprites.Sprite;
import io.res.Music;
import main.Application;

public class Bee extends Mob {

	public Bee(int x, int y, int width, int height, Sprite sprite) {
		super(x, y, width, height, sprite);

		Music.bee.start();
		Music.bee.pause();
		Music.bee.setVolume(-8);
	}

	@Override
	public void extendedMobUpdate() {
		if (Application.player.y < y + 60 && y < Application.player.y + Application.player.getHeight()) yDir -= 1;
	}

	@Override
	public void inactive() {
		Music.bee.pause();
	}

	@Override
	public void active() {
		Music.bee.resume();
	}
}
