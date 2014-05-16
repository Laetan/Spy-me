package com.spyme.fileninja;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.spyme.core.Spyme;

public class EndScreen implements Screen{
	

	Spyme game;
	OrthographicCamera cam;
	Texture [] textures;
	Array<int[]> savedFiles;
	final Array<SavedFile> fileList = new Array<SavedFile>();
	int score, tscore, ttscore;
	float lastUpdated;
	
	public EndScreen(Spyme gam, Array<int[]> sav){
		Gdx.graphics.setContinuousRendering(true);
		game = gam;
		textures = new Texture[7];
		textures[0] = new Texture(Gdx.files.internal("buffer_icon.png"));
		textures[1] = new Texture(Gdx.files.internal("file_icon.png"));
		textures[2] = new Texture(Gdx.files.internal("mail_icon.png"));
		textures[3] = new Texture(Gdx.files.internal("sound_icon.png"));
		textures[4] = new Texture(Gdx.files.internal("yt_icon.png"));
		textures[5] = new Texture(Gdx.files.internal("zip_icon.png"));
		textures[6] = new Texture(Gdx.files.internal("virus_icon.png"));
		savedFiles = sav;
		score = 0;
		ttscore = tscore = 0;
		cam = new OrthographicCamera();
		cam.setToOrtho(true, game.width, game.height);
		cam.update();
		game.batch.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(0, 0, 1, 1);
		int i = 1;
		fileList.add(new SavedFile(0,0));
		
		for(int[] s: savedFiles){
			if(s[0] != 0) fileList.add(new SavedFile(s[0],i));
			score += s[0] == 0 ? 500 : 100;
			i++;
		}
		fileList.swap(0, fileList.size-1);
		lastUpdated = TimeUtils.nanoTime();
		Gdx.input.setInputProcessor(new InputHandler());
		
		
		
	}
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.batch.begin();
		
		for(SavedFile s : fileList){
			if(!s.dispose)
				game.batch.draw(textures[s.fileType], s.x, s.y, 64f, s.height, 0, 0, 64, s.height, false, true);
		}
		game.font.draw(game.batch, ttscore+"", 200, 300);
		game.batch.end();
		
		ttscore += MathUtils.ceil((tscore - ttscore)*4*delta);
		for(SavedFile s : fileList){
			s.update(delta);
		}
		
		if(TimeUtils.nanoTime() - lastUpdated > 1000000000){
			for(SavedFile s : fileList){
				if(!s.dispose){
					tscore+=( !s.move() ) ? 0 : (s.fileType == 0)? 500 :100;
				}
				
			}
			lastUpdated = TimeUtils.nanoTime();
		}
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
		for(Texture t : textures)
		{
			t.dispose();
		}
	}
	
	private class InputHandler implements InputProcessor{

		@Override
		public boolean keyDown(int keycode) {
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			return false;
		}

		@Override
		public boolean keyTyped(char character) {

			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			ttscore = tscore = score;
			for(SavedFile s:fileList){
				s.dispose=true;
			}
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {

			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {

			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			return false;
		}

		@Override
		public boolean scrolled(int amount) {

			return false;
		}
	}	

}
