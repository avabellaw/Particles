package entity.player;

import java.awt.event.KeyEvent;

import board.Board;
import board.Map;
import entity.Entity;
import graphics.Display;
import graphics.Mask;
import graphics.sprites.Sprite;
import main.Application;
import main.Main;
import tiles.Odskok;
import tiles.Stairs;
import tiles.TeleTile;

public class Player extends Entity {

	//	private boolean hasMagic = false;
	private static final double SPEED = 5.4;
	public static Mask mask;

	//Application.board
	public Player(int x, int y) {
		super(x, y, Board.tileSize, Board.tileSize * 2, Sprite.player);

		mask = new Mask(this);
		this.weight = 2.2;
		yDir = 0;
		hardness = 1.2;
		maxSpeed = 15;
	}
	
	public void reinit() {
		mask.reinit();
	}

	@Override
	public void renderMask(Display d) {
		mask.draw(d);
		if (Main.showPausedMenu) Application.pauseMenu.render();
	}

	public void keyClicked(int e, boolean clicked) {
		if (e == KeyEvent.VK_ESCAPE/* && !Application.board.isShowingDialog()*/) {
			if(Main.showPausedMenu) {
				Application.board.resume(true);
			} else {
				Application.board.pause(true);
			}
		}
		if (Application.board.isPaused) return;
		if (Application.board.isShowingDialog() && clicked) {
			stopXMovement(); //////////////////////////////////////////Sets xDir to 0 !!
			if (e == KeyEvent.VK_SPACE) Application.board.advanceDialog();
			return;
		}

		if (e == KeyEvent.VK_D) xDir = SPEED;
		if (e == KeyEvent.VK_A) xDir = (-SPEED);

		switch (e) {
		case KeyEvent.VK_SPACE:
			if (isCollidingBottom() && !(getEntityunderneath() instanceof Odskok)) yDir = -10;
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		/*	if (Application.board.isShowingDialog()) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) Application.board.advanceDialog();
				return;
			}*/
		switch (e.getKeyCode()) {
		case KeyEvent.VK_D:
			xDir = 0;
			break;
		case KeyEvent.VK_A:
			xDir = 0;
			break;
		}
	}

	@Override
	public void update() {
		if (mask.light.count <= 255 && mask.light.isLight) {
			mask.light.update();
		}
		if (Application.board.isPaused) return;

		if (x + width > Application.board.getScreenWidth()) {
			if (Map.hasRight()) {
				boolean collidingInRight = false;

				for (int i = 0; i < Map.getMapRight().entities.size(); i++) {
					if (isOnSameVertical(Map.getMapRight().entities.get(i)) && Map.getMapRight().entities.get(i).x == 0) collidingInRight = true;
				}

				if (!collidingInRight) Application.board = Map.moveMapRight();
			}
		}
		if (x < 0 && Map.hasLeft()) {
			Application.board = Map.moveMapLeft();
			if (getEntityInside() != null) {
				Application.board = Map.moveMapRight();
			}
		}
		if (getEntityInside() instanceof TeleTile) {
			Application.board = Map.moveMapBelow(0);
		}

		if (y + height >= Application.board.getScreenHeight()) {
			Map.moveToCheckPoint();
		}

		updateMovement();
		gravity();
	}

	@Override
	protected boolean isCollidingBottom() {
		if (y + height >= Application.board.getScreenHeight()) return true;
		for (int i = 0; i < Application.board.entities.size(); i++) {
			Entity current = Application.board.entities.get(i);
			if (current.equals(this) || current.isVoid()) continue;

			//bottom collision
			if (/**/( /**/(x > current.x && x < current.x + current.getWidth())/**/ || (x + width > current.x && x < current.x + current.getWidth()))) {
				if (current instanceof Stairs) {
					if (y + height + (x - current.x) >= current.y && y + (x - current.x) < current.y) return true;
					else continue;
				}

				if ((y + height >= current.y) && y < current.y) {
					return true;
				}
			}
		}
		return false;
	}

	public Entity getUnderneath() {
		if (y + height >= Application.board.getScreenHeight()) return null;
		for (int i = 0; i < Application.board.entities.size(); i++) {
			Entity current = Application.board.entities.get(i);
			if (current.equals(this) || current.isVoid()) continue;

			//bottom collision
			if (/**/( /**/(x > current.x && x < current.x + current.getWidth())/**/ || (x + width > current.x && x < current.x + current.getWidth()))) {
				if ((y + height >= current.y) && y < current.y) {
					return current;
				}
			}
		}
		return null;
	}

	@Override
	protected boolean isCollidingRight() {
		if (x + width > Application.board.getScreenWidth()) return true;
		for (int i = 0; i < Application.board.entities.size(); i++) {
			Entity current = Application.board.entities.get(i);
			if (current.equals(this) || current.isVoid()) continue;

			if (current instanceof Stairs) {
				if (y + height >= current.y - ((x - current.x) + current.getWidth()) && y < current.y + current.getHeight() - (x - current.x + current.getWidth())) {
					if (x + width >= current.x && x < current.x) {
						return true;
					}
				}
				continue;
			}

			if (isOnSameVertical(current)) {
				if (x + width >= current.x && x <= current.x) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	protected boolean isCollidingLeft() {
		for (int i = 0; i < Application.board.entities.size(); i++) {
			Entity current = Application.board.entities.get(i);
			if (current.equals(this) || current.isVoid()) continue;

			if (isOnSameVertical(current)) {
				if (x <= current.x + current.getWidth() && x + width > current.x) {
					if (current instanceof Stairs) return false;
					return true;
				}
			}
		}

		return false;
	}

	public void pushToEnd() {
		x = Application.board.getScreenWidth() - width;
	}

	public void pull(int x, int y) {
		xDir = yDir = 0;

		this.x += x;
		this.y += y;
		xDir = 0;
	}

	public void hasLight() {
		mask.light.isLight = true;
	}

	public boolean isLit() {
		return (mask.light.count >= 255 ? false : true);
	}
	public void stopXMovement() {
		xDir = 0;
	}

	//	public void hasMagic(boolean hasMagic) {
	//		this.hasMagic = hasMagic;
	//	}

}
