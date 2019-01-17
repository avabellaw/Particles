package main;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import graphics.Display;
import io.menu_input.MenuInputs;

public abstract class Component {

	public static Main main;
	public Display display;
	public boolean init = false;

	public Component(Display display, Main main) {
		this.display = display;
		Component.main = main;
		init();
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void keyClicked(KeyEvent e) {

	}

	public void mouseMoved(MouseEvent e) {

	}

	public void renderMasks() {

	}
	public abstract void inputIsActive(boolean active);

	public abstract void reinit();
	protected abstract void init();

	protected abstract void update();

	protected abstract void render();

	public abstract void close();
}
