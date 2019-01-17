package entity;

import board.Board;
import graphics.sprites.Sprite;

public class TestParticle extends Entity {

	private boolean grabbed = false;

	public TestParticle(int x, int y, int width, int height, double weight, Board board) {
		super(x, y, width, height, Sprite.testParticle);

		this.weight = weight;

		hardness = 1.1;
		yDir = 0;
		maxSpeed = 100;
	}

	public void update() {
		if (grabbed) {
			//x = Component.inputs.mouseX;
			//int potentialY = Component.inputs.mouseY;
			/*if(isColliding() && potentialY + height > getEntityunderneath().y) {
				return;
			}*/
			// y = potentialY;
			return;
		}

		gravity();
		updateMovement();
	}

	public void clickedOn() {
		//grabbed = !grabbed;
		grabbed = true;
	}

	public void letGo() {
		grabbed = false;
	}

}
