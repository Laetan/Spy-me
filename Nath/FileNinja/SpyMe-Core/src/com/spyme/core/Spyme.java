package com.spyme.core;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Spyme extends Game{
	
	public SpriteBatch batch;
	public BitmapFont font;
	public int width;
	public int height;
	
	@Override
	public void create() {
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();
		batch = new SpriteBatch();
		font = new BitmapFont(true);
//		font = new BitmapFont(Gdx.files.internal("font.fnt"),true);

		this.setScreen(new TestScreen(this));
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

