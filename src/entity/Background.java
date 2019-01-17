package entity;

public class Background {

	public int[] pixels;

	public Background(int[] pixels) {
		this.pixels = new int[pixels.length];
		for (int i = 0; i < pixels.length; i++)
			this.pixels[i] = pixels[i];
	}

}
