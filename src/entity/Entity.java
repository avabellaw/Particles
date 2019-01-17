package entity;

import java.awt.Color;

import board.Map;
import graphics.Display;
import graphics.sprites.Sprite;
import main.Application;
import tiles.Stairs;

public abstract class Entity {

	protected double upsCount, maxSpeed;
	protected double weight;
	protected double yDir = 0, xDir = 0;
	public int width;
	public int height;
	protected int[] pixels;
	public double hardness = 1.1;
	private boolean isVoid = false;

	public int x, y;

	public Entity(int x, int y, int width, int height, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		weight = 0;

		pixels = new int[width * height];

		if (sprite == null) {
			for (int i = 0; i < pixels.length; i++)
				pixels[i] = 0xff12ff;
		} else {
			pixels = sprite.getSprite();
		}
	}

	public void draw(Display d) {
		for (int yi = 0; yi < height; yi++) {
			for (int xi = 0; xi < width; xi++) {
				if (Display.isVoid(pixels[xi + yi * width])) continue;
				try {
					int rx = x;
					if (rx < 0) rx = 0;
					d.pixels[(rx + xi) + ((y + yi) * (d.pWidth))] = pixels[xi + (yi * width)];
				} catch (Exception e) {
					System.err.println("Array index out of bounds when trying to render");
				}
			}
		}
	}

	public abstract void update();

	protected void updateMovement() {
		if (Application.board.isPaused) return;
		//if (yDir == 0) return;
		if (xDir > 0) {
			for (int i = 0; i < xDir; i++) {
				if (getEntityRight() instanceof Stairs || getEntityunderneath() instanceof Stairs) {
					x++;
					y--;
					continue;
				}
				if (!isCollidingRight() && (x + width >= Application.board.getScreenWidth() ? Map.hasRight() : true)) x += 1;
			}
		} else {
			for (int i = 0; i > xDir; i--) {
				if (!isCollidingLeft() && (x == 0 ? Map.hasLeft() : true)) {
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
				} else {
					//yDir = 0;
					if (getEntityunderneath() == null) {
						yDir -= yDir * hardness + Application.board.GRAVITY;
						return;
					}

					yDir -= ((yDir * getEntityunderneath().hardness - (getEntityunderneath().yDir)));
					break;
				}

			}
		}

	}

	protected boolean isCollidingRight() {
		for (int i = 0; i < Application.board.entities.size(); i++) {
			Entity current = Application.board.entities.get(i);
			if (current.equals(this) || current.isVoid()) continue;

			if (y + height == current.y + current.height) {
				if ((x + width + 1 >= current.x && x + width < current.x) || x + width >= Application.board.getScreenWidth()) {
					return true;
				}
			}
		}

		return false;
	}

	protected boolean isCollidingLeft() {
		for (int i = 0; i < Application.board.entities.size(); i++) {
			Entity current = Application.board.entities.get(i);
			if (current.equals(this) || current.isVoid()) continue;

			if (y + height == current.y + current.height) {
				if ((x <= current.x + current.width && x + width > current.x) || x < 0) {
					return true;
				}
			}
		}

		return false;
	}

	protected boolean isCollidingBottom() {
		if (y + height >= Application.board.getScreenHeight() - 1) return true;
		for (int i = 0; i < Application.board.entities.size(); i++) {
			Entity current = Application.board.entities.get(i);
			if (current.equals(this) || current.isVoid) continue;

			//bottom collision
			if (/**/((y + height >= current.y) && y < current.y)/**/ && ( /**/(x >= current.x && x < current.x + current.width)/**/ || (x + width >= current.x && x <= current.x + current.width))) {
				return true;
			}
		}
		return false;
	}

	public void renderMask(Display d) {

	}

	protected Entity getEntityunderneath() {
		for (int i = 0; i < Application.board.entities.size(); i++) {
			Entity current = Application.board.entities.get(i);
			if (current.equals(this)) continue;

			//bottom collision
			if (/**/((y + height >= current.y) && y < current.y)/**/ && ( /**/(x >= current.x && x <= current.x + current.width)/**/ || (x + width - 1 >= current.x && x + width <= current.x + current.width))) {
				return current;
			}
		}
		return null;
	}

	protected Entity getEntityRight() {
		for (int i = 0; i < Application.board.entities.size(); i++) {
			Entity current = Application.board.entities.get(i);
			if (current.equals(this) || current.isVoid()) continue;

			//bottom collision
			if (/**/((y + height > current.y) && y < current.y + current.getHeight())/**/ && (x + width >= current.x && x + width < current.x + current.width)) {
				return current;
			}
		}
		return null;
	}

	protected Entity getEntityInside() {
		for (int i = 0; i < Application.board.entities.size(); i++) {
			Entity current = Application.board.entities.get(i);
			if (current.equals(this)) continue;

			//bottom collision
			if (/**/((y + height > current.y) && y < current.y + current.getHeight())/**/ && (x >= current.x && x < current.x + current.width)) {
				return current;
			}
		}
		return null;
	}

	protected void gravity() {
		if (yDir <= maxSpeed) {
			yDir += (Application.board.GRAVITY * weight);
			upsCount = 0;
		} else yDir = maxSpeed;

		upsCount++;
	}

	protected boolean isOnSameVertical(Entity current) {
		if ((current.y < y + height && current.y > y) || (current.y < y + height && current.y > y - height) || (current.y + current.getHeight() <= y && current.y + current.getHeight() >= y)) return true;
		return false;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void clickedOn() {
	}

	public double getYDir() {
		return yDir;
	}

	protected void makeVoid() {
		isVoid = true;
	}

	public boolean isVoid() {
		return isVoid;
	}

	public void inactive() {

	}

	public void active() {

	}

}
