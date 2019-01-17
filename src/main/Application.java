package main;

import java.awt.event.MouseEvent;

import board.Board;
import board.Map;
import entity.player.Player;
import graphics.Display;
import io.Inputs;
import io.res.Music;
import main.menu.PauseMenu;

public class Application extends Component {

	public static Board board;
	public static Player player = new Player(0, 0);
	public static Inputs inputs;
	public static PauseMenu pauseMenu;

	public Application(Display display, Main main) {
		super(display, main);
		//NOTHING PAST THIS POINT
	}

	@Override
	public void reinit() {
//		Map.init();
		board = Map.MAP[0][0];
		display.setTitle("Particles !");
		player.reinit();
		
		Music.background.beginning();
		inputIsActive(true);
	}

	protected void init() {
		board = Map.MAP[0][0];
		inputs = new Inputs(board, player);

				display.addMouseListener(inputs);
		display.addMouseMotionListener(inputs);
		display.addKeyListener(inputs);
		inputIsActive(true);

		pauseMenu = new PauseMenu(display, main, Player.mask);
	}

	public static void music(boolean start) {
		if (start) Music.background.resume();
		else Music.background.pause();
	}

	@Override
	public void render() {
		board.render(display);
	}

	@Override
	public void renderMasks() {
		board.renderMasks(display);
	}

	/*	@Override
		public void closeComponent() {
			player = new Player(0, 0);
			Map.init();
			clip.close();
			isRunning = false;
			Main.current = state.START_MENU;
		}
	*/
	


	public void close() {
		inputIsActive(false);
	}
	
	@Override
	protected void update() {
		board.update();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	public void inputIsActive(boolean active) {
		
	}
}
