package com.SpyMe.FileNinja;

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

/** La classe MainScreen contient l'intégralité du moteur du jeu. Appelée par FileNinja
 * <p>
 * Contient la classe interne InputHandler
 * @author Nathanael
 * @version 0.1
 */
public class MainScreen implements Screen{

	final FileNinja game;
	OrthographicCamera cam;
	Texture red;
	Texture blue;
	Texture green;
	final Pool<RectangleFile> filePool = Pools.get(RectangleFile.class);
	final Array<RectangleFile> activFiles = new Array<RectangleFile>();
	final Pool<StoredFile> storePool = Pools.get(StoredFile.class);
	final Array<StoredFile> storedFiles = new Array<StoredFile>();
	float lastFileTime;

	
	public MainScreen(FileNinja gam){
		game = gam;
		
		red = new Texture(Gdx.files.internal("red_sphere.png"));
		blue = new Texture (Gdx.files.internal("blue_sphere.png"));
		green = new Texture(Gdx.files.internal("green_sphere.png"));
		
		cam = new OrthographicCamera();
		cam.setToOrtho(true, 480, 800);
		lastFileTime = 0;
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		cam.update();
		game.batch.setProjectionMatrix(cam.combined);
		Gdx.input.setInputProcessor(new InputHandler());
		
	}
	@Override
	public void render(float delta) {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		int slen = storedFiles.size;
		StoredFile sfile;
		
		game.batch.begin();
		for(RectangleFile file : activFiles)
		{
			switch(file.fileType)
			{	
			case 0:
				game.batch.draw(red, file.x, file.y);
				break;
			case 1:
				game.batch.draw(blue, file.x, file.y);
				break;
			case 2:
				game.batch.draw(green, file.x, file.y);
				break;
			}
			
		}
		for(int i = 0; i < slen; i++)
		{
			sfile = storedFiles.get(i);
			if(sfile.display)
			{
				switch(sfile.fileType)
				{
				case 0:
					game.batch.draw(red, (i * 100) + 20, 800 - 70);
					break;
				case 1:
					game.batch.draw(blue, (i * 100) + 20, 800 - 70);
					break;
				case 2:
					game.batch.draw(green, (i * 100) + 20, 800 - 70);
					break;
				}
				game.font.draw(game.batch, sfile.stored*10+"%",(i*100)+83, 800-30);
			}
		}		
		game.batch.end();
		

		//respawn()
		if(TimeUtils.nanoTime() - lastFileTime > 500000000)
		{
			RectangleFile newFile = filePool.obtain();
			newFile.init(MathUtils.random(0, 2));
			activFiles.add(newFile);
			lastFileTime = TimeUtils.nanoTime();
		}
		

		update();
		
	}
	

	private void testPos(RectangleFile file, int j){
		
		boolean d = false;
		
		
		if(!file.caught)
		{
			if(file.y > 800)
			{
				activFiles.removeIndex(j);
				filePool.free(file);				
			}
		}
		else
		{
			if(file.y > 800 - 70)
			{
				
				for(StoredFile sfile : storedFiles)
				{
					if(sfile.fileType == file.fileType && sfile.stored < 10)
					{
						sfile.add();
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
		int len = activFiles.size;
		for(int i = len; --i>= 0;)
		{
			
			file = activFiles.get(i);
			file.update(Gdx.graphics.getDeltaTime());
			testPos(file,i);
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

		red.dispose();
		blue.dispose();
		green.dispose();
		
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
					for(j = 0; j<slen; j++)
					{
						sfile = storedFiles.get(j);
						if(sfile.fileType== file.fileType)
						{
							file.catchFile(j);
							break;
						}
					}
					if(j == slen)
					{
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
