package com.spyme.menu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.spyme.core.Plateau;
import com.spyme.core.Spyme;

public class MenuScreen implements Screen{

//****************************************************************************************
//****************************************************************************************
		
	Spyme game;
	OrthographicCamera cam;
	Texture  texture;
	
//****************************************************************************************
//****************************************************************************************
			
	public MenuScreen(Spyme gam){
		Gdx.graphics.setContinuousRendering(true);
		game = gam;
		texture = new Texture(Gdx.files.internal("menu_t.png"));
		cam = new OrthographicCamera();
		cam.setToOrtho(true, game.width, game.height);
		cam.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.input.setInputProcessor(new MenuHandler());
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
		
	}
	
//****************************************************************************************
//****************************************************************************************
		
	private class MenuHandler implements InputProcessor{

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
			if(screenY<0.45f*game.height&&screenY>0.33f*game.height){
				game.setScreen(new Plateau(game));
				//System.out.println("on lance le plateau");
			}else if(screenY<0.57f*game.height&&screenY>0.46f*game.height){
				game.setScreen(new OptionScreen(game));
			}else if(screenY<0.69f*game.height&&screenY>0.59f*game.height){
				//game.setScreen(new HelpScreen(game));
				System.out.println("on lance le menu d'aide");	
			}else if(screenY>0.7f*game.height&&screenY<0.82f*game.height){
				game.setScreen(new ExitScreen(game));
			}
			System.out.println(screenY);
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