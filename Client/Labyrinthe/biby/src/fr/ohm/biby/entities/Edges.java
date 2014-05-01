package fr.ohm.biby.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Edges {
	
	private int x;
	private int y;
	private int x2;
	private int y2;
	private Sprite sprite;

	/**
	 * constructor
	 * @param x : position on the x axis of the first vertice
	 * @param y : position on the y axis of the first vertice
	 * @param x2 : position on the x axis of the second vertice
	 * @param y2 : position on the y axis of the second vertice
	 */
	public Edges(int x, int y, int x2, int y2){
		this.sprite=new Sprite(new Texture(Gdx.files.internal("textures/edge.png")));
		this.y2=y2;
		this.x2=x2;
		this.x=x;
		this.y=y;
		
	}
	
	public int min(int x1, int x2){
		return x1>x2?x2:x1;
	}
	
	/**
	 * draw the edges
	 * @param sb
	 */
	public void draw(SpriteBatch sb){
		sb.begin();
		if(Math.abs(x-x2)==0||Math.abs(y2-y)==0){
			sb.draw(sprite,min(x,x2),min(y,y2),Math.abs(x2-x)+2,Math.abs(y2-y)+2);
		}
		sb.end();
		
	}
}
