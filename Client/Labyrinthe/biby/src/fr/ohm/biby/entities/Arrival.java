package fr.ohm.biby.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Arrival {
	private int x;
	private int y;
	private int width;
	private int height;
	private Sprite sprite;
	
	/**
	 * constructor
	 */
	public Arrival(int width, int height, int posx, int posy){
		this.sprite=new Sprite(new Texture(Gdx.files.internal("textures/arrivee.png")));
		this.height=height;
		this.width=width;
		this.x=posx;
		this.y=posy;
		
	}
	
	public void draw(SpriteBatch sb){
		sb.begin();
		sb.draw(sprite,x,y,width,height);
		sb.end();
		
	}
}
