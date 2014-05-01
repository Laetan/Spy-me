package fr.ohm.biby.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.ohm.biby.biby;

public class Ball {

	//coordinates
	private int x;
	private int y;
	//dimensions
	private int width;
	private int height;
	private Sprite sprite;
	//directions allowed
	private boolean left=false;
	private boolean right=false;
	private boolean up=false;
	private boolean down=false;
	//if the ball is moving
	private boolean moving=false;
	//max positions allowed following the vertices where the player is
	private int posXmin;
	private int posXmax;
	private int posYmin;
	private int posYmax;

	/**
	 * constructor
	 */
	public Ball(int width, int height, int spawnX, int spawnY){
		this.sprite=new Sprite(new Texture(Gdx.files.internal("textures/ball.png")));
		this.height=height;
		this.width=width;
		this.x=spawnX;
		this.y=spawnY;
	}
	
	
	/**
	 * update the player's movement
	 * @param dt: frame per seconds
	 */
	public void update(float dt){
		if(left){
			update_left();
		}else if(right){
			update_right();
		}else if(up){
			update_up();
		}else if(down){
			update_down();
		}
		else{
			this.moving=false;
		}
	}
	

	/**
	 * update the position when the left drag is activated
	 */
	public void update_left(){
		if(this.x>this.posXmin){
		this.moving=true;
		setX(-biby.ballSpeed);
		biby.cam.position.set(this.x, this.y, 0);
		biby.cam.update();
		}else{
			this.x=this.posXmin;
			this.left=false;
			this.moving=false;
		}
	}
	/**
	 * update the position when the right drag is activated
	 */
	public void update_right(){
		if(this.x<this.posXmax){
			this.moving=true;
			setX(biby.ballSpeed);
			biby.cam.position.set(this.x, this.y, 0);
			biby.cam.update();
		}else{
			this.x=this.posXmax;
			this.right=false;
			this.moving=false;
		}
	}

	/**
	 * update the position when the up drag is activated
	 */
	public void update_up(){
		if(this.y<this.posYmax){
			this.moving=true;
			setY(biby.ballSpeed);
			biby.cam.position.set(this.x, this.y, 0);
			biby.cam.update();
		}else{
			this.y=this.posYmax;
			this.up=false;
			this.moving=false;
		}
	}

	/**
	 * update the position when the down drag is activated
	 */
	public void update_down(){
		if(this.y>this.posYmin){
		this.moving=true;
		setY(-biby.ballSpeed);
		biby.cam.position.set(this.x, this.y, 0);
		biby.cam.update();
		}else{
			this.y=this.posYmin;
			this.down=false;
			this.moving=false;
		}	
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
//**********************getters and setters********************************************
	
	public int getPosXmin() {
		return posXmin;
	}
	
	public int getPosXmax() {
		return posXmax;
	}

	public int getPosYmin() {
		return posYmin;
	}

	public int getPosYmax() {
		return posYmax;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	public int getWidth(){
		return this.width;
	}
	public boolean getMoving() {
		return this.moving;
	}
	public void setMoving(boolean moving) {
		this.moving = moving;
	}


	public void setLeft(boolean left) {
		this.left = left;
	}
	
	public void setRight(boolean right) {
		this.right = right;
	}
	
	public void setUp(boolean up) {
		this.up = up;
	}
	
	public void setDown(boolean down) {
		this.down = down;
	}
		
	public void setPosXmin(int posXmin) {
		this.posXmin = posXmin;
	}

	public void setPosXmax(int posXmax) {
		this.posXmax = posXmax;
	}

	public void setPosYmin(int posYmin) {
		this.posYmin = posYmin;
	}

	public void setPosYmax(int posYmax) {
		this.posYmax = posYmax;
	}

	public void setX(int pos){
		this.x+=pos*Gdx.graphics.getDeltaTime()*30;
	}
	
	public void setY(int pos){
		this.y+=pos*Gdx.graphics.getDeltaTime()*30;
	}
}
