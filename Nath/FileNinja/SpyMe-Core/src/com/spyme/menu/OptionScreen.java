package com.spyme.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.spyme.core.Spyme;

public class OptionScreen implements Screen{
	
//****************************************************************************************
//****************************************************************************************
	
	Spyme game;
	OrthographicCamera cam;
	Texture texture;
	public static String coRepServeur;
	
//****************************************************************************************
//****************************************************************************************
	
	public OptionScreen(Spyme gam){
		Gdx.graphics.setContinuousRendering(true);
		game = gam;
		texture = new Texture(Gdx.files.internal("option_t.png"));
		cam = new OrthographicCamera();
		cam.setToOrtho(true, game.width, game.height);
		cam.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.input.setInputProcessor(new OptionHandler());
	}
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
		texture.dispose();
	}
	
//****************************************************************************************
//****************************************************************************************
		
	private class OptionHandler implements InputProcessor{

		public boolean keyDown(int keycode) {
			return false;
		}

		public boolean keyUp(int keycode) {
			return false;
		}

		public boolean keyTyped(char character) {
			return false;
		}

		public boolean touchDown(int screenX, int screenY, int pointer,
				int button) {
			if(screenX>0.18f*game.width&&screenX<0.94f*game.width&&screenY>0.525f*game.height&&screenY<0.62f*game.height){
				Spyme.state=3;
				String url="reinitialisation?pseudo="+game.player.pseudo;
				game.httpReq(url);
				while(game.repServeur == null){System.out.println(game.repServeur);}

				//if(coRepServeur!=null){
				System.out.println(game.repServeur);
				game.player.level=0;
				game.player.score=0;
				coRepServeur=null;

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
