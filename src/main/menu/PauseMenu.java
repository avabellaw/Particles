package main.menu;

import board.Board;
import board.dialogs.Dialog;
import graphics.Display;
import graphics.Mask;
import main.Application;
import main.Main;

public class PauseMenu extends Menu {

	private static int width = 35 * Board.tileSize, height = 28 * Board.tileSize;
	public Mask mask;

	Display display;

	public PauseMenu(Display display, Main main, Mask mask) {
		super(0, 0, width, height);
		this.display = display;

		this.mask = mask;
		addMenuItems();

		for (int i = 0; i < items.size(); i++) {
			if(items.get(i) instanceof Button.Music) continue;
			items.get(i).preRender(this);
		}
	}

	private void addMenuItems() {
		items.add(new Text(0, 0, Dialog.Sprite.pause_menu.getWidth(), Dialog.Sprite.pause_menu.getHeight(), Dialog.Sprite.pause_menu));

		Items mainMenuButton = new Button.MainMenu(centerHorizontal(Button.Resume.dimensions.width), (int) (height * 0.40));
		Items resumeButton = new Button.Resume(centerHorizontal(Button.Resume.dimensions.width), (int) (height * 0.78));
		items.add(resumeButton);
		items.add(mainMenuButton);
		items.add(Main.buttonMusic);
	}

	private int centerHorizontal(int itemWidth) {
		itemWidth /= 2;
		int displayCenter = width / 2, x;
		x = displayCenter - itemWidth;
		return x;
	}

	public void render() {
		for (int yi = 0; yi < height; yi++) {
			for (int xi = 0; xi < width; xi++) {
				if (Display.isVoid(pixels[xi + yi * width])) continue;
				mask.pixels[(x + xi) + ((y + yi) * width)] = pixels[xi + yi * width];

			}
		}
				Main.buttonMusic.render(display, mask);
	}

	@Override
	public void update() {
	}
	
	@Override
	public void close() {
		mi.isActive(false);
		Main.showPausedMenu = false;
		Display.pauseGameDrawing(false);
		Application.music(true);
		Main.display.setTitle(Main.TITLE);
	}

	@Override
	public void reinit() {
		mi.isActive(true);
		Main.showPausedMenu = true;
		Main.display.setTitle(Main.TITLE + " - Paused");
		Display.pauseGameDrawing(true);
		Application.music(false);
	}
	
	
}
