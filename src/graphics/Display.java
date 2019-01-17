package graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import graphics.sprites.Sprite;
import main.Main;

public class Display extends Canvas {

	private static final long serialVersionUID = 1L;

	private BufferedImage image;
	public int[] pixels;
	public JFrame frame;
	public int pWidth, pHeight;
	private Graphics g;
	private static boolean isPaused = false, pausedWithClear = false;

	public Main main;

	private double scale = 1;

	private boolean skip = false;

	public Display(int width, int height, Main main) {
		init(width, height);
		this.main = main;

		setFocusable(true);
	}

	public static void pauseGameDrawing(boolean pause) {
		isPaused = pause;
	}

	public Display(int width, int height, double scale) {
		this.scale = scale;
		init(width, height);

		setFocusable(true);
	}

	private void init(int width, int height) {
		this.pWidth = width;
		this.pHeight = height;

		this.setName(Main.TITLE);
		this.setPreferredSize(new Dimension((int) (width * scale), (int) (height * scale)));

		pixels = new int[width * height];

		setImg(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));

		createFrame();

		pixels = ((DataBufferInt) getImg().getRaster().getDataBuffer()).getData();
	}

	public static boolean isVoid(int pixel) {
		if (pixel == new Color(186, 255, 245).getRGB() || pixel == new Color(20, 0, 0).getRGB()) return true;
		return false;
	}
	
	public static int voidPixel() {
		return new Color(186, 255,245).getRGB();
	}

	public void resize(int width, int height) {
		skip = true;
		setPreferredSize(new Dimension((int) (width * scale), (int) (height * scale)));
		this.pWidth = width;
		this.pHeight = height;
		frame.add(this);
		frame.pack();
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				Main.close();
			}

		});

		pixels = new int[(width) * (height)];
		setImg(new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB));
		pixels = ((DataBufferInt) getImg().getRaster().getDataBuffer()).getData();

		skip = false;
	}

	protected void createFrame() {
		frame = new JFrame(this.getName());
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);

		frame.add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				Main.close();
			}

		});
		frame.setIconImage(Sprite.icon.getImg());

		frame.setVisible(true);
	}

	public void setTitle(String title) {
		this.setName(title);
		frame.setTitle(getName());
	}

	public String getTitle() {
		return this.getName();
	}

	public void render() {
		if (skip) return;

		if (getBufferStrategy() == null) {
			createBufferStrategy(3);
			return;
		}

		g = getBufferStrategy().getDrawGraphics();
		clear();
		if (!isPaused && !isPausedWithClear()) main.render();

		g.drawImage(getImg(), 0, 0, (int) (pWidth * scale), (int) (pHeight * scale), null);

		main.renderMasks();
		g.dispose();

		getBufferStrategy().show();
	}

	public void clear() {
		if (isPaused) return;
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0xff000000;
		}
	}

	public void close() {
		getBufferStrategy().dispose();
		frame.dispose();
	}

	public BufferedImage getImg() {
		return image;
	}

	public void setImg(BufferedImage image) {
		this.image = image;
	}

	public double getScale() {
		return scale;
	}

	public Graphics2D get2DGraphics() {
		return (Graphics2D) g;
	}

	public static void pausedWithClear(boolean paused) {
		pausedWithClear = paused;
	}

	public boolean isPausedWithClear() {
		return pausedWithClear;
	}
}
