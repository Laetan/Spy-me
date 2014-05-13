package com.spyme.core;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.spyme.fileninja.StartScreen;

public class TestScreen implements Screen{
	
	final Spyme game;
	OrthographicCamera cam;
	
	public TestScreen(Spyme gam){
		game = gam;
		cam = new OrthographicCamera();
		cam.setToOrtho(true, game.width, game.height);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		cam.update();
		game.batch.setProjectionMatrix(cam.combined);
		Gdx.graphics.setContinuousRendering(false);
		Gdx.input.setInputProcessor(new StartHandler());
	}
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.font.draw(game.batch, "Click", game.width/2-30, game.height/2);
		game.batch.end();
	}

	private void testConn(){
		//String ans = game.httpRequest("connexion?pseudo=nico&password=crystal");
	
		//System.out.println("Test Connexion : "+ans);
	}
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	private class StartHandler implements InputProcessor{

		@Override
		public boolean keyDown(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			
			//game.setScreen(new StartScreen(game));
			testConn();
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}

}
