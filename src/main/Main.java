package main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import board.Board;
import graphics.Display;
import main.menu.Button;

public class Main {
	public static boolean isRunning = true;
	public static Component current, application;
	public static Display display;
	public static Button.Music buttonMusic = new Button.Music(0, 0);

	static int fpsTotal = 0;
	static int secondsOpen = 0;

	// public static Dialog paused = new Dialog(Dialog.Sprite.pause_menu, new
	// DialogAction.Instant());

	public static boolean showPausedMenu = false;
	public final static double UPS = 60.0;

	public static StartMenu startMenu;

	public static final String TITLE = "Particles";

	public Main() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		display = new Display(35 * Board.tileSize, 28 * Board.tileSize, this);
		startMenu = new StartMenu(display, this);
		current = startMenu;

		application = new Application(display, this);
		run();
	}

	public static enum state {
		START_MENU, APPLICATION, BOARD_BUILDER, ENDED;
	}

	protected void run() {
		long before = System.nanoTime(), start = System.currentTimeMillis();
		double delta = 0.0;
		int ups = 0, fps = 0;

		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - before) / (1000000000.0 / UPS);

			display.render();
			fps++;

			while (delta >= 1) {
				update();
				ups++;
				delta--;
			}

			if (System.currentTimeMillis() - start >= 1000) {
				System.out.println("ups: " + ups + ", fps: " + fps);
				fpsTotal += fps;
				secondsOpen++;
				fps = ups = 0;

				start += 1000;
			}

			before = now;
		}

		display.close();
	}

	public static void close() {
		if (current instanceof Application) {
			Application.board.pause(true);
			return;
		} else if (current instanceof BoardBuilder) {
			BoardBuilder bb = (BoardBuilder) Main.current;
			bb.s.close();
			bb.close();
			Main.current = Main.startMenu;
			return;
		}

		isRunning = false;
		if (secondsOpen > 0)
			System.out.println("FPS Avg: " + fpsTotal / secondsOpen);
	}

	public static void main(String[] args) {
		new Main();
	}

	public void render() {
		current.render();
	}

	public void renderMasks() {
		current.renderMasks();
	}

	void update() {
		current.update();
	}

}
