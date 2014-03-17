package com.SpyMe.FileNinja;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Starter classe pour Windows.
 * <p>
 * Configure une écran de 800x480.
 * @author Nathanael
 * @version 0.1
 * 
 * 
 */
public class DesktopGame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "FileNinja";
		cfg.height = 800;
		cfg.width = 480;
		new LwjglApplication(new FileNinja(), cfg);
	}

}
