package com.spyme.fileninja;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.spyme.core.Spyme;

public class StartScreen implements Screen{

	final Spyme game;
	OrthographicCamera cam;
	Texture texture;
	
	
	public StartScreen(Spyme gam){
		game = gam;
		texture = new Texture(Gdx.files.internal("red_sphere.png"));
		System.out.print("ok");
		cam = new OrthographicCamera();
		cam.setToOrtho(true, game.width, game.height);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		cam.update();
		game.batch.setProjectionMatrix(cam.combined);
		Gdx.input.setInputProcessor(new StartHandler());
	}
	
	private void start(){
		game.setScreen(new MainScreen(game));
		dispose();
	}
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.font.setScale(1);
		
		game.batch.begin();
		game.font.draw(game.batch, "Catch : ", game.width/2-80, game.height/2-100);
		game.batch.draw(texture, game.width/2-33, game.height/2, 64, 64, 0, 0, 64, 64, false, true);
		game.font.setScale(0.5f);
		game.font.draw(game.batch, "Tap anywhere to begin", game.width/2-150, game.height/2+200);
		game.batch.end();
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
		
		texture.dispose();
		
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
			start();
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
