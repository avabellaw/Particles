package entity;

import board.Board;

public class TestParticleConst extends TestParticle {

	public TestParticleConst(int x, int y, int width, int height, double weight, Board board) {
		super(x, y, width, height, weight, board);
		// TODO Auto-generated constructor stub
		yDir = board.GRAVITY * weight;
	}

	protected void gravity() {
		if (yDir <= maxSpeed) {
			upsCount = 0;
		} else yDir = maxSpeed;

		if(yDir == 0) yDir = 1;
		
		upsCount++;
	}

}
