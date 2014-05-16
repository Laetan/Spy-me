package com.spyme.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.spyme.core.Spyme;

public class ExitScreen implements Screen{

//****************************************************************************************
//********************************************attributes**********************************
		
	Spyme game;
	OrthographicCamera cam;
	Texture texture;
	public static String coRepServeur;
	
//****************************************************************************************
//****************************************************************************************
	/**
	 * initialize the different elements
	 * @param gam:instance of the game
	 */
	public ExitScreen(Spyme gam){
		Gdx.graphics.setContinuousRendering(true);
		game = gam;
		this.texture = new Texture(Gdx.files.internal("deco_t.png"));
		cam = new OrthographicCamera();
		cam.setToOrtho(true, game.width, game.height);
		cam.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.input.setInputProcessor(new ExitHandler());
	}
	
	/**
	 * draw the different elements
	 */
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.batch.draw(texture,0,0,game.width,game.height);
		game.batch.end();
	}
	
	public void resize(int width, int height) {
	}

	public void show() {
	}

	public void hide() {
	}

	public void pause() {
	}

	public void resume() {
	}

	public void dispose() {
	}
	
//****************************************************************************************
//****************************************************************************************
		
	private class ExitHandler implements InputProcessor{

		public boolean keyDown(int keycode) {
			return false;
		}

		public boolean keyUp(int keycode) {
			return false;
		}

		public boolean keyTyped(char character) {
			return false;
		}

		/**
		 * called when the user touch the screen. Triggered the different events
		 */
		public boolean touchDown(int screenX, int screenY, int pointer,
				int button) {
			if(screenY>0.64f*game.height&&screenY<0.75f*game.height){
			if(screenX>0.19f*game.width&&screenX<0.385f*game.width){
				Spyme.state=2;
				String url="deconnexion?pseudo="+game.player.pseudo;
				game.httpReq(url);
				 while(game.repServeur == null){System.out.print("");}
				Gdx.app.exit();
			}else if(screenX>0.59f*game.width&&screenX<0.82f*game.width){
				game.setScreen(new MenuScreen(game));
			}}
			return false;
		}

		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			return false;
		}

		public boolean touchDragged(int screenX, int screenY, int pointer) {
			return false;
		}

		public boolean mouseMoved(int screenX, int screenY) {
			return false;
		}

		public boolean scrolled(int amount) {
			return false;
		}
	
	}
}
