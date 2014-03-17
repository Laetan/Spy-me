package com.SpyMe.FileNinja;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**Classe Game de FileNinja. Un peu le main du programme. Appelle les différents screen de l'application. 
 * 
 * @author Nathanaël
 * @version 0.1
 */
public class FileNinja extends Game {

	SpriteBatch batch;
	BitmapFont font;
	
	@Override
	public void create() {

		batch = new SpriteBatch();
		font = new BitmapFont();
		
		this.setScreen(new MainScreen(this));
	}
	
	@Override
	public void render()
	{
		super.render();
	}
	
	@Override
	public void dispose()
	{
		batch.dispose();
		font.dispose();
	}

}
