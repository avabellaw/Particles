package io.res;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler {

	public static FileHandler boardBuilderHelp = new FileHandler("res/Help.txt", "<br />");

	private String text = "";

	public FileHandler(String pathname, String newLine) {
		File file = new File(pathname);

		if (!file.exists()) try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));

			String line;
			while ((line = br.readLine()) != null) {
				text += line + newLine;
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getText() {
		return text;
	}

}
