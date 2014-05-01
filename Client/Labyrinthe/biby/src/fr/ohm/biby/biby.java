package fr.ohm.biby;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import fr.ohm.biby.manager.GameInputProcessor;
import fr.ohm.biby.manager.GameKeys;
import fr.ohm.biby.manager.GameStateManager;

public class biby implements ApplicationListener {
	
	public static int WIDTH;
	public static int HEIGHT;
	public static int ballSpeed=20;
	public static int playState=0;
	public static GameStateManager gsm;
	public static OrthographicCamera cam;
	
	
	/**
	 * function called when the game is created
	 */
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
	}


	/**
	 * function called every "delta time"
	 */
	public void render() {		
		//black screen
		Gdx.gl.glClearColor(0, 0,0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.draw();
		GameKeys.update();
		
	}
	
	public void dispose() {
	}

	public void resize(int width, int height) {
	}

	public void pause() {
	}

	public void resume() {
	}
}
