package com.spyme.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.spyme.core.Spyme;

public class MenuScreen implements Screen{

//****************************************************************************************
//****************************************************************************************
		
	Spyme game;
	OrthographicCamera cam;
	Texture [] textures;
	
//****************************************************************************************
//****************************************************************************************
			
	public MenuScreen(Spyme gam){
		Gdx.graphics.setContinuousRendering(true);
		game = gam;
		textures = new Texture[8];
		textures[0] = new Texture(Gdx.files.internal("continuer_m.png"));
		textures[1] = new Texture(Gdx.files.internal("continuer_m2.png"));
		textures[2] = new Texture(Gdx.files.internal("options_m.png"));
		textures[3] = new Texture(Gdx.files.internal("options_m2.png"));
		textures[4] = new Texture(Gdx.files.internal("aide_m.png"));
		textures[5] = new Texture(Gdx.files.internal("aide_m2.png"));
		textures[6] = new Texture(Gdx.files.internal("deconnexion_m.png"));
		textures[7] = new Texture(Gdx.files.internal("deconnexion_m2.png"));
		cam = new OrthographicCamera();
		cam.setToOrtho(true, game.width, game.height);
		cam.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.input.setInputProcessor(new MenuHandler());
	}
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		for(int i=0;i<textures.length;i=i+2){
			game.batch.draw(textures[i],game.width/10,game.height/1.6f-i*50,game.width/1.2f,game.width/5);
		}
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
			if(screenY<310&&screenY>240){
				//game.setScreen(new PlateauScreen(game));
				System.out.println("on lance le plateau");
			}else if(screenY<410&&screenY>350){
				game.setScreen(new OptionScreen(game));
			}else if(screenY<500&&screenY>430){
				//game.setScreen(new HelpScreen(game));
				System.out.println("on lance le menu d'aide");	
			}else if(screenY>530&&screenY<590){
				game.setScreen(new ExitScreen(game));
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