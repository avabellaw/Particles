package io.res;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;

public class BoardFile {

	private List<Integer> board;
	private int row, lineMax;

	private static final Dimension DEFAULT_SIZE = new Dimension(35, 28);

	private static final String characterCodes_array[] = new String[] { "bg", "d"};
	private static String characterCode = "";
	
	public Background boardBackground;
	public int dialogNum = 0;

	private File file;

	public BoardFile(String pathname, List<Integer> boardValues) {
		init(pathname);

		board = boardValues;
	}

	public BoardFile(String pathname) {
		init(pathname);
	}

	private void init(String pathname) {
		board = new ArrayList<Integer>();
		file = new File(pathname);
		if (!file.exists())
			try {
				file.createNewFile();
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				for (int y = 0; y < DEFAULT_SIZE.getHeight(); y++) {
					for (int x = 0; x < DEFAULT_SIZE.getWidth(); x++) {
						bw.write("<0>");
					}
					bw.newLine();
				}

				bw.write("<!bg=DEFAULT>");
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		int lineMax = 35, row = 0;

		try {

			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				String code = "";
				characterLoop: for (int i = 0; i < line.length(); i++) {
					String character = line.substring(i, i + 1);
					if (character.equals("<"))
						continue characterLoop;

					try {
						// check for other codes
						if(character.equals("!")) {
							i+=1;
							characterCode = "err";
							for(String charCode : characterCodes_array) {
								//System.out.println(charCode);
								if(line.substring(i, i + charCode.length()).equals(charCode)) {
									i+= charCode.length();
									characterCode = charCode;
									continue characterLoop;
								}
							}
							
							/*if (line.substring(i, i + 3).equals(characterCodes_array[0] + "=")) {
								i += 2;
								characterCode = characterCodes_array[0];
								continue characterLoop;
							}
							if (line.substring(i, i + 2).equals(characterCodes_array[1] + "=")) {
								i += 1;
								characterCode = characterCodes_array[1];
								continue characterLoop;
							}*/
						}
					} catch (Exception e) {
						// no characters left {i + 3}
					}

					if (character.equals(">")) {
						if (characterCode != "") {
							if (characterCode.equals(characterCodes_array[0])) {
								
								// background code
								if(code.equals("DEFAULT")) {
									boardBackground = new Background(true, Sprite.background1);
								} else if(code.equalsIgnoreCase("1bee")) {
									boardBackground = new Background(true, Sprite.background1, Sprite.background1_);
								} else if (code.equalsIgnoreCase("crown")) {
									boardBackground = new Background(true, Sprite.background1, Sprite.background2);
								} else if(code.equalsIgnoreCase("crown-fixed")) {
									boardBackground = new Background(false, Sprite.background2);
								}

							} else if(characterCode.equals(characterCodes_array[1])) {
								dialogNum = Integer.parseInt(code);
							} else if(characterCode.equalsIgnoreCase("err")) {
								System.err.println("Character code error");
							}

							row--;
							code = "";
							characterCode = "";
							continue characterLoop;
						} else {
							board.add(Integer.parseInt(code));
							code = "";
							continue characterLoop;
						}

					}
					code += character;
				}
				row++;
			}

			br.close();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}

		this.row = row;
		this.lineMax = lineMax;
	}

	public void save(List<Integer> boardValues, int boardWidth) {
		final int width = boardWidth;

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));

			for (int i = 0; i < boardValues.size(); i++) {
				bw.write("<" + boardValues.get(i).toString() + ">");
				if (i - boardWidth + 1 >= 0) {
					bw.newLine();
					boardWidth += width;
				}
			}

			bw.write("<bg=1a>");

			bw.close();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	public Dimension getDimension() {
		return new Dimension(lineMax, row);
	}

	public List<Integer> getBoard() {
		return board;
	}

	public String getFileName() {
		return file.getName();
	}
	
	public class Background{
		public Sprite[] sprite;
		public boolean firstMoving;
		
		public Background(boolean firstMoving, Sprite... sprite ) {
			this.firstMoving = firstMoving;
			this.sprite = sprite;
		}
	}
}
