package main.menu;

import java.awt.Dimension;

import graphics.Display;
import graphics.sprites.Sprite;
import main.Application;
import main.Main;
import main.StartMenu;

public abstract class Button extends Items {

	//Could have made this Button class abstract then had different classes for the different buttons

	/*public static enum button_type {
		START, BUILDER, HELP;
	}
	
	public button_type buttonType;*/

	Dimension dim;

	public Button(int x, int y, Dimension dimensions, Sprite sprite) {
		super(x, y, dimensions.width, dimensions.height, sprite);
		this.dim = dimensions;
	}

	public Dimension getDimension() {
		return dim;
	}

	public void clicked() {

	}

	public static class Start extends Button {
		public static Dimension dimensions = new Dimension(350, 70);

		public Start(int x, int y) {
			super(x, y, dimensions, Sprite.startButton);
		}

		@Override
		public void clicked() {
			StartMenu.startParticles();
			;
		}
	}

	public static class Builder extends Button {
		public static Dimension dimensions = new Dimension(350, 50);

		public Builder(int x, int y) {
			super(x, y, dimensions, Sprite.builderButton);
		}

		@Override
		public void clicked() {
			StartMenu.startBoardBuilder();
		}
	}

	public static class Help extends Button {
		public static Dimension dimensions = new Dimension(20, 20);

		public Help(int x, int y) {
			super(x, y, dimensions, Sprite.helpButton);
		}
	}

	public static class Resume extends Button {
		public static Dimension dimensions = new Dimension(350, 60);

		public Resume(int x, int y) {
			super(x, y, dimensions, Sprite.resumeButton);
		}
		
		public void clicked() {
			Application.board.resume(true);
		}
	}

	public static class MainMenu extends Button {
		public static Dimension dimensions = new Dimension(350, 60);

		public MainMenu(int x, int y) {
			super(x, y, dimensions, Sprite.mainMenuButton);
		}
		
		public void clicked() {
			Display.pausedWithClear(false);
			Application.board.resume(true);
			Main.current = Main.startMenu;
			Application.music(false);
		}
	}

	public static class Music extends Button {
		public static Dimension dimensions = new Dimension(32, 32);
		private boolean isMute = false; //it is muted

		public Music(int x, int y) {
			super(x, y, dimensions, Sprite.musicOn);
		}

		@Override
		public void clicked() {
			if(isMute) {
				isMute = false;
				setSprite(Sprite.musicOn);
				io.res.Music.background.resume();
				io.res.Music.background.mute(false);
			} else {
				isMute = true;
				setSprite(Sprite.musicOff);
				io.res.Music.background.pause();
				io.res.Music.background.mute(true);
			}
		}
	}
}
