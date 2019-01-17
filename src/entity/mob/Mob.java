package entity.mob;

import java.util.Random;

import entity.Entity;
import graphics.sprites.Sprite;
import main.Main;

public abstract class Mob extends Entity {

	private int count = 0;

	public Mob(int x, int y, int width, int height, Sprite sprite) {
		super(x, y, width, height, sprite);
	}

	@Override
	public void update() {
		aiMovement();
		updateMovement();

		extendedMobUpdate();
	}

	public void extendedMobUpdate() {

	}

	private void aiMovement() {
		if (count++ < Main.UPS * 0.2) return;
		yDir = new Random().nextInt(3) - 1;
		xDir = new Random().nextInt(3) - 1;
		count = 0;
	}

	@Override
	protected void updateMovement() {
		//if (yDir == 0) return;

		if (xDir > 0) {
			for (int i = 0; i < xDir; i++) {
				if (!isCollidingRight()) x += 1;
			}
		} else {
			for (int i = 0; i > xDir; i--) {
				if (!isCollidingLeft()) {
					x -= 1;
				}
			}
		}

		if (yDir < 0) {
			if (yDir > -1) yDir = 0;
			for (int i = 0; i < yDir * -1; i++) {
				if (y > 0) y += -1;
			}
		} else {
			for (int i = 0; i < yDir; i++) {
				if (!isCollidingBottom()) {
					y += 1;
				}

			}
		}

	}
}
