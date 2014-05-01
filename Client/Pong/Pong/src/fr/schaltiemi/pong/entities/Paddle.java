package fr.schaltiemi.pong.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.schaltiemi.pong.Pong;

public class Paddle extends Entity {
	
	private boolean isLeft;
	private boolean isRight;
	
	/**
	 * Paddle's constructor
	 * @param name:Paddle's name
	 * @param width: Paddle's width
	 * @param height: Paddle's height
	 * @param spawnX: Paddle's spawn position X axis
	 * @param spawnY: Paddle's spawn position Y axis
	 */
	public Paddle(String name, int width, int height, int spawnX,int spawnY){
		super(name, new Texture(Gdx.files.internal("textures/paddle.png")));
		this.name=name;
		this.height=height;
		this.width=width;
		spawn(spawnX,spawnY); 
	}


	/**
	 * Paddle's spawn 
	 * @param x: position on the x axis
	 * @param y: position on the y axis
	 */
	public void spawn(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	/**
	 * Stay in the screen bounds
	 */
	protected void wrap(){
		if(x<0)x=0;
		if(x>Pong.WIDTH-this.width)x=Pong.WIDTH-this.width;
		
	}

	/**
	 * update the paddle's position
	 * @param frame per second
	 */
	public void update(float dt){

		if(isLeft)move(-10);
		if(isRight)move(10);
		wrap();
		
	}
	
	/**
	 * draw the paddle
	 * @param sb: used to draw the paddle
	 */
	public void draw(SpriteBatch sb){
		sb.begin();
		sb.draw(sprite,x,y,width,height);
		sb.end();
		
	}

	//setter
	public void setX(int pos){
		this.x=pos;
	}
	
	public void setLeft(boolean b){isLeft=b;}
	public void setRight(boolean b){isRight=b;}

}