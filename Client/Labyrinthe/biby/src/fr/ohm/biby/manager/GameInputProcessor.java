package fr.ohm.biby.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

import fr.ohm.biby.biby;
import fr.ohm.biby.entities.Ball;
import fr.ohm.biby.gamestates.MenuState;
import fr.ohm.biby.gamestates.PlayState;

public class GameInputProcessor extends InputAdapter{

	/**
	 * set the down state of UP/DOWN/LEFT/RIGHT/Q/D/ENTER
	 */
	/*int alternate=-1;
	private int[] position={0,0};*/
	private int[] position={0,0,0,0};
	public static boolean l=false;
	public static boolean r=false;
	public static boolean d=false;
	public static boolean u=false;
	

	/**
	 * function called when a keyboard key is pressed
	 */
	public boolean keyDown(int k) {
		if(k == Keys.UP) {
			GameKeys.setKey(GameKeys.UP, true);
		}
		if(k == Keys.LEFT) {
			GameKeys.setKey(GameKeys.LEFT, true);
		}
		if(k == Keys.DOWN) {
			GameKeys.setKey(GameKeys.DOWN, true);
		}
		if(k == Keys.RIGHT) {
			GameKeys.setKey(GameKeys.RIGHT, true);
		}
		if(k == Keys.ENTER) {
			GameKeys.setKey(GameKeys.ENTER, true);
		}
		return true;
	}
	
	/**
	 * function called when a keyboard key is released
	 */
	public boolean keyUp(int k) {
		if(k == Keys.UP) {
			GameKeys.setKey(GameKeys.UP, false);
		}
		if(k == Keys.LEFT) {
			GameKeys.setKey(GameKeys.LEFT, false);
		}
		if(k == Keys.DOWN) {
			GameKeys.setKey(GameKeys.DOWN, false);
		}
		if(k == Keys.RIGHT) {
			GameKeys.setKey(GameKeys.RIGHT, false);
		}
		if(k == Keys.ENTER) {
			GameKeys.setKey(GameKeys.ENTER, false);
		}
		return true;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * function called when the screen is touched
	 */
	public boolean touchDown(int screenX, int screenY, int pointer, int button){
		position[0]=screenX;
		position[2]=screenY;
		if(biby.playState==0){
			checkMenuDetection(screenY);
		}
		else if (biby.playState==2){
			biby.gsm.setState(GameStateManager.PLAY);
		}
		return true;
	}

	/**
	 * check the menu selection
	 * @param screenY: position on the y axis
	 */
	void checkMenuDetection(int screenY){
		for(int i=0;i<MenuState.menuItems.length;i++){
			if(screenY>biby.HEIGHT-biby.HEIGHT/2+70*i&&screenY<biby.HEIGHT-biby.HEIGHT/2+(70*i)+70){
				if(i==0){
					biby.gsm.setState(GameStateManager.PLAY);
				}
				else if(i==1){
					Gdx.app.exit();
				}
			}
		}
	}

	/**
	 * function called when the user drag his finger
	 */
	public boolean touchDragged(int screenX, int screenY, int pointer){	
		if(biby.playState==1){
		position[1]=screenX;
		position[3]=screenY;
		checkBallDetection(PlayState.ball,screenX);
		}
		return true;
	}

	/**
	 * check the direction the user choose to go
	 * @param b: Ball/player
	 * @param screenX: position on the x axis
	 */
	void checkBallDetection(Ball b,int screenX){
		if(PlayState.ball.getMoving()==false){
		if(Math.abs(position[1]-position[0])>Math.abs(position[2]-position[3])){
			if(position[1]>position[0]&&PlayState.tempV.getvDir()[3]!=null){
				check_right(b);
			}else if(position[1]<position[0]&&PlayState.tempV.getvDir()[2]!=null){
				check_left(b);
				}
		}else{
			if(position[3]<position[2]&&PlayState.tempV.getvDir()[1]!=null){
				check_up(b);
			}else if(position[3]>position[2]&&PlayState.tempV.getvDir()[0]!=null){
				check_down(b);
			}
		}
		position[0]=position[1];
		position[2]=position[3];
		}
		}
	
	/**
	 * check if the right direction is chosen
	 * @param b: Ball
	 */
	public void check_right(Ball b){
		setFalse();
		r=true;
		if(b.getX()<b.getPosXmax()){
			b.setX(biby.ballSpeed);
			biby.cam.position.set(b.getX(), b.getY(), 0);
			biby.cam.update();
		}else{
			b.setRight(false);
			b.setMoving(false);
		}
	}
	
	/**
	 * check if the left direction is chosen
	 * @param b: Ball
	 */
	public void check_left(Ball b){
		setFalse();
		l=true;
		if(b.getX()>b.getPosXmin()){
			b.setX(-biby.ballSpeed);
			biby.cam.position.set(b.getX(), b.getY(), 0);
			biby.cam.update();

		}else{
			b.setLeft(false);
			b.setMoving(false);
		}
	}
	
	/**
	 * check if the up direction is chosen
	 * @param b: Ball
	 */
	public void check_up(Ball b){
		setFalse();
		u=true;
		if(b.getY()<b.getPosYmax()){
			b.setY(biby.ballSpeed);
			biby.cam.position.set(b.getX(), b.getY(), 0);
			biby.cam.update();
		}else{
			b.setUp(false);
			b.setMoving(false);
		}
	}
	
	/**
	 * check if the down direction is chosen
	 * @param b: Ball
	 */
	public void check_down(Ball b){
		setFalse();
		d=true;
		if(b.getY()>b.getPosYmin()){
			b.setY(-biby.ballSpeed);
			biby.cam.position.set(b.getX(), b.getY(), 0);
			biby.cam.update();
		}else{
			b.setDown(false);
			b.setMoving(false);
		}
	}
	
	/**
	 * set all the directions false
	 */
	public void setFalse(){
		r=false;
		l=false;
		d=false;
		u=false;
	}
		
}
