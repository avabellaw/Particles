package main;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.Map;
import graphics.Display;
import graphics.sprites.Sprite;
import io.menu_input.MenuInputs;
import main.menu.Button;
import main.menu.Items;
import main.menu.Text;

public class StartMenu extends Component {

	private static int width = 35 * Board.tileSize, height = 28 * Board.tileSize;
	private static double scale = 1;

	protected static MenuInputs input;

	private int offsetter = 1, count = 0;

	private static List<Items> items = new ArrayList<Items>();

	public StartMenu(Display display, Main main) {
		super(display, main);
		//NOTHING PAST THIS POINT
	}

	@Override
	public void reinit() {
		display.setTitle("Particles?");
		Map.refresh();

		if (display.frame.getJMenuBar() != null) {
			display.frame.setJMenuBar(null);
		}
		input.isActive(true);
	}

	@Override
	protected void init() {
		display.setTitle("Particles?");

		addMenuItems();

		Map.refresh();

		input = new MenuInputs(this);
		display.addMouseListener((input));
		input.isActive(true);
	}

	private void addMenuItems() {
		items.add(new Text(centerHorizontal(Sprite.title.getWidth()), 14, Sprite.title.getWidth(), Sprite.title.getHeight(), Sprite.title));

		Items startButton = new Button.Start(centerHorizontal(Button.Start.dimensions.width), (int) (height * 0.58));
		items.add(Main.buttonMusic);
		items.add(startButton);
		items.add(new Button.Builder(centerHorizontal(Button.Builder.dimensions.width), startButton.getY() + startButton.getHeight() + 7 * 3));
	}

	private int centerHorizontal(int itemWidth) {
		itemWidth /= 2;
		int displayCenter = width / 2, x;

		x = displayCenter - itemWidth;
		return x;
	}

	public void mouseClicked(MouseEvent e) {
		int x = e.getX(), y = e.getY();
		for (int i = 0; i < items.size(); i++) {
			Items currentItem = items.get(i);

			if (currentItem instanceof Button) buttonClicked(x, y, (Button) items.get(i));

		}
	}

	private void buttonClicked(int x, int y, Button current) {
		if (!(Main.current instanceof StartMenu)) return;
		x /= scale;
		y /= scale;
		if ((x >= current.getX() && x <= (current.getX() + current.getSize().width)) && (y >= current.getY() && y <= current.getY() + current.getSize().height)) {
			current.clicked();

		}
	}

	public static void startParticles() {
		Main.application.reinit();
		Main.current = Main.application;
		Application.music(true);
	}

	public static void startBoardBuilder() {
		Main.current.close();
		BoardBuilder bb;
		Main.current =(bb =  new BoardBuilder(Main.display, main));
		bb.reinit();
	}

	@Override
	public void render() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x * y % 1000 == y) {
					display.pixels[x + y * width] = 0xffff12ff;
				} else {

					display.pixels[x + y * width] = 0xff111551;
				}

				if (x * (y + offsetter) % 100 == 0) display.pixels[x + y * width] = 0xff9989ff;
			}
		}
		for (int i = 0; i < items.size(); i++)
			items.get(i).render(display);
	}

	@Override
	protected void update() {
		count++;
		if (count >= 1) {
			offsetter += 1;
		}

		if (offsetter >= 1000) offsetter = 5;
		if (count >= 60) count = 0;
	}

	@Override
	public void close() {
		input.isActive(false);
	}

	@Override
	public void inputIsActive(boolean active) {
		input.isActive(active);
		
	}
}