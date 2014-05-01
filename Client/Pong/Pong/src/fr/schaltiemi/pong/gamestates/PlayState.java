package fr.schaltiemi.pong.gamestates;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.schaltiemi.pong.Pong;
import fr.schaltiemi.pong.entities.Ball;
import fr.schaltiemi.pong.entities.Paddle;
import fr.schaltiemi.pong.manager.GameKeys;
import fr.schaltiemi.pong.manager.GameStateManager;



public class PlayState extends GameState{
	
	private SpriteBatch sb;
	private Ball ball;
	public static Paddle paddle1;
	public static Paddle paddle2;
	private BitmapFont font;
	
	/**
	 * PlayState's constructor
	 * @param gsm: chosen state (menu-play-blackscreen-gameover)
	 */
	public PlayState(GameStateManager gsm){
		super(gsm);
	}

	/**
	 * function called once when the playState is created
	 */
	public void init() {
		Pong.playState=3;
		System.out.println(Pong.tempSpeed);
		Pong.ballSpeed=Pong.tempSpeed;
		sb= new SpriteBatch();
		initializeElements();
	}

	/**
	 * initialize the font, the ball and the 2 paddles
	 */
	public void initializeElements(){
		font= new BitmapFont();
		font.setScale(2.5f);
		ball= new Ball("ball",Pong.WIDTH/20,Pong.WIDTH/20);
		paddle1=new Paddle("paddle1",Pong.WIDTH/6,Pong.WIDTH/22,Pong.WIDTH/2-45,Pong.WIDTH/15);
		paddle2=new Paddle("paddle2",Pong.WIDTH/6,Pong.WIDTH/22,Pong.WIDTH/2-45,Pong.HEIGHT-Pong.WIDTH/22-Pong.WIDTH/15);
	}
	/**
	 * update the screen
	 */
	public void update(float dt) {
		handleInput();
		ball.update(dt);
		paddle1.update(dt);
		paddle2.update(dt);
		if(Pong.isIA){
			paddle2IA();
		}
		checkCollisions();
		updateScore();
		isGameOver();
	}
	
	/**
	 * increase the ball's speed when a collision is detected
	 */
	private void increaseSpeed(){
		if(Pong.ballSpeed<Pong.ballMaxSpeed){
			Pong.ballSpeed++;
		}
	}
	/**
	 * if the ball hits the left corner of a paddle, go on left
	 * if the ball hits the right corner of a paddle, go on right
	 */
	private void hitCorner(){
		if( (ball.getX()>paddle1.getX()+paddle1.getWidth()-20) && (ball.getX()<paddle1.getX()+paddle1.getWidth())){
			ball.setLeft(false);
			increaseSpeed();
		}
		else if( (ball.getX()-ball.getWidth()<paddle1.getX()+20) && (ball.getX()-ball.getWidth()>paddle1.getX())){
			ball.setLeft(true);
			increaseSpeed();
		}
		else if( (ball.getX()-ball.getWidth()<paddle2.getX()+20) && (ball.getX()-ball.getWidth()>paddle2.getX())){
			ball.setLeft(true);
			increaseSpeed();
		}
		else if( (ball.getX()>paddle2.getX()+paddle2.getWidth()-20) && (ball.getX()<paddle2.getX()+paddle2.getWidth())){
			ball.setLeft(false);
			increaseSpeed();
		}
	}
	
	/**
	 * switch to game over state
	 */
	private void isGameOver(){
		if(Pong.scoreJ1==3||Pong.scoreJ2==3){
			gsm.setState(GameStateManager.RESULT);
		}
	}
	
	/**
	 * update the score
	 */
	private void updateScore(){
		if(ball.getY()<0){
			Pong.scoreJ2=(Pong.scoreJ2+1);
			gsm.setState(GameStateManager.BLACK);
			ball.spawn(240, Pong.HEIGHT-20);
			ball.setUp(false);
		}
		if(ball.getY()>Pong.HEIGHT){
			Pong.scoreJ1=(Pong.scoreJ1+1);
			gsm.setState(GameStateManager.BLACK);
			ball.spawn(240, 20);
			ball.setUp(true);
		}
	}
	
	/**
	 * when there is only one player, movement of the paddle2 
	 */
	private void paddle2IA(){
		if(ball.isUp()==false){
			if(paddle2.getX()+paddle2.getWidth()/2<235){
				paddle2.move(Pong.IASpeed);
			}else if(paddle2.getX()+paddle2.getWidth()/2>245){
				paddle2.move(-Pong.IASpeed);
			}
		}
		else if(ball.getX()+(ball.getWidth()/2)>paddle2.getX()+paddle2.getWidth()/2){
			paddle2.move(Pong.IASpeed);
		}else if(ball.getX()+(ball.getWidth()/2)<paddle2.getX()+paddle2.getWidth()/2){
			paddle2.move(-Pong.IASpeed);
		}
	}
	
	/**
	 * collisions between the ball and the paddles
	 */
	private void checkCollisions(){
		if(ball.getY()<=Pong.WIDTH/15+paddle1.getHeight()&&ball.getY()>Pong.WIDTH/15){
			if((ball.getX()+ball.getWidth()>paddle1.getX())&&
					(ball.getX()<paddle1.getX()+paddle1.getWidth())){
				hitCorner();
				ball.setUp(true);
				increaseSpeed();
			}
		}
		if(ball.getY()+ball.getHeight()>=Pong.HEIGHT-paddle2.getHeight()/2-(Pong.WIDTH/15)&&ball.getY()+ball.getHeight()<Pong.HEIGHT-Pong.WIDTH/15){
			if((ball.getX()+ball.getWidth()>paddle2.getX())&&
					(ball.getX()<paddle2.getX()+paddle2.getWidth())){
				hitCorner();
				ball.setUp(false);
				increaseSpeed();
			}
		}
	}

	/**
	 * draw the different elements
	 */
	public void draw() {
		ball.draw(sb);
		paddle1.draw(sb);
		paddle2.draw(sb);
		
		//draw score
		sb.setColor(1,1,1,1);
		sb.begin();
		String s="Joueur 1 : "+Integer.toString(Pong.scoreJ1)+" - Joueur 2 : "+Integer.toString(Pong.scoreJ2);
		font.draw(sb,s, Pong.WIDTH/2-font.getBounds(s).width/2, Pong.HEIGHT/2);
		sb.end();
	}

	/**
	 * listen to the keyboard keys left/right/q/d
	 */
	public void handleInput() {
		paddle1.setLeft(GameKeys.isDown(GameKeys.LEFT));
		paddle1.setRight(GameKeys.isDown(GameKeys.RIGHT));
		paddle2.setLeft(GameKeys.isDown(GameKeys.Q));
		paddle2.setRight(GameKeys.isDown(GameKeys.D));
		
	}

	public void dispose() {
	}

	//getters and setters
	public Paddle getPaddle1() {
		return paddle1;
	}

	public Paddle getPaddle2() {
		return paddle2;
	}

}
