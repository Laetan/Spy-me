package com.spyme.fileninja;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.TimeUtils;
import com.spyme.core.*;

/** La classe MainScreen contient l'intégralité du moteur du jeu. Appelée par FileNinja
 * <p>
 * Contient la classe interne InputHandler
 * @author Nathanael
 * @version 0.1
 */
public class MainScreen implements Screen{

	final Spyme game;
	final String VICTORY_TEXT = "Victory ! ";
	final String DEFEAT_TEXT = "ECHEC !";
	OrthographicCamera cam;
	int state;
	/*
	 * 0 : paused
	 * 1 : running
	 * 2 : ended_lost
	 * 3 : ended_won
	 */
	Texture[] textures;
	Texture buffer;
	int targetCount;
	int targetLife;

	final Pool<RectangleFile> filePool = Pools.get(RectangleFile.class);
	final Array<RectangleFile> activFiles = new Array<RectangleFile>();
	final Pool<StoredFile> storePool = Pools.get(StoredFile.class);
	final Array<StoredFile> storedFiles = new Array<StoredFile>();
	final Array<int[]> savedFiles = new Array<int[]>();

	float lastFileTime;

	
	public MainScreen(Spyme gam){
		Gdx.graphics.setContinuousRendering(true);
		game = gam;
		System.out.print("ok");
		textures = new Texture[7];
		textures[0] = new Texture(Gdx.files.internal("buffer_icon.png"));
		textures[1] = new Texture(Gdx.files.internal("file_icon.png"));
		textures[2] = new Texture(Gdx.files.internal("mail_icon.png"));
		textures[3] = new Texture(Gdx.files.internal("sound_icon.png"));
		textures[4] = new Texture(Gdx.files.internal("yt_icon.png"));
		textures[5] = new Texture(Gdx.files.internal("zip_icon.png"));
		textures[6] = new Texture(Gdx.files.internal("virus_icon.png"));
		buffer = new Texture(Gdx.files.internal("buffer.jpg"));
		
		cam = new OrthographicCamera();
		cam.setToOrtho(true, game.width, game.height);
		lastFileTime = 0;
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		cam.update();
		game.batch.setProjectionMatrix(cam.combined);
		
		Gdx.input.setInputProcessor(new InputHandler());
		
		targetCount = 0;
		targetLife = 3;
		state = 1;
		
		StoredFile sfile = storePool.obtain();
		sfile.init(0, 0);
		storedFiles.add(sfile);
	}
	
	@Override
	public void render(float delta) {
		
		
		if(state == 1)
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		gameRunning();
		switch(state){
		case 0:
			break;
		case 1:
			break;
		case 2:
			gameEnded();
			break;
		case 3:
			gameEnded();
			break;
		}
		game.batch.end();
		
		if(state == 1){
			spawn();
			update();
		}
		if(storedFiles.get(0).stored>=10){
			state = 3;
		}
	}
	
	private void gameRunning(){
		
		for(RectangleFile file : activFiles)
		{
			game.batch.draw(textures[file.fileType], file.x, file.y, file.width, file.height, 0, 0, (int)file.width,(int)file.height, false, true);
		}

		game.batch.draw(buffer,0, game.height-85, game.width, 85, 0, 0, game.width,75, false, true);
		for(StoredFile sfile : storedFiles)
		{
			if(sfile.stored > 0){
				game.batch.draw(textures[sfile.fileType], sfile.x, sfile.y, sfile.width, sfile.height, 0, 0, (int)sfile.width,(int)sfile.height, false, true);
				game.font.draw(game.batch, sfile.stored*10+"%",sfile.x+63, game.height-30);
			}
		}	
	}
	
	private void gameEnded(){
		Gdx.graphics.setContinuousRendering(false);
		if(state == 3){
			game.font.setScale(1.5f);
			game.font.draw(game.batch, VICTORY_TEXT, game.width /2-140, game.height /2-100);
			game.font.setScale(0.5f);
			game.font.draw(game.batch, "Tap anywhere to continue", game.width /2-170, game.height /2+100);
		}
		else{
			game.font.setScale(1.5f);
			game.font.draw(game.batch, DEFEAT_TEXT, game.width /2-110, game.height /2-100);
			game.font.setScale(0.5f);
			game.font.draw(game.batch, "Tap anywhere to play again", game.width /2-170, game.height /2+100);
		}

		
	}
	
	private void spawn(){
		
		if(TimeUtils.nanoTime() - lastFileTime > 500000000)
		{
			RectangleFile newFile = filePool.obtain();
			newFile.init(MathUtils.random(1, 6));
			activFiles.add(newFile);
			lastFileTime = TimeUtils.nanoTime();
			if(MathUtils.randomBoolean( 0.1f) && targetCount < 10){
				newFile = filePool.obtain();
				newFile.init(0);
				activFiles.add(newFile);
				targetCount++;
			}
		}
	}

	private void testPos(RectangleFile file, int j){
		
		boolean d = false;
		
		if(!file.caught)
		{
			if(file.y > game.height -70)
			{
				if(file.fileType == 0){
					targetLife--;
					targetCount--;
					if(targetLife == 0){
						state = 2;
					}
				}
				activFiles.removeIndex(j);
				filePool.free(file);				
			}
		}
		else
		{
			if(file.y > game.height - 70)
			{
				for(StoredFile sfile : storedFiles)
				{
					
					if(sfile.fileType == file.fileType && sfile.stored < 10)
					{
						sfile.add();
						if(sfile.stored == 10)
						{
							sfile.dispose();
							int i[] = {sfile.fileType};
							savedFiles.add(i);
						}
						d = true;
						break;
					}
				}
				
				if(!d)
				{
					
					StoredFile sfile = storePool.obtain();
					sfile.init(file.fileType, storedFiles.size);
					sfile.add();
					storedFiles.add(sfile);
				}
				
				activFiles.removeIndex(j);
				filePool.free(file);
				
			}
		}
	}

	/**
	 * Lance l'actualisation de la position de touts les items
	 */
	private void update(){
		
		RectangleFile file;
		StoredFile sfile;
		int len = activFiles.size;
		int slen = storedFiles.size;
		float delta = Gdx.graphics.getDeltaTime();
		boolean down;
		
		for(int i = len; --i>= 0;)
		{
			
			file = activFiles.get(i);
			file.update(delta);
			testPos(file,i);
		}
		for(int i = slen; --i>= 0;)
		{
			sfile = storedFiles.get(i);
			if(sfile.moving || sfile.toDispose)
			{
				down = sfile.update(delta);
				if(down)
				{					
					storedFiles.removeIndex(i);
					storePool.free(sfile);
					slen = storedFiles.size;
					for(int j = i; j< slen; j++)
					{
						sfile = storedFiles.get(j);
						sfile.move(j);
					}
					
				}
			}
		}
	}
	
	public void end(){
		if(state == 3){		
			game.setScreen(new EndScreen(game, savedFiles));
			dispose();
		}
		else{
			game.setScreen(new MainScreen(game));
			dispose();
		}
	}
	
	
	@Override
	public void resize(int width, int height) {
		
		
	}

	@Override
	public void show() {
		
		
	}

	@Override
	public void hide() {
		
		
	}

	@Override
	public void pause() {

		
	}

	@Override
	public void resume() {
		
		
	}

	@Override
	public void dispose() {

		for(Texture t : textures){
			t.dispose();
		}
		buffer.dispose();
	}
	
	/** Cette classe gère les inputs du joueur sous la forme d'évenements.
	 * 
	 * @author Nathanaël
	 * @version 0.1
	 */
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
			if(state > 1){
				end();
			}
			
			int len = activFiles.size;
			int slen = storedFiles.size;
			RectangleFile file;
			StoredFile sfile;
			int j;
			Vector3 touchPos = new Vector3(screenX, screenY, 0);
			cam.unproject(touchPos);

			for(int i = len; --i >= 0;)
			{
				file = activFiles.get(i);
				if(touchPos.x > file.x && touchPos.x < file.x+64 && touchPos.y > file.y && touchPos.y < file.y + 64)
				{
		
					if(file.fileType == 0)
					{
						file.catchFile(0);
					}
					else if(file.fileType == 6){
						state = 2;
					}
					else{
						for(j = 1; j < slen; j++){
							sfile = storedFiles.get(j);
							if(sfile.fileType== file.fileType)
								break;
						}
						
						file.catchFile(j);
					}
					
				}
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




	
	
	




