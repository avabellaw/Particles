package io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import board.Board;
import board.Map;
import entity.TestParticle;
import entity.player.Player;
import main.Application;

public class Inputs implements MouseListener, KeyListener, MouseMotionListener {
	Board board;

	public int mouseX = 0, mouseY = 0;
	private Player player;
	boolean clicked = false;

	private Set<Integer> pressed = new LinkedHashSet<Integer>();

	TestParticle held = null;

	boolean two = true;

	public Inputs(Board board, Player player) {
		this.board = board;
		this.player = player;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_R) {
			Map.moveToCheckPoint();
			return;
		}
		
		if(Application.board.isShowingDialog() && !clicked) return;
		clicked = false;
		pressed.add(e.getKeyCode());
		Iterator<Integer> it = pressed.iterator();
		while (it.hasNext()) {
			player.keyClicked(it.next(), pressed.size() > 1 ? false : true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {	
		clicked = true;
		int keyCode = e.getKeyCode();
		if (keyCode != KeyEvent.VK_SPACE) player.keyReleased(e);
		pressed.remove(keyCode);
		Iterator<Integer> it = pressed.iterator();
		while (it.hasNext()) {
			player.keyClicked(it.next(), false);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		/*	if (two) {
				board.entities.add(new TestParticle(mouseX, mouseY, 10, 10, 2, board));
			} else board.entities.add(new TestParticle2(mouseX, mouseY, 10, 10, 2, board));
		
			two = !two;*/
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {

		/*mouseX = e.getX();
		mouseY = e.getY();
		for (int i = 0; i < board.entities.size(); i++) {
			Entity current = board.entities.get(i);
		
			if ((mouseX >= current.x && mouseX <= current.x + current.getHeight()) && (mouseY >= current.y && mouseY <= current.y + current.getHeight())) {
				current.clickedOn();
				try {
					held = (TestParticle) current;
				} catch (Exception e1) {
					//It just tried to hold a tile
				}
			}
		}
		
		for (int i = 0; i < board.entities.size(); i++) {
			Entity current = board.entities.get(i);
		
			if ((mouseX >= current.x && mouseX <= current.x + current.getHeight()) && (mouseY >= current.y && mouseY <= current.y + current.getHeight())) {
				System.out.println(current.getYDir());
			}
		}*/

	}

}
