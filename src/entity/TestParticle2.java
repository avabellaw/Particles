package entity;

import java.awt.event.KeyEvent;

import board.Board;
import graphics.sprites.Sprite;

public class TestParticle2 extends Entity {

	public TestParticle2(int x, int y, int width, int height, double weight, Board board) {
		super(x, y, width, height, Sprite.testParticle2);

		this.weight = weight;

		hardness = 1.6;
		yDir = 0;
		maxSpeed = 100;
	}

	public void KeyPressed(KeyEvent e) {
		x += 1;
	}

	public void update() {
		gravity();
		updateMovement();
	}

}
