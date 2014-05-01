package fr.schaltiemi.pong.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.schaltiemi.pong.Pong;

public class Ball  extends Entity{
		
	/**
	 * Ball's constructor
	 * @param name : name of the entity
	 * @param width : ball's width
	 * @param height: ball's height
	 */
		public Ball(String name, int width, int height){
			super(name, new Texture(Gdx.files.internal("textures/ball3.png")));
			this.name=name;
			this.height=height;
			this.width=width;
			spawn(Pong.WIDTH/2,Pong.HEIGHT/2); //the ball spawns in the middle of the screen
		}
		
		/**
		 * spawn location
		 * @param x : position on X axis
		 * @param y: position on Y axis
		 */
		public void spawn(int x, int y){
			this.x=x;
			this.y=y;
		}
		
		/**
		 * ball's rebounds against walls
		 */
		public void wrap(){
			if(x<0)left=false;
			if(x>Pong.WIDTH-Pong.WIDTH/20)left=true;
			
		}
		
		/**
		 * update the ball's movement
		 * @param dt: frame per seconds
		 */
		public void update(float dt){
			if(left){
				if(up){
					move2(-Pong.ballSpeed,Pong.ballSpeed);
				}else{
					move2(-Pong.ballSpeed,-Pong.ballSpeed);
				}
			}else{
				if(up){
					move2(Pong.ballSpeed,Pong.ballSpeed);
				}else{
					move2(Pong.ballSpeed,-Pong.ballSpeed);
				}
			}
			wrap();
		}
		/**
		 * draw the ball
		 * @param sprite containing the picture used for the ball
		 */
		public void draw(SpriteBatch sb){
			sb.begin();
			sb.draw(sprite,x,y,width,height);
			sb.end();
			
		}



}