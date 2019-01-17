package main.menu;

import java.awt.Color;
import java.awt.Dimension;

import graphics.Display;
import graphics.Mask;
import graphics.sprites.Sprite;

public abstract class Items {

	private int x, y, width, height, pixels[];


	public Items(int x, int y, int width, int height, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		pixels = new int[width * height];

		setSprite(sprite);
	}

	protected void setSprite(Sprite sprite) {
		for (int i = 0; i < pixels.length; i++)
			this.pixels[i] = sprite.getSprite()[i];
	}

	public void render(Display d) {
		for (int yi = 0; yi < height; yi++) {
			for (int xi = 0; xi < width; xi++) {
				if (Display.isVoid(pixels[xi + yi * width])) continue;
				d.pixels[(x + xi) + ((y + yi) * d.pWidth)] = pixels[xi + yi * width];
			}
		}
	}
	
	public void preRender(Menu menu) {
		for (int yi = 0; yi < height; yi++) {
			for (int xi = 0; xi < width; xi++) {
				if (Display.isVoid(pixels[xi + yi * width])) continue;
				menu.getPixels()[(x + xi) + ((y + yi) * menu.width)] = pixels[xi + yi * width];
				if (xi * yi % 1000 == yi && xi > 5 && xi < width - 5 && yi > 5 && yi < height - 5) {
					menu.getPixels()[(x + xi) + (y + yi) * width] = new Color(255, 18, 255).getRGB();
				}
				if (xi*(yi + 10) % 100 == 0 && xi > 5 && xi < width - 5 && yi > 5 && yi < height - 5) 
					menu.getPixels()[x + xi + (yi + y) * width] = 0x9989ff;
			}
		}
	}

	public void render(Display d, Mask m) {
		for (int yi = 0; yi < height; yi++) {
			for (int xi = 0; xi < width; xi++) {
				if (Display.isVoid(pixels[xi + yi * width])) continue;
				m.pixels[(x + xi) + ((y + yi) * d.pWidth)] = pixels[xi + yi * width];
			}
		}
	}

	public Dimension getSize() {
		return new Dimension(width, height);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
