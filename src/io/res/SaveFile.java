package io.res;

import java.io.File;
import java.io.IOException;

public class SaveFile {
	
	//Static variables
	public static final String SAVE_FILE_NAME[] = new String[] { "game_state.psf" };
	private static File staticFile;
	
	
	//SaveFile variables
	public static SaveFile[] saveFiles = new SaveFile[SAVE_FILE_NAME.length];
	
	private File file;
	public SaveFile(File file) {
		this.file = file;
	}
	
	//Setup static files
	static {
		for(int i = 0; i < SAVE_FILE_NAME.length; i++) {
			staticFile = new File(SAVE_FILE_NAME[i]);
			
			if(!staticFile.exists()) {
				try {
					staticFile.createNewFile();
				} catch (IOException e) {
					System.out.println("Error creating save file");
				}
			}
			
			saveFiles[i] = new SaveFile(staticFile);
		}
	}

}
