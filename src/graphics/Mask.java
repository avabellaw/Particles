package graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import board.Board;
import entity.Entity;
import main.Application;
import main.Main;

public class Mask {
	Entity entity;
	public Light light;
	public int[] pixels;
	private BufferedImage pause = new BufferedImage(35 * Board.tileSize, 28 * Board.tileSize, BufferedImage.TYPE_INT_ARGB);
	private boolean preRender = true;
	public BufferedImage mask;

	public Mask(Entity entity) {
		pixels = new int[35 * Board.tileSize * 28 * Board.tileSize];
		pixels = ((DataBufferInt) pause.getRaster().getDataBuffer()).getData();
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0x0;
		}
		this.entity = entity;
		light = new Light(entity);
	}
	
	public void reinit() {
		light = new Light(entity);
	}

	public void draw(Display d) {
		light.draw(this, graphics.sprites.Sprite.lighting);
//		if(Main.showPausedMenu)Application.pauseMenu.render();
		//preRender the mask image for pauseMenu
		if(preRender) {
			Application.pauseMenu.render();
			preRender = false;
		}

		Graphics2D g2 = (Graphics2D) d.get2DGraphics();
		g2.drawImage(mask, (int) ((entity.x - mask.getWidth() / 2 + entity.width / 2) * d.getScale()), (int) ((entity.y - mask.getHeight() / 2 + entity.height / 2) * d.getScale()), null);
		if (Application.board.current != null) Application.board.current.render(g2);

		if (Main.showPausedMenu) g2.drawImage(pause, 0, 0, null);
	}

}
