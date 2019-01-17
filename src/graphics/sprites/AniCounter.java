package graphics.sprites;

public class AniCounter {
	private int count = 0, limit, framerate, frcount = 0;

	public AniCounter(int limit, int framerate) {
		this.limit = limit;
		this.framerate = framerate;
	}

	public int getCount() {
		return count;
	}

	public void updateCount() {
		if (frcount >= framerate) {
			count++;

			if (count > limit || count < 0) count = 0;
			frcount = 0;
		}
		frcount++;
	}
}
