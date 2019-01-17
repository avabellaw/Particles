package main.menu;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import entity.Entity;
import graphics.Display;
import io.menu_input.MenuInputs;
import main.Application;
import main.Main;

public abstract class Menu extends Entity {
	protected static double scale = 1;
	protected static List<Items> items = new ArrayList<Items>();
	protected MenuInputs mi = new MenuInputs(this);

	public Menu(int x, int y, int width, int height) {
		super(x, y, width, height, null);

		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;

		pixels = new int[width * height];
		Main.display.addMouseListener(mi);
	}

	public int[] getPixels() {
		return pixels;
	}

	public void mouseClicked(MouseEvent e) {
		if (!Main.showPausedMenu) return;
		int x = e.getX(), y = e.getY();
		for (int i = 0; i < items.size(); i++) {
			Items currentItem = items.get(i);

			if (currentItem instanceof Button) buttonClicked(x, y, (Button) items.get(i));

		}
	}

	protected void buttonClicked(int x, int y, Button current) {
		x /= scale;
		y /= scale;
		if ((x >= current.getX() && x <= (current.getX() + current.getSize().width)) && (y >= current.getY() && y <= current.getY() + current.getSize().height)) {
			current.clicked();
		}
	}

	public void inputIsActive(boolean active) {
		mi.isActive(active);
	}
	
	public abstract void close();

	public abstract void reinit();
}
