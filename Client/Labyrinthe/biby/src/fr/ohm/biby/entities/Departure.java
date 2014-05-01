package fr.ohm.biby.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Departure {

	//coordinates
	private int x;
	private int y;
	//dimensions
	private int width;
	private int height;
	private Sprite sprite;
	
	//constructor
	public Departure(int width, int height, int posx, int posy){
		this.sprite=new Sprite(new Texture(Gdx.files.internal("textures/depart.png")));
		this.height=height;
		this.width=width;
		this.x=posx;
		this.y=posy;
		
	}
	//draw the departure
	public void draw(SpriteBatch sb){
		sb.begin();
		sb.draw(sprite,x,y,width,height);
		sb.end();
		
	}
}
