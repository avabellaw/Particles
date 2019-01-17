package board.dialogs;

import java.awt.Point;

import board.Board;
import entity.player.Player;
import graphics.Display;
import main.Application;
import tiles.Tile;

public abstract class DialogAction {

	protected Player player;

	public DialogAction() {
		this.player = Application.player;
	}

	public abstract void close();

	public abstract boolean isTriggered();

	//Different actions
	public static class atPoint extends DialogAction {

		private boolean findPosition = false;//set to true to print out actionPostion and player.x   improve to include y
		private int x, y;

		public atPoint(Point point) {
			if (x >= 0) this.x = point.x * Board.tileSize;
			if (y >= 0) this.y = point.y * Board.tileSize;
		}

		@Override
		public boolean isTriggered() {
			if (findPosition) System.out.println(player.x / Board.tileSize + "    " + player.y / Board.tileSize);
			if ((player.x >= x && player.x <= player.x + player.getWidth() || x < 0 ? true : false) && (player.y + player.getHeight() >= y && player.y + Board.tileSize <= y || y < 0 ? true : false)) return true;
			return false;
		}

		@Override
		public void close() {
		}

	}

	public static class Instant extends DialogAction {
		@Override
		public boolean isTriggered() {
			return true;
		}

		@Override
		public void close() {

		}

	}

	public static class first extends Instant {
		@Override
		public boolean isTriggered() {
			Display.pausedWithClear(true);
			return true;
		}

		@Override
		public void close() {
			Application.player.hasLight();
		}
	}

	public static class onTile extends DialogAction {
		String tile;

		public onTile(Tile tile) {
			this.tile = tile.getClass().getName();
		}

		@Override
		public void close() {

		}

		@Override
		public boolean isTriggered() {
			if (player.getUnderneath() != null) {
				if (player.getUnderneath().getClass().getName() == tile) {
					return true;
				}
			}
			return false;
		}
	}
}
