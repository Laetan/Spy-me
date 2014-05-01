package fr.ohm.biby.manager;

import fr.ohm.biby.gamestates.GameState;
import fr.ohm.biby.gamestates.Instruction;
import fr.ohm.biby.gamestates.MenuState;
import fr.ohm.biby.gamestates.PlayState;

public class GameStateManager {
		//current game state
		private GameState gameState;
		public static final int MENU=0;
		public static final int PLAY=1;
		public static final int INSTRUCT=2;
		public GameStateManager(){
			setState(INSTRUCT);
		}
		
		/**
		 * dispose the current state
		 * @param state: state we want to display(MENU/PLAY/INSTRUCTIONS)
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
			 if(state==INSTRUCT){
				 gameState=new Instruction(this);
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
