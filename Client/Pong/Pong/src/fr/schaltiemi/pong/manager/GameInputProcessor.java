package fr.schaltiemi.pong.manager;

import java.awt.Font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

import fr.schaltiemi.pong.Pong;
import fr.schaltiemi.pong.entities.Paddle;
import fr.schaltiemi.pong.gamestates.MenuState;
import fr.schaltiemi.pong.gamestates.NPlayState;
import fr.schaltiemi.pong.gamestates.PlayState;


public class GameInputProcessor extends InputAdapter {
    
	/**
	 * set the down state of UP/DOWN/LEFT/RIGHT/Q/D/ENTER
	 */
	int alternate=-1;
	private int[] position={0,0};

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
		if(k == Keys.Q) {
			GameKeys.setKey(GameKeys.Q, true);
		}
		if(k == Keys.D) {
			GameKeys.setKey(GameKeys.D, true);
		}
		if(k == Keys.SHIFT_LEFT || k == Keys.SHIFT_RIGHT) {
			GameKeys.setKey(GameKeys.SHIFT, true);
		}
		return true;
	}
	
	/**
	 * set the up state of UP/DOWN/LEFT/RIGHT/Q/D/ENTER
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
		if(k == Keys.Q) {
			GameKeys.setKey(GameKeys.Q, false);
		}
		if(k == Keys.D) {
			GameKeys.setKey(GameKeys.D, false);
		}
		if(k == Keys.SHIFT_LEFT || k == Keys.SHIFT_RIGHT) {
			GameKeys.setKey(GameKeys.SHIFT, false);
		}
		return true;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button){
		position[0]=screenX;
		System.out.println(screenY);
		if(Pong.playState==1){
			checkPlayerDetection(screenY);
		}
		else if (Pong.playState==2){
			checkMenuDetection(screenY);
		}
		else if (Pong.playState==4){
			checkGODetection(screenY);
		}
		return true;
	}

void checkPlayerDetection(int screenY){
	for(int i=0;i<NPlayState.menuItems.length;i++){
		if(screenY>Pong.HEIGHT-Pong.HEIGHT/2+70*i&&screenY<Pong.HEIGHT-Pong.HEIGHT/2+(70*i)+70){
			if(i==0){
				Pong.isIA=true;
				Pong.gsm.setState(GameStateManager.MENU);
			}
			else if(i==1){
				Pong.isIA=false;
				Pong.gsm.setState(GameStateManager.MENU);
			}
			else if(i==2){
				Gdx.app.exit();
			}
		}
	}
}

void checkMenuDetection(int screenY){
	for(int i=0;i<MenuState.menuItems.length;i++){
		if(screenY>Pong.HEIGHT-Pong.HEIGHT/2+70*i&&screenY<Pong.HEIGHT-Pong.HEIGHT/2+(70*i)+70){
			if(i==0){
				setConfig(13,11,23);
			}
			else if(i==1){
				setConfig(18,15,28);
			}
			else if(i==2){
				setConfig(23,30,43);
			}
			else if(i==3){
				Gdx.app.exit();
			}
		}
	}
}

void checkGODetection(int screenY){
	for(int i=0;i<NPlayState.menuItems.length;i++){
		if(screenY>Pong.HEIGHT-Pong.HEIGHT/2+70*i&&screenY<Pong.HEIGHT-Pong.HEIGHT/2+(70*i)+70){
			if(i==0){
				Pong.gsm.setState(GameStateManager.MENU);
			}
			else if(i==1){
				Gdx.app.exit();
			}
		}
	}
}
/**
 * change the ball speed/maxspeed, the IA speed related to the level chosen
 * @param ts: new ballSpeed
 * @param ias: new IA paddle speed
 * @param bms: new ball maximum speed
 */
private void setConfig(int ts, int ias, int bms){
	Pong.tempSpeed=ts;
	Pong.IASpeed=ias;
	Pong.ballMaxSpeed=bms;
	Pong.gsm.setState(GameStateManager.PLAY);
}

	public boolean touchUp(int screenX,int screenY,int pointer, int button){
		//System.out.println("bouh up");
		return true;
	}
	
	public boolean touchDragged(int screenX, int screenY, int pointer){	
		if(Pong.playState==3){
		position[1]=screenX;
		if(screenY<100){
			if(!Pong.isIA){
				checkPaddleDetection(PlayState.paddle2,screenX);
		}}
		if(screenY>Pong.HEIGHT-100){
			checkPaddleDetection(PlayState.paddle1,screenX);
			}}
		return true;
	}
	
	void checkPaddleDetection(Paddle p,int screenX){
			if(p.getX()+p.getWidth()/2+100>screenX && p.getX()+p.getWidth()/2<screenX+p.getWidth()+40){
				if(position[1]>position[0]){
					p.setX(screenX-p.getWidth()/2);
				}else{
					p.setX(screenX-p.getWidth()/2);
				}
				position[0]=position[1];
				}
		}
}


