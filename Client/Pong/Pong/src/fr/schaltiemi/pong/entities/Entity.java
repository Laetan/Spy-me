package fr.schaltiemi.pong.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


public abstract class Entity {
	
	protected String name; //entity's name
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected Sprite sprite;
	
	//direction going left-right-up-down
	protected boolean left=true;
	protected boolean up=true;

	/**
	 * Entity's construtor
	 * @param name:Entity's name
	 * @param texture: Entity's texture (picture)
	 */
	protected Entity(String name, Texture texture){
		this.name=name;
		this.sprite=new Sprite(texture);
	}
		
	/**
	 * Entity's movement (paddle)
	 * @param dx: translation on the x axis
	 */
	public void move(float dx){
		x +=dx*Gdx.graphics.getDeltaTime()*30;
	}
	
	/**
	 * Entity's movement (ball)
	 * @param dx: translation on the x axis
	 * @param dy: translation on the y axis
	 */
	public void move2(float dx,float dy){
		if(dx>1){//il se déplace à droite
			left=false;
		}
		else if(dx<0){
			left=true;
		}
		if(dy>1){//il se déplace à droite
			up=true;
		}
		else if(dy<0){
			up=false;
		}
		x +=dx*Gdx.graphics.getDeltaTime()*30;
		y +=dy*Gdx.graphics.getDeltaTime()*30;
	}
	
/*************************************************************************************************************
 *****************************************getters and setters ************************************************* 
 **************************************************************************************************************/
	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}
	
	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
