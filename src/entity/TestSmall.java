package entity;

import board.Board;

public class TestSmall extends Entity {

	public TestSmall(int x, int y, Board board) {
		super(x, y, 2, 2, null);

		this.weight = 1.4;
		maxSpeed = 100;
	}

	@Override
	public void update() {
		gravity();
		updateMovement();
	}

}
