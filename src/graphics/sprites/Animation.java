package graphics.sprites;

import board.Board;

public class Animation {

	/**
	 * Create the animation here
	 * Add the animation to the animation update at the bottom
	 * In the tile that u have animated, when it is active/inactive set renderanimation to true/false
	 */
	public static Animation odskok = new Animation(Animation.getAnimation(Sprite.stairsDown.positionRight(), Sprite.regularTop.positionUnder(), Board.tileSize, Board.tileSize, 6, Spritesheet.ENTITIES), 5);
	public static Animation red = new Animation(Animation.getAnimation(Sprite.stairsDown.positionRight(), Sprite.teleTile.positionUnder(), Board.tileSize, Board.tileSize, 8, Spritesheet.ENTITIES), 2);

	private int count = 0, updateCounter = 0, countModifier = 1;;
	private Sprite sprites[];
	private int frameSpeed = 60;

	private boolean renderAni = false;;

	public Animation(Sprite[] sprites) {
		this.sprites = sprites;
	}

	public Animation(Sprite[] sprites, int frameSpeed) {
		this.sprites = sprites;

		this.frameSpeed = frameSpeed;
	}

	public Sprite getFrame() {
		return sprites[count];
	}

	public void update() {
		if (!renderAni) return;
		if (count >= sprites.length - 1) countModifier = -1;
		if (count <= 0) countModifier = 1;

		if (updateCounter >= frameSpeed) {
			count += countModifier;
			updateCounter = 0;
		}
		updateCounter++;
	}

	public static Sprite[] getAnimation(int xOff, int yOff, int width, int height, int xAmount, Spritesheet spritesheet) {
		Sprite[] sprites = new Sprite[xAmount];

		for (int i = 0; i < xAmount; i++) {
			sprites[i] = new Sprite(xOff + i * width, yOff, width, height, spritesheet);
		}

		return sprites;
	}

	public static void aniUpdate() {
		odskok.update();
		red.update();
	}

	public void setRenderAnimation(boolean renderAni) {
		this.renderAni = renderAni;
	}

	/*
		public static class MutableBoolean {
			public boolean renderAni = false;
	
			public void setBoolean(boolean renderAni) {
				this.renderAni = renderAni;
			}
	
			public boolean getBoolean() {
				return renderAni;
			}
		}*/

}
