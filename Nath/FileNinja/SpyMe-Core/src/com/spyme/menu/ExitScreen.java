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
	Texture [] textures;
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
		textures = new Texture[5];
		textures[0] = new Texture(Gdx.files.internal("deconnexion.png"));
		textures[1] = new Texture(Gdx.files.internal("oui1.png"));
		textures[2] = new Texture(Gdx.files.internal("oui2.png"));
		textures[3] = new Texture(Gdx.files.internal("non1.png"));
		textures[4] = new Texture(Gdx.files.internal("non2.png"));
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
		game.batch.draw(textures[0],game.width/6,game.height/3,game.width/1.5f,game.height/2.5f);
		game.batch.draw(textures[2],game.width/4,game.height/3.3f,game.width/4,game.width/4);
		game.batch.draw(textures[4],3*game.width/4-game.width/4,game.height/3.3f,game.width/4,game.width/4);
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
			if(screenX>130&&screenX<230&&screenY>480&&screenY<540){
				Spyme.state=2;
				String url="deconnexion?pseudo="+game.player.pseudo;
				game.httpReq(url);
				 while(game.repServeur == null){System.out.print("");}
				Gdx.app.exit();
			}else if(screenX>260&&screenX<350&&screenY>480&&screenY<540){
				game.setScreen(new MenuScreen(game));
			}
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
