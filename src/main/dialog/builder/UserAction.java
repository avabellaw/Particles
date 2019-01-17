package main.dialog.builder;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import main.BoardBuilder;

public abstract class UserAction {
	public static List<UserAction> actions = new ArrayList<UserAction>();
	private static int index = -1;
	protected BoardBuilder bb;

	public UserAction(BoardBuilder bb) {
		this.bb = bb;
	}

	public static void undoAction() {
		if (index < 0) return;
		actions.get(index).undo();
		index--;
	}

	public static void redoAction() {
		if (actions.size() > 0 && index < actions.size() - 1) {
			index++;
			actions.get(index).redo();
		}
	}

	public static void addResizeAction(Dimension oldSize, Dimension newSize, BoardBuilder bb) {
		removeItemsAbove();
		actions.add(new Resized(oldSize, newSize, bb));
		index++;
	}

	public static void addTileAction(int tileCode, int previousTileCode, int index, BoardBuilder bb) {
		removeItemsAbove();
		actions.add(new Tile(tileCode, previousTileCode, index, bb));
		UserAction.index++;
	}

	private static void removeItemsAbove() {
		if (actions.size() > UserAction.index + 1) {
			int count = actions.size() - 1;
			while (UserAction.index + 1 < actions.size()) {
				actions.remove(count--);
			}
		}

	}

	protected abstract void undo();

	protected abstract void redo();

}

class Tile extends UserAction {
	private final int tileCode, previousTileCode, index;

	public Tile(int tileCode, int previousTileCode, int index, BoardBuilder bb) {
		super(bb);
		this.tileCode = tileCode;
		this.previousTileCode = previousTileCode;
		this.index = index;
	}

	@Override
	protected void undo() {
		bb.setTileDown(index, previousTileCode);
	}

	@Override
	protected void redo() {
		bb.setTileDown(index, tileCode);
	}

}

class Resized extends UserAction {
	private final Dimension newSize, oldSize;

	public Resized(Dimension oldSize, Dimension newSize, BoardBuilder bb) {
		super(bb);
		this.newSize = newSize;
		this.oldSize = oldSize;
	}

	@Override
	protected void undo() {
		bb.resize(oldSize.width, oldSize.height);
	}

	@Override
	protected void redo() {
		bb.resize(newSize.width, newSize.height);

	}
}
