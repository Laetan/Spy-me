package fr.schaltiemi.pong;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;


import fr.schaltiemi.pong.manager.GameInputProcessor;
import fr.schaltiemi.pong.manager.GameKeys;
import fr.schaltiemi.pong.manager.GameStateManager;

public class Pong implements ApplicationListener {
	
	//size screen
	public static int WIDTH;
	public static int HEIGHT;
	
	public static int scoreJ1;
	public static int scoreJ2;
	//speed ball/maxspeed/IA paddle speed - change following to the difficulty level
	public static int ballSpeed;
	public static int ballMaxSpeed;
	public static int tempSpeed;
	public static int IASpeed;
	public static int playState=0; //1 -nplayer, 2 menu, 3 play, 4 game over
	public static boolean isIA;
	
	public static OrthographicCamera cam;
	
	public static GameStateManager gsm;
	
	//calls once when the game starts up
	public void create() {	
		WIDTH=Gdx.graphics.getWidth();
		HEIGHT=Gdx.graphics.getHeight();
		//set the camera at the game's dimension
		cam = new OrthographicCamera(WIDTH,HEIGHT);
		//translate it to superpose it to the game
		cam.translate(WIDTH/2,HEIGHT/2);
		cam.update();
		
		Gdx.input.setInputProcessor(new GameInputProcessor());
		gsm=new GameStateManager();
		scoreJ1=0;
		scoreJ2=0;
	}

	//game loop method
	public void render() {
		//black screen
		Gdx.gl.glClearColor(0, 0,0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.draw();
		GameKeys.update();
	}

	//calls when the app exit
	public void dispose() {
	}

	//calls whenever you change the size of the window
	public void resize(int width, int height) {
	}

	//android - home page/call
	public void pause() {
	}

	//android -resume app
	public void resume() {
	}
}
