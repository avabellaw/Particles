package graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import entity.Entity;
import graphics.sprites.Sprite;
import main.Application;
import main.Main;

public class Light extends Entity {

	public int[] pixels;
	public int count = 0;
	Sprite sprite;
	public boolean isLight = false, initial = true;;

	public Light(Entity entity) {
		super(entity.x, entity.y, entity.getWidth(), entity.getHeight(), null);
	}

	public void draw( Mask mask,Sprite sprite) {
		x = Application.player.x;
		y = Application.player.y;

		this.sprite = sprite;

		if (initial) {
			mask.mask = new BufferedImage(sprite.getWidth(), sprite.getHeight(), BufferedImage.TYPE_INT_ARGB);
			pixels = ((DataBufferInt) mask.mask.getRaster().getDataBuffer()).getData();
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = 0x0;
			}
			initial = false;
		}
	}

	@Override
	public void update() {
		if (count < 255 && isLight) {
			if (Main.display.isPausedWithClear()) {
				Display.pausedWithClear(false);
				Application.board.pause(false);
			}
			for (int i = 0; i < pixels.length; i++) {
				int r, g, b, trans;
				r = 0xFF & (sprite.getSprite()[i]) >> 16;
				g = 0xFF & (sprite.getSprite()[i]) >> 8;
				b = 0xFF & (sprite.getSprite()[i]) >> 0;
				trans = 255 - count + (0xFF & (sprite.getSprite()[i]) >> 24);
				;
				if (trans > 255) trans = 255;
				int argb = (trans << 24) | (r << 16) | (g << 8) | b;
				pixels[i] = argb;
			}
			count += 5;
		} else {
			Application.board.resume(false);
			count += 5;
			for (int i = 0; i < pixels.length; i++)
				pixels[i] = sprite.getSprite()[i];
		}

	}

}
