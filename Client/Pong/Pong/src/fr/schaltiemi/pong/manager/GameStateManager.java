package fr.schaltiemi.pong.manager;

import fr.schaltiemi.pong.gamestates.GameOverState;
import fr.schaltiemi.pong.gamestates.GameState;
import fr.schaltiemi.pong.gamestates.MenuState;
import fr.schaltiemi.pong.gamestates.NPlayState;
import fr.schaltiemi.pong.gamestates.PlayState;
import fr.schaltiemi.pong.gamestates.ScoreState;

public class GameStateManager {
	//current game state
	private GameState gameState;
	
	public static final int MENU=0;
	public static final int PLAY=1;
	public static final int BLACK=2;
	public static final int RESULT=3;
	public static final int NPLAYER=4;
	
	public GameStateManager(){
		setState(NPLAYER);
	}
	
	/**
	 * dispose the current state
	 * @param state: state we want to display(MENU/PLAY/BLACKSCREEN/GAMEOVER)
	 */
	public void setState(int state){
		//we dispose the current state
		if(gameState!=null){
			gameState.dispose();
		}
		 if(state==MENU){
			 gameState=new MenuState(this);
		 }
		 if(state==PLAY){
			 gameState=new PlayState(this);
		 }
		 if(state==BLACK){
			 gameState=new ScoreState(this);
		 }
		 if(state==RESULT){
			 gameState=new GameOverState(this);
		 }
		 if(state==NPLAYER){
			 gameState=new NPlayState(this);
		 }
	}

	/**
	 * update the state
	 * @param dt
	 */
	public void update(float dt){
		gameState.update(dt);
	}
	
	/**
	 * draw the state chosen
	 */
	public void draw(){
		gameState.draw();
	}
}
