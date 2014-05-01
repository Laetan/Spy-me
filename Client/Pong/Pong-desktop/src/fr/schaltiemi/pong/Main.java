package fr.schaltiemi.pong;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;



public class Main {
	public static void main(String[] args) {
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Pong";
		cfg.width = 480;
		cfg.height = 700;
		cfg.useGL30=false;
		cfg.resizable=false;
		
		new LwjglApplication(new Pong(), cfg);
	}
}
