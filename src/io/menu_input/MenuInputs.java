package io.menu_input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Component;
import main.menu.Menu;

public class MenuInputs implements MouseListener, KeyListener, MouseMotionListener {

	private Menu menu;
	private Component component;
	private boolean isActive = false;

	public MenuInputs(Component component) {
		this.component = component;
	}

	public MenuInputs(Menu menu) {
		this.menu = menu;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if (isActive) {
			if (menu != null) menu.mouseClicked(e);
			else if (component != null) component.mouseClicked(e);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (isActive) {
			if (component != null) component.keyClicked(e);
			;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (isActive) {
			if (component != null) component.mouseClicked(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (isActive) {
			if (component != null) component.mouseMoved(e);
		}
	}
	
	public void isActive(boolean active) {
		this.isActive = active;
	}
	
	public boolean getIsActive() {
		return isActive;
	}

}
